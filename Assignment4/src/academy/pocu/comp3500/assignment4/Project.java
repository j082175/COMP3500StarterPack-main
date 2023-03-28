package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public final class Project {
    private Map<Task, List<Task>> graph = new HashMap<>();
    private Task[] tasks;

    public Project(final Task[] tasks) {
        this.tasks = tasks;

        for (Task task : tasks) {
            graph.put(task, new ArrayList<>());
        }
        for (Task task : tasks) {
            for (Task predecessor : task.getPredecessors()) {
                graph.get(predecessor).add(task);
            }
        }
    }

    public int findTotalManMonths(final String task) {
        HashMap<Task, Integer> discovered = new HashMap<>();
        int result = 0;

        for (Task t : tasks) {
            if (t.getTitle().equals(task)) {
                result = searchOnlyDiscoveredBackwardTotal(t, discovered);
            }
        }

        return result;
    }

    public int findMinDuration(final String task) {
        // 직원 1명만 투입가능

        HashMap<Task, Integer> discovered = new HashMap<>();
        int result = 0;

        for (Task t : tasks) {
            if (t.getTitle().equals(task)) {
                result = searchOnlyDiscoveredBackwardMin(t, discovered);
            }
        }

        return result;
    }

    public int findMaxBonusCount(final String task) {
        return -1;
    }

    private static boolean isInCycle(Map<Task, List<Task>> graph, Task title) {
        HashMap<Task, Integer> visited = new HashMap<>();
        Stack<Task> stack = new Stack<>();
        stack.push(title);
        while (!stack.isEmpty()) {
            Task currTitle = stack.pop();

            if (visited.containsKey(currTitle)) {
                return true;
            }
            visited.put(currTitle, 0);
            for (Task nextTitle : graph.get(currTitle)) {
                if (!visited.containsKey(nextTitle)) { // only add if not already visited
                    stack.push(nextTitle);
                } else if (nextTitle.equals(title)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static List<String> searchDepthFirst(Task node, Map<Task, List<Task>> graph, HashMap<Task, Integer> discovered, boolean includeMaintenance, String goal, int[] total) {
        Stack<Task> stack = new Stack<>();
        List<String> list = new LinkedList<>();

        stack.push(node);
        discovered.put(node, 0);

        while (!stack.empty()) {
            Task next = stack.pop();

            if (!list.contains(next.getTitle())) {
                list.add(next.getTitle());
                total[0] += next.getEstimate();
                if (next.getTitle().equals(goal)) {
                    break;
                }
            }

            for (Task neighbor : graph.get(next)) {
                boolean isCheck = false;

                if (!discovered.containsKey(neighbor)) {
                    if (neighbor.getPredecessors().size() > 1) {

                        if (isInCycle(graph, neighbor)) {
                            if (!includeMaintenance) {
                                continue;
                            } else {
                                List<String> result = searchOnlyDiscovered(neighbor, graph, discovered);
                                list.addAll(result);
                            }
                        } else {
                            for (int i = 0; i < neighbor.getPredecessors().size(); i++) {
                                if (stack.contains(neighbor.getPredecessors().get(i)) || !discovered.containsKey(neighbor.getPredecessors().get(i))) {
                                    isCheck = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (isCheck) {
                        continue;
                    }

                    stack.push(neighbor);
                    discovered.put(neighbor, 0);
                }
            }
        }

        return list;
    }

    public static List<String> searchOnlyDiscovered(Task task, Map<Task, List<Task>> graph, HashMap<Task, Integer> discovered) {
        List<Task> list = new LinkedList<>();

        searchOnlyDiscoveredRecursive(task, graph, discovered, list);
        list.add(task);

        List<String> returnList = new LinkedList<>();
        for (Task t : list) {
            returnList.add(0, t.getTitle());
        }

        return returnList;
    }

    public static Task searchOnlyDiscoveredRecursive(Task task, Map<Task, List<Task>> graph, HashMap<Task, Integer> discovered, List<Task> list) {
        for (Task t : graph.get(task)) {
            if (discovered.containsKey(t)) {
                continue;
            }
            discovered.put(task, 0);

            Task task1 = searchOnlyDiscoveredRecursive(t, graph, discovered, list);
            if (!list.contains(task1)) {
                list.add(task1);
            }

        }

        return task;
    }

    public static int searchOnlyDiscoveredBackwardMin(Task task, HashMap<Task, Integer> discovered) {
        int min = Integer.MAX_VALUE;
        int[] duration = new int[]{0};
        for (Task t : task.getPredecessors()) {
            if (discovered.containsKey(t)) {
                continue;
            }
            discovered.put(task, 0);

            searchOnlyDiscoveredBackwardMinRecursive(t, discovered, duration, new int[]{0});
            if (duration[0] < min) {
                min = duration[0];
            }

            duration[0] = 0;
        }

        return min;
    }

    public static int searchOnlyDiscoveredBackwardMinRecursive(Task task, HashMap<Task, Integer> discovered, int[] duration, int[] history) {
        discovered.put(task, 0);
        duration[0] += task.getEstimate();

        for (Task t : task.getPredecessors()) {
            if (discovered.containsKey(t)) {
                continue;
            }
            history[0] += searchOnlyDiscoveredBackwardMinRecursive(t, discovered, duration, history);
        }

        return task.getEstimate();
    }

    public static int searchOnlyDiscoveredBackwardTotal(Task task, HashMap<Task, Integer> discovered) {
        int[] duration = new int[]{0};
        for (Task t : task.getPredecessors()) {
            if (discovered.containsKey(t)) {
                continue;
            }
            discovered.put(task, 0);

            searchOnlyDiscoveredBackwardTotalRecursive(t, discovered, duration);
        }

        duration[0] += task.getEstimate();
        return duration[0];
    }

    public static void searchOnlyDiscoveredBackwardTotalRecursive(Task task, HashMap<Task, Integer> discovered, int[] duration) {
        discovered.put(task, 0);
        duration[0] += task.getEstimate();

        for (Task t : task.getPredecessors()) {
            if (discovered.containsKey(t)) {
                continue;
            }
            searchOnlyDiscoveredBackwardTotalRecursive(t, discovered, duration);
        }

    }
}