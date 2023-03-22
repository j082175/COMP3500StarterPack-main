package academy.pocu.comp3500.lab10;

import academy.pocu.comp3500.lab10.project.Task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Project {
    public static List<String> findSchedule(final Task[] tasks, final boolean includeMaintenance) {


        List<String> result = sortTopologically(tasks, includeMaintenance);
        return result;



        /////////////////////////////////////


/*        List<String> totalList = new LinkedList<>();

        for (Task task : tasks) {
            List<String> resultList = searchBreadthFirst1(task, includeMaintenance, new HashSet<>(), new HashSet<>(), new HashSet<>(), new boolean[]{false});

            if (!totalList.containsAll(resultList)) {
                List<String> mergedList = new ArrayList<>(resultList);
                mergedList.removeAll(totalList);
                totalList.addAll(mergedList);
            }
        }

        return totalList;*/




//////////////////////////////////////////////





        /*HashSet<Task> discovered = new HashSet<>();
        List<String> totalList = new LinkedList<>();

        for (Task task : tasks) {
*//*            if (discovered.contains(task)) {
                continue;
            }*//*

            List<String> l = new LinkedList<>();

            if (!discovered.contains(task)) {
                discovered.add(task);
                l.add(task.getTitle());
            }

            boolean[] isCheck = new boolean[]{false};
            HashSet<Task> isSame = new HashSet<>();
            isSame.add(task);
            findRecursive(task, discovered, l, includeMaintenance, isSame, isCheck, new Task(null, 0));
            isSame.clear();

            totalList.addAll(l);

        }

        return totalList;*/
    }

    private static List<String> findRecursive(final Task tasks, HashSet<Task> discovered, List<String> list, boolean includeMaintenance, HashSet<Task> isSame, boolean[] isCheck, Task pivot) {


        for (Task task : tasks.getPredecessors()) {

            if (isSame.contains(task) && !includeMaintenance) {
                list.clear();
                return null;
            } else if (isSame.contains(task) && includeMaintenance) {
                isCheck[0] = true;
                return null;
            }

            if (!discovered.contains(task)) {
                discovered.add(task);
                list.add(0, task.getTitle());

/*                if (list != null) {
                    list.addAll(list);
                } else {
                    list = null;
                }*/


            } else {
                pivot = tasks;
            }

            if (isCheck[0]) {
                List<String> l1 = new LinkedList<>();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).equals(pivot.getTitle())) {
                        for (int j = 0; j < i; j++) {
                            list.remove(0);
                        }
                        list.addAll(l1);
                        isCheck[0] = false;
                        break;
                    }
                    l1.add(list.get(i));
                }

            }

        }

        for (Task task : tasks.getPredecessors()) {

            if (task.getPredecessors().size() != 0 && !discovered.contains(task.getPredecessors().get(0))) {
                findRecursive(task, discovered, list, includeMaintenance, isSame, isCheck, pivot);
            } else if (task.getPredecessors().size() == 1 && isSame.contains(task.getPredecessors().get(0))) {

                if (!includeMaintenance) {
                    list.clear();
                    return null;
                }

                List<String> l1 = new LinkedList<>();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).equals(pivot.getTitle())) {
                        for (int j = 0; j < i; j++) {
                            list.remove(0);
                        }
                        list.addAll(l1);
                        isCheck[0] = false;
                        break;
                    }
                    l1.add(list.get(i));
                }
            }

        }


        return null;
    }

    private static List<String> searchBreadthFirst(Task task, boolean includeMaintenance) {
        HashSet<Task> discovered = new HashSet<>();
        Stack<Task> stack = new Stack<>();
        List<String> list = new LinkedList<>();
        HashSet<Task> isRingBuffer = new HashSet<>();
        Stack<Task> overTwo = new Stack<>();
        isRingBuffer.add(task);

        stack.push(task);
        discovered.add(task);

        while (!stack.isEmpty()) {

            Task next = stack.pop();

            // System.out.print(next.data + " ");
            if (!list.contains(next.getTitle())) {
                list.add(0, next.getTitle());
            }

            for (Task neighbor : next.getPredecessors()) {
                if (next.getPredecessors().size() > 1) {
                    if (!overTwo.contains(next)) {
                        overTwo.push(next);
                    }
                }

                if (isRingBuffer.contains(neighbor) && !includeMaintenance) {
                    list.clear();
                } else if (isRingBuffer.contains(neighbor)) {
                    Task t = overTwo.pop();
                    int index = list.indexOf(t.getTitle());
                    List<String> backward = list.subList(0, index);
                    List<String> foreward = list.subList(index, list.size());
                    list = foreward;
                    list.addAll(backward);
                }

                if (!discovered.contains(neighbor)) {
                    stack.push(neighbor);
                    discovered.add(next);
                }


            }
        }

        return list;
    }

    private static List<String> searchBreadthFirst1(Task task, boolean includeMaintenance, HashSet<Task> overlap, HashSet<Task> compare, HashSet<Task> discovered, boolean[] isLoop) {
        //HashSet<Task> discovered = new HashSet<>();
        Stack<Task> stack = new Stack<>();
        List<String> list = new LinkedList<>();
        HashSet<Task> isRingBuffer = new HashSet<>();
        Stack<Task> overTwo = new Stack<>();
        isRingBuffer.add(task);

        Task next = task;

        while (next != null) {

            // System.out.print(next.data + " ");


            if (next.getPredecessors().size() > 1 && !overlap.contains(next)) {

                overlap.add(next);
                Stack<List<String>> s = new Stack<>();

                for (Task neighbor : next.getPredecessors()) {
                    List<String> result = searchBreadthFirst1(neighbor, includeMaintenance, overlap, compare, discovered, isLoop);

                    if (isLoop[0]) {
                        list.clear();
                    }

                    if (result != null) {
                        s.add(result);
                    }
                }

                if (!isLoop[0]) {
                    overlap.clear();
                }


                if (!overlap.contains(next)) {
                    if (!list.contains(next.getTitle())) {
                        list.add(0, next.getTitle());
                    }
                }


                if (s.size() != 0) {
                    list.clear();
                }

                while (s.size() != 0) {
                    List<String> result = s.pop();
                    // list.addAll(0, result);

                    list.addAll(result);

                }


                return list;

            }

            if (!list.contains(next.getTitle())) {
                list.add(0, next.getTitle());
            }


            if (overlap.contains(next) && !includeMaintenance) {
                list.clear();
                isLoop[0] = true;
                return list;
            } else if (overlap.contains(next) && includeMaintenance) {
/*                Iterator iter = overlap.iterator();
                Task t = (Task) iter.next();

                int index = list.indexOf(t.getTitle());
                List<String> backward = list.subList(0, index);
                List<String> foreward = list.subList(index, list.size());
                list = foreward;
                list.addAll(backward);*/

            }

            if (next.getPredecessors().size() != 0) {
                next = next.getPredecessors().get(0);
            } else {
                next = null;
            }


        }

        return list;
    }

    private static List<String> searchBreadthFirstRecursive(Task next, boolean includeMaintenance, HashSet<Task> discovered) {

        List<String> list = new LinkedList<>();

        if (discovered.contains(next)) {
            if (!includeMaintenance) {
                return null;
            }
        }

        for (Task neighbor : next.getPredecessors()) {
            if (next.getPredecessors().size() > 1) {
                discovered.add(next);
                List<String> resultList = searchBreadthFirstRecursive(neighbor, includeMaintenance, discovered);
                if (resultList != null) {

                    List<String> mergedList = new ArrayList<>(resultList);
                    mergedList.removeAll(list);
                    list.addAll(mergedList);


                    // list.addAll(resultList);
                } else {
                    return list;
                }
            }
        }

        return list;
    }

    private static LinkedList<String> sortTopologically(Task[] tasks, boolean includeMaintenance) {
        HashSet<Task> discovered = new HashSet<>();
        LinkedList<String> sortedList = new LinkedList<>();
        HashSet<Task> overlap = new HashSet<>();
        HashSet<String> isLoop = new HashSet<>();
        boolean[] isCheck = new boolean[]{false};
        LinkedList<String> backup = new LinkedList<>();
        HashSet<Task> discovered2 = new HashSet<>();

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
                    backup,
                    discovered2);
            overlap.clear();

            if (isCheck[0]) {
                String t = null;

                for (int i = 0; i < sortedList.size(); i++) {
                    if (isLoop.contains(sortedList.get(i))) {
                        t = sortedList.get(i);
                    }
                }


/*                if (sortedList.contains(backup.getLast())) {
                    backup.removeLast();

                    if (backup.size() > sortedList.size()) {
                        for (int i = 0 ; i < backup.size(); i++) {
                            if (sortedList.contains(backup.get(i))) {
                                t = null;
                                break;
                            }
                        }
                    } else {
                        for (int i = 0 ; i < sortedList.size(); i++) {
                            if (backup.contains(sortedList.get(i))) {
                                t = null;
                                break;
                            }
                        }
                    }


                    sortedList.removeAll(backup);
                }*/

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

            isCheck[0] = false;
            isLoop.clear();
            discovered2.clear();
            backup.clear();

        }

        return sortedList;
    }

    private static boolean topologicalSortRecursive(Task task, HashSet<Task> discovered, LinkedList<String> linkedList, HashSet<Task> overlap, boolean includeMaintenance, HashSet<String> isLoop, boolean[] isCheck, LinkedList<String> backup, HashSet<Task> discovered2) {
        discovered.add(task);
        discovered2.add(task);
        boolean check = false;

        for (Task nextTask : task.getPredecessors()) {


            if (nextTask.getPredecessors().size() > 1) {


                if (isLoop.contains(nextTask.getTitle())) {
                    if (!includeMaintenance) {
                        return true;
                    } else {
                        for (int i = 0; i < linkedList.size(); i++) {
                            String str = new String(linkedList.get(i));
                            if (!backup.contains(str)) {
                                backup.add(str);
                            }
                        }
                        backup.add(new String(nextTask.getTitle()));
                        linkedList.clear();
                        isCheck[0] = true;
                    }
                }
                isLoop.add(nextTask.getTitle());

/*                for (Task nextTask2 : nextTask.getPredecessors()) {
                    if (overlap.contains(nextTask2)) {
                        discovered.add(nextTask);
                        if (!linkedList.contains(nextTask.getTitle())) {
                            linkedList.add(nextTask.getTitle());
                        }

                        nextTask = nextTask2;
                        break;
                    }
                }*/



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
/*                if (discovered2.contains(nextTask)) {
                    if (!includeMaintenance) {
                        return true;
                    } else {

                        if (!linkedList.contains(nextTask.getTitle())) {
                            linkedList.add(nextTask.getTitle());
                        }

                        for (int i = 0; i < linkedList.size(); i++) {
                            String str = new String(linkedList.get(i));
                            if (!backup.contains(str)) {
                                backup.add(str);
                            }
                        }
                        linkedList.clear();
                        isCheck[0] = true;
                    }
                }*/
                continue;
            }

            check = topologicalSortRecursive(nextTask,
                    discovered,
                    linkedList,
                    overlap,
                    includeMaintenance,
                    isLoop,
                    isCheck,
                    backup,
                    discovered2);


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