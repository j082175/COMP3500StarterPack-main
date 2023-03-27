package academy.pocu.comp3500.lab10;

import academy.pocu.comp3500.lab10.project.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Project {
    public static List<String> findSchedule(final Task[] tasks, final boolean includeMaintenance) {
        // create graph using task dependencies
        Map<Task, List<Task>> graph = new HashMap<>();

        for (Task task : tasks) {
            graph.put(task, new ArrayList<>());
        }
        for (Task task : tasks) {
            for (Task predecessor : task.getPredecessors()) {
                graph.get(predecessor).add(task);
            }
        }

        HashSet<Task> discovered = new HashSet<>();
        List<String> list = new LinkedList<>();

        for (Task task : tasks) {
            if (task.getPredecessors().size() == 0) {
                List<String> result = searchDepthFirst(task, graph, discovered, includeMaintenance);
                list.addAll(result);
            }
        }

        return list;
    }
    private static boolean isInCycle(Map<Task, List<Task>> graph, Task title) {
        Set<Task> visited = new HashSet<>();
        Stack<Task> stack = new Stack<>();
        stack.push(title);
        while (!stack.isEmpty()) {
            Task currTitle = stack.pop();

            if (visited.contains(currTitle)) {
                return true;
            }
            visited.add(currTitle);
            for (Task nextTitle : graph.get(currTitle)) {
                if (!visited.contains(nextTitle)) { // only add if not already visited
                    stack.push(nextTitle);
                } else if (nextTitle.equals(title)) {
                    return true;
                }
            }
        }

        return false;
    }
    public static List<String> searchDepthFirst(Task node, Map<Task, List<Task>> graph, HashSet<Task> discovered, boolean includeMaintenance) {
        Stack<Task> stack = new Stack<>();
        List<String> list = new LinkedList<>();

        stack.push(node);
        discovered.add(node);

        while (!stack.empty()) {
            Task next = stack.pop();

            if (!list.contains(next.getTitle())) {
                list.add(next.getTitle());
            }

            for (Task neighbor : graph.get(next)) {
                boolean isCheck = false;

                if (!discovered.contains(neighbor)) {
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
                                if (stack.contains(neighbor.getPredecessors().get(i)) || !discovered.contains(neighbor.getPredecessors().get(i))) {
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
                    discovered.add(neighbor);
                }
            }
        }

        return list;
    }
    public static List<String> searchOnlyDiscovered(Task task, Map<Task, List<Task>> graph, HashSet<Task> discovered) {
        List<Task> list = new LinkedList<>();

        searchOnlyDiscoveredRecursive(task, graph, discovered, list);
        list.add(task);

        List<String> returnList = new LinkedList<>();
        for (Task t : list) {
            returnList.add(0, t.getTitle());
        }

        return returnList;
    }
    public static Task searchOnlyDiscoveredRecursive(Task task, Map<Task, List<Task>> graph, HashSet<Task> discovered, List<Task> list) {
        for (Task t : graph.get(task)) {
            if (discovered.contains(t)) {
                continue;
            }
            discovered.add(task);

            Task task1 = searchOnlyDiscoveredRecursive(t, graph, discovered, list);
            if (!list.contains(task1)) {
                list.add(task1);
            }

        }

        return task;
    }
}