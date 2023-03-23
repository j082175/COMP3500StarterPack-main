package academy.pocu.comp3500.lab10;

import academy.pocu.comp3500.lab10.project.Task;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Project {
    public static List<String> findSchedule(final Task[] tasks, final boolean includeMaintenance) {

        List<String> result = sortTopologically(tasks, includeMaintenance);
        return result;
    }

    private static LinkedList<String> sortTopologically(Task[] tasks, boolean includeMaintenance) {
        HashSet<Task> discovered = new HashSet<>();
        LinkedList<String> sortedList = new LinkedList<>();
        HashSet<Task> overlap = new HashSet<>();
        HashSet<String> isLoop = new HashSet<>();
        boolean[] isCheck = new boolean[]{false};
        LinkedList<String> backup = new LinkedList<>();

        for (Task task : tasks) {
            if (discovered.contains(task)) {
                continue;
            }

            overlap.add(task);
            topologicalSortRecursive(task,
                    discovered,
                    sortedList,
                    overlap,
                    includeMaintenance,
                    isLoop,
                    isCheck,
                    backup
            );
            overlap.clear();

            if (isCheck[0]) {
                String t = null;

                for (int i = 0; i < sortedList.size(); i++) {
                    if (isLoop.contains(sortedList.get(i))) {
                        t = sortedList.get(i);
                    }
                }

                if (t != null) {

                    int index = sortedList.indexOf(t);
                    List<String> backward = sortedList.subList(0, index);
                    List<String> foreward = sortedList.subList(index, sortedList.size());
                    LinkedList<String> n = new LinkedList<>(foreward);
                    sortedList = n;
                    sortedList.addAll(backward);

                    LinkedList<String> b1 = new LinkedList<>(backup);

                    b1.addAll(sortedList);
                    sortedList = b1;
                } else {
                    backup.addAll(sortedList);
                    sortedList = new LinkedList<>(backup);
                }

            }

            if (!includeMaintenance) {

                Task t = task;
                while (t.getPredecessors().size() == 1) {
                    t = t.getPredecessors().get(0);
                }

                if (!sortedList.contains(t.getTitle())) {
                    sortedList.remove(task.getTitle());
                }

            }

            isCheck[0] = false;
            isLoop.clear();
            backup.clear();

        }

        return sortedList;
    }

    private static boolean topologicalSortRecursive(Task task, HashSet<Task> discovered, LinkedList<String> linkedList, HashSet<Task> overlap, boolean includeMaintenance, HashSet<String> isLoop, boolean[] isCheck, LinkedList<String> backup) {
        discovered.add(task);
        boolean check = false;

        // 루프 돌기전 자기자신이 loop 인지를 check
        if (task.getPredecessors().size() > 1) {
            if (!isLoop.contains(task.getTitle())) {
                // 이 노드가 isLoop 인지 조건검사
                Task pivot = task;
                boolean c = false;

                for (Task t : pivot.getPredecessors()) {
                    while (t.getPredecessors().size() != 0) {

                        if (overlap.contains(t) && includeMaintenance) {
                            c = true;
                            overlap.clear();
                            for (int i = 0; i < linkedList.size(); i++) {
                                String str = new String(linkedList.get(i));
                                if (!backup.contains(str)) {
                                    backup.add(str);
                                }
                            }

                            linkedList.clear();
                            isCheck[0] = true;
                        }

                        if (t.getPredecessors().size() > 1) {
                            for (Task t2 : t.getPredecessors()) {
                                while (t2.getPredecessors().size() != 0) {
                                    t2 = t2.getPredecessors().get(0);
                                }

                                if (t2.getTitle().equals(pivot.getTitle())) {
                                    isLoop.add(task.getTitle());
                                    break;
                                }
                            }
                        }

                        if (c) {
                            discovered.remove(t);
                        }

                        t = t.getPredecessors().get(0);
                    }

                    c = false;

                    if (t.getTitle().equals(pivot.getTitle())) {
                        isLoop.add(task.getTitle());
                    }
                }
            }
        }


        for (Task nextTask : task.getPredecessors()) {

            if (nextTask.getPredecessors().size() > 1) {


                if (isLoop.contains(nextTask.getTitle())) {

                    if (!includeMaintenance) {

                        // 새로 추가한 코드 만약 루프노드도 포함해야한다면
/*                        if (!linkedList.contains(nextTask.getTitle())) {
                            linkedList.add(nextTask.getTitle());
                        }*/
                        //

                        return true;
                    } else {
                        for (int i = 0; i < linkedList.size(); i++) {
                            String str = new String(linkedList.get(i));
                            if (!backup.contains(str)) {
                                backup.add(str);
                            }
                        }
                        if (!backup.contains(nextTask.getTitle())) {
                            backup.add(new String(nextTask.getTitle()));
                        }

                        linkedList.clear();
                        isCheck[0] = true;
                    }
                } else {
                    // 이 노드가 isLoop 인지 조건검사
                    Task pivot = nextTask;
                    boolean c = false;

                    for (Task t : pivot.getPredecessors()) {
                        while (t.getPredecessors().size() != 0) {

                            if (overlap.contains(t) && includeMaintenance) {
                                c = true;
                                overlap.clear();
                                for (int i = 0; i < linkedList.size(); i++) {
                                    String str = new String(linkedList.get(i));
                                    if (!backup.contains(str)) {
                                        backup.add(str);
                                    }
                                }

                                linkedList.clear();
                                isCheck[0] = true;
                            }

                            if (t.getPredecessors().size() > 1) {
                                break;
                            }

                            if (c) {
                                discovered.remove(t);
                            }

                            t = t.getPredecessors().get(0);
                        }

                        c = false;

                        if (t.getTitle().equals(pivot.getTitle())) {
                            isLoop.add(nextTask.getTitle());
                        }
                    }
                }


            }


            if (overlap.contains(nextTask)) {

                if (!includeMaintenance) {
                    return true;
                } else {

                    for (int i = 0; i < linkedList.size(); i++) {
                        String str = new String(linkedList.get(i));
                        if (!backup.contains(str)) {
                            backup.add(str);
                        }

                    }
                    linkedList.clear();
                    isCheck[0] = true;
                }

            }

            if (discovered.contains(nextTask)) {
                continue;
            }

            check = topologicalSortRecursive(nextTask,
                    discovered,
                    linkedList,
                    overlap,
                    includeMaintenance,
                    isLoop,
                    isCheck,
                    backup);

        }

        if (check) {

            if (!includeMaintenance) {
                return true;
            }

        }

        if (!linkedList.contains(task.getTitle()) && !backup.contains(task.getTitle())) {
            linkedList.addLast(task.getTitle());
        }

        return false;
    }
}