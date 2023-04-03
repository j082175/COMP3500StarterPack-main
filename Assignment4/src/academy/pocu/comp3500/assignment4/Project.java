package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public final class Project {
    private final Map<Task, List<Task>> graph = new HashMap<>();
    private final Map<String, Map<String, Integer>> frontedge = new HashMap<>();
    private Task[] tasks;
    HashMap<String, Task> hashMap = new HashMap<>();
    ArrayList<Task> startTask = new ArrayList<>();

    public Project(final Task[] tasks) {
        this.tasks = tasks;

        for (Task task : tasks) {
            hashMap.put(task.getTitle(), task);
            graph.put(task, new ArrayList<>());
            if (task.getPredecessors().size() == 0) {
                startTask.add(task);
            }

            frontedge.put(task.getTitle(), new HashMap<>());
        }

        for (Task task : tasks) {
            for (Task predecessor : task.getPredecessors()) {
                graph.get(predecessor).add(task);
                frontedge.get(task.getTitle()).put(predecessor.getTitle(), predecessor.getEstimate());
            }
        }

        Sort.quickSort(startTask);
    }

    public int findTotalManMonths(final String task) {
        int result = -1;
        if (this.hashMap.containsKey(task)) {
            result = searchDepthTotal(this.hashMap.get(task));
        }

        return result;
    }

    public int findMinDuration(final String task) {
        // 직원 1명만 투입가능

        int result = -1;
        if (this.hashMap.containsKey(task)) {
            result = searchOnlyDiscoveredBackwardMin(this.hashMap.get(task));
        }
        return result;
    }

    public int findMaxBonusCount(final String task) {
        int min = this.hashMap.get(task).getEstimate();

        int sum = 0;
        int result = 0;
        while (true) {
            result = searchOnlyDiscoveredBackwardMinFlux(hashMap.get(task), min);
            if (result == Integer.MAX_VALUE || result == 0) {
                break;
            } else {
                min -= result;
                sum += result;
            }
        }

        for (Task task2 : tasks) {
            frontedge.put(task2.getTitle(), new HashMap<>());
        }
        for (Task task2 : tasks) {
            for (Task predecessor : task2.getPredecessors()) {
                frontedge.get(task2.getTitle()).put(predecessor.getTitle(), predecessor.getEstimate());
            }
        }

        return sum;
    }

    private int searchOnlyDiscoveredBackwardMin(Task task) {
        HashMap<Task, Integer> discovered = new HashMap<>();
        int max = Integer.MIN_VALUE;
        Task[] history = new Task[]{null};

        for (Task t : task.getPredecessors()) {

            if (discovered.containsKey(t)) {
                continue;
            }

            discovered.put(task, 0);
            int result = searchOnlyDiscoveredBackwardMinRecursive(t, discovered, history, task.getEstimate());
            if (max < result) {
                max = result;
            }

        }

        if (max == Integer.MIN_VALUE) {
            max = task.getEstimate();
        }

        return max;
    }

    private int searchOnlyDiscoveredBackwardMinRecursive(Task task, HashMap<Task, Integer> discovered, Task[] history, int min) {
        discovered.put(task, 0);

        Task taskResult = task;
        int maxValue = Integer.MIN_VALUE;
        int result = min;

        result = searchDepthMin(taskResult, history, result);
        taskResult = history[0];

        if (maxValue < result) {
            maxValue = result;
        }

        if (taskResult != null && taskResult.getPredecessors().size() > 1) {
            for (Task t : taskResult.getPredecessors()) {

                int result1 = searchOnlyDiscoveredBackwardMinRecursive(t, discovered, history, result);
                if (maxValue < result1) {
                    maxValue = result1;
                }
            }

        }

        return maxValue;
    }

    private int searchDepthTotal(Task task) {
        HashMap<Task, Integer> discovered = new HashMap<>();
        Stack<Task> stack = new Stack<>();
        int total = 0;

        stack.push(task);
        discovered.put(task, 0);

        while (!stack.empty()) {
            Task next = stack.pop();

            total += next.getEstimate();

            for (Task neighbor : next.getPredecessors()) {
                if (!discovered.containsKey(neighbor)) {
                    stack.push(neighbor);
                    discovered.put(neighbor, 0);
                }
            }
        }

        return total;
    }

    private int searchDepthMin(Task task, Task[] taskHistory, int min) {
        HashMap<Task, Integer> discovered = new HashMap<>();
        Stack<Task> stack = new Stack<>();
        Task next = null;
        int total = min;

        stack.push(task);
        discovered.put(task, 0);

        while (!stack.empty()) {
            next = stack.pop();

            total += next.getEstimate();
            // total[0] += next.getEstimate();

            if (next.getPredecessors().size() == 1) {
                if (!discovered.containsKey(next.getPredecessors().get(0))) {
                    stack.push(next.getPredecessors().get(0));
                    discovered.put(next.getPredecessors().get(0), 0);
                }
            }

        }

        if (task.getPredecessors().size() == 0 && next == task) {
            taskHistory[0] = null;
        } else {
            taskHistory[0] = next;
        }

        return total;
    }

    private int searchOnlyDiscoveredBackwardMinFlux(Task task, int min) {

        int result = searchOnlyDiscoveredBackwardMinFluxRecursive(task, min, task, new boolean[]{false});

        return result;
    }

    private int searchOnlyDiscoveredBackwardMinFluxRecursive(Task task, int min, Task root, boolean[] isEnd) {

        Task next = task;

        if (task.getPredecessors().size() == 0) {
            isEnd[0] = true;
            return min;
        }

        PriorityQueue<Task> queue = new PriorityQueue<>(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.getEstimate() <= o2.getEstimate() ? 1 : -1;
            }
        });
        for (Task neighbor : task.getPredecessors()) {
            queue.add(neighbor);
        }

        while (!queue.isEmpty()) {
            Task neighbor = queue.poll();

            if (frontedge.get(next.getTitle()).containsKey(neighbor.getTitle()) && frontedge.get(next.getTitle()).get(neighbor.getTitle()) > 0) {

                if (min > frontedge.get(next.getTitle()).get(neighbor.getTitle())) {
                    min = frontedge.get(next.getTitle()).get(neighbor.getTitle());
                }

                int result1 = searchOnlyDiscoveredBackwardMinFluxRecursive(neighbor, min, root, isEnd);
                if (result1 == Integer.MAX_VALUE) {
                    continue;
                }

                min = result1;

                if (result1 <= frontedge.get(next.getTitle()).get(neighbor.getTitle())) {
                    for (Task t1 : graph.get(neighbor)) {
                        frontedge.get(t1.getTitle()).put(neighbor.getTitle(), frontedge.get(t1.getTitle()).get(neighbor.getTitle()) - result1);
                    }

                    break;
                } else {
                    for (Task t1 : graph.get(neighbor)) {
                        frontedge.get(t1.getTitle()).put(neighbor.getTitle(), frontedge.get(t1.getTitle()).get(neighbor.getTitle()) - frontedge.get(t1.getTitle()).get(neighbor.getTitle()));
                    }
                    min = result1 - frontedge.get(next.getTitle()).get(neighbor.getTitle());

                }
            }
        }

        if (!isEnd[0]) {
            return Integer.MAX_VALUE;
        }

        return min;
    }
}