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
            findRecursive(task, discovered, l, includeMaintenance, new HashSet<>(), isCheck, new Task(null, 0));

            list.addAll(l);

        }

        return list;
    }

    private static List<String> findRecursive(final Task tasks, HashSet<Task> discovered, List<String> list, boolean includeMaintenance, HashSet<Task> isSame, boolean[] isCheck, Task pivot) {
        isSame.add(tasks);

        for (Task task : tasks.getPredecessors()) {

            if (isSame.contains(task) && !includeMaintenance) {
/*                for (int i = 0; i < isSame.size(); i++) {
                    list.remove(0);
                }*/
                list.clear();
                return null;
            } else if (isSame.contains(task) && includeMaintenance) {
                isCheck[0] = true;
                return null;
            }

            if (!discovered.contains(task)) {
                discovered.add(task);
                list.add(0, task.getTitle());
                findRecursive(task, discovered, list, includeMaintenance, isSame, isCheck, pivot);

/*                if (list != null) {
                    list.addAll(list);
                } else {
                    list = null;
                }*/


            } else {
                pivot = tasks;
            }

            if (isCheck[0] == true) {
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