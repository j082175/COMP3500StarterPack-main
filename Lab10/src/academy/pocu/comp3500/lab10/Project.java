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
            if (discovered.contains(task)) {
                continue;
            }

            discovered.add(task);
            list.add(task.getTitle());
            if (task.getPredecessors().size() == 0) {
                continue;
            }

            findRecursive(task, discovered, list, includeMaintenance, new HashSet<>());
        }

        list.sort(String.CASE_INSENSITIVE_ORDER);

        return list;
    }

    private static void findRecursive(final Task tasks, HashSet<Task> discovered, List<String> list, boolean includeMaintenance, HashSet<Task> isSame) {
        isSame.add(tasks);

        for (Task task : tasks.getPredecessors()) {

            if (isSame.contains(task) && !includeMaintenance) {
                for (int i = 0; i < isSame.size(); i++) {
                    list.remove(list.size() - 1);
                }
            }

            if (!discovered.contains(task)) {
                discovered.add(task);
                list.add(task.getTitle());
                findRecursive(task, discovered, list, includeMaintenance, isSame);
            }
        }
    }
}