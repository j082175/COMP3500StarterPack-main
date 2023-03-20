package academy.pocu.comp3500.lab10;

import academy.pocu.comp3500.lab10.project.Task;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Project {
    public static List<String> findSchedule(final Task[] tasks, final boolean includeMaintenance) {

        HashSet<Task> discovered = new HashSet<>();
        List<String> list = new LinkedList<>();

        for (Task task : tasks) {
/*            if (discovered.contains(task)) {
                continue;
            }*/

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

            list.addAll(l);

        }

        return list;
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
}