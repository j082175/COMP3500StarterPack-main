package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;

public final class Project {
    private final Map<String, Integer> taskIndexMap = new HashMap<>();
    private final ArrayList<Task> tasksList = new ArrayList<>();
////////////////////////////

    private final Map<Task, List<Task>> graph = new HashMap<>();
    private final Map<String, Map<String, Integer>> backedge = new HashMap<>();
    private final Map<String, Map<String, Integer>> frontedge = new HashMap<>();
    List<List<Integer>>[] adjacencyList;
    private Task[] tasks;
    HashMap<String, Task> hashMap = new HashMap<>();
    ArrayList<Task> startTask = new ArrayList<>();
    // List<int[]>[] adjacencyList = new List[100];

    public Project(final Task[] tasks) {
        int index = 0;
        for (Task task : tasks) {
            taskIndexMap.put(task.getTitle(), index++);
            tasksList.add(task);
        }



        /////////////////
        this.tasks = tasks;
        adjacencyList = new List[tasks.length];
        int count = 0;

        for (Task task : tasks) {
            hashMap.put(task.getTitle(), task);
            graph.put(task, new ArrayList<>());
            if (task.getPredecessors().size() == 0) {
                startTask.add(task);
            }

            backedge.put(task.getTitle(), new HashMap<>());
            frontedge.put(task.getTitle(), new HashMap<>());

            adjacencyList[count] = new LinkedList<>();
            ++count;
        }


        for (Task task : tasks) {
            for (Task predecessor : task.getPredecessors()) {
                graph.get(predecessor).add(task);
                frontedge.get(task.getTitle()).put(predecessor.getTitle(), predecessor.getEstimate());
                backedge.get(predecessor.getTitle()).put(task.getTitle(), 0);
            }
        }

        count = 0;
        for (Task task : tasks) {
            searchBreadthFirst(task, graph, adjacencyList[count]);
            ++count;
        }


        for (int i = 0; i < startTask.size(); i++) {
            // maxFlowEdmondsKarp(tasks.length, i, 6, adjacencyList);
        }


    }

/*    public int findMaxBonusCount(final String taskTitle) {
        int taskIndex = taskIndexMap.get(taskTitle);
        int[][] capacities = buildCapacityMatrix();
        int maxFlow = findMaxFlow(capacities, taskIndex + 1, capacities.length - 1);
        return maxFlow;
    }

    private int[][] buildCapacityMatrix() {
        int n = tasksList.size() + 2;
        int[][] capacities = new int[n][n];

        // Connect start node to source nodes
        for (int i = 0; i < tasksList.size(); i++) {
            Task task = tasksList.get(i);
            if (task.getPredecessors().isEmpty()) {
                capacities[0][i + 1] = task.getEstimate();
            }
        }

        // Connect task nodes to task nodes and end node
        for (int i = 0; i < tasksList.size(); i++) {
            Task task = tasksList.get(i);
            for (Task predecessor : task.getPredecessors()) {
                int j = taskIndexMap.get(predecessor.getTitle()) + 1;
                capacities[j][i + 1] = Integer.MAX_VALUE;
            }
            capacities[i + 1][n - 1] = 0;
        }

        return capacities;
    }

    private int findMaxFlow(int[][] capacities, int source, int sink) {
        int maxFlow = 0;
        int[] parent = new int[capacities.length];

        while (bfs(capacities, source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;

            // find the maximum flow through the path found by BFS
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, capacities[u][v]);
            }

            // update residual capacities of the edges and reverse edges along the path
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                capacities[u][v] -= pathFlow;
                capacities[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    private boolean bfs(int[][] capacities, int source, int sink, int[] parent) {
        boolean[] visited = new boolean[capacities.length];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int next = 0; next < capacities.length; next++) {
                if (!visited[next] && capacities[current][next] > 0) {
                    queue.offer(next);
                    visited[next] = true;
                    parent[next] = current;
                    if (next == sink) {
                        return true;
                    }
                }
            }
        }

        return false;
    }*/


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

/*    public int findMaxBonusCount(final String task) {
        int result = 0;
        for (int i = 0; i < startTask.size(); i++) {
            result = findShortestDistance(startTask.get(i), hashMap.get(task));

            int a = 1;
        }

        return result;
    }*/

    public int findMaxBonusCount(final String task) {
        int min = this.hashMap.get(task).getEstimate();

        int sum = 0;
        int result = 0;
        while (true) {
            // result = findShortestDistance(this.hashMap.get(task), graph, frontedge1, backedge1);
            result = searchOnlyDiscoveredBackwardMinFlux(hashMap.get(task), min, frontedge, backedge);
            if (result == Integer.MAX_VALUE || result == 0) {
                break;
            } else {
                min -= result;
                sum += result;

            }
        }

        for (Task task2 : tasks) {
            backedge.put(task2.getTitle(), new HashMap<>());
            frontedge.put(task2.getTitle(), new HashMap<>());
        }
        for (Task task2 : tasks) {
            for (Task predecessor : task2.getPredecessors()) {
                frontedge.get(task2.getTitle()).put(predecessor.getTitle(), predecessor.getEstimate());
                backedge.get(predecessor.getTitle()).put(task2.getTitle(), 0);
            }
        }

        return sum;
    }


    private boolean isInCycle(Map<Task, List<Task>> graph, Task title) {
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

    private int findShortestDistance(Task s, Map<Task, List<Task>> graph, Map<Task, Map<Task, Integer>> frontedge, Map<Task, Map<Task, Integer>> backedge) {
        HashMap<Task, Integer> distances = new HashMap<>();
        Queue<Task> queue = new LinkedList<>();
        int startFlux = s.getEstimate();

        queue.add(s);
        distances.put(s, 0);
        while (!queue.isEmpty()) {
            Task next = queue.remove();

            // int distance = distances.get(next);

/*            if (next.equals(d)) {
                return startFlux;
                // return distance;
            }*/

            if (next.getPredecessors().size() == 0) {
                return startFlux;
            }

            for (Task neighbor : next.getPredecessors()) {

                if (frontedge.get(next).containsKey(neighbor) && frontedge.get(next).get(neighbor) > 0) {
                    queue.add(neighbor);
                    startFlux = startFlux > frontedge.get(next).get(neighbor) ? frontedge.get(next).get(neighbor) : startFlux;
                    frontedge.get(next).put(neighbor, frontedge.get(next).get(neighbor) - startFlux);
                    backedge.get(neighbor).put(next, backedge.get(neighbor).get(next) + startFlux);
                    break;
                } else if (backedge.get(next).containsKey(neighbor) && backedge.get(next).get(neighbor) > 0) {
                    queue.add(neighbor);
                    startFlux = startFlux > backedge.get(neighbor).get(next) ? backedge.get(neighbor).get(next) : startFlux;
                    frontedge.get(next).put(neighbor, frontedge.get(next).get(neighbor) + startFlux);
                    backedge.get(neighbor).put(next, backedge.get(neighbor).get(next) - startFlux);
                }

/*                if (!distances.containsKey(neighbor)) {
                    queue.add(neighbor);
                    distances.put(neighbor, distance + 1);
                }*/
            }
        }

        return -1;
    }

    private static int findMinimumFlux(Task s) {
        HashMap<Task, Integer> distances = new HashMap<>();
        Stack<Task> queue = new Stack<>();
        int min = Integer.MAX_VALUE;

        queue.push(s);
        distances.put(s, 0);
        while (!queue.isEmpty()) {
            Task next = queue.pop();

            int distance = distances.get(next);

            if (min > next.getEstimate()) {
                min = next.getEstimate();
            }

            if (next.getPredecessors().size() == 0) {
                return min;
            }

            for (Task neighbor : next.getPredecessors()) {
                if (!distances.containsKey(neighbor)) {
                    queue.add(neighbor);
                    distances.put(neighbor, distance + 1);
                }
            }
        }

        return -1;
    }

    private int searchOnlyDiscoveredBackwardMinFlux(Task task, int min, Map<String, Map<String, Integer>> frontedge, Map<String, Map<String, Integer>> backedge) {

        int result = searchOnlyDiscoveredBackwardMinFluxRecursive(task, frontedge, backedge, min, new boolean[]{false});

        return result;
    }

    private int searchOnlyDiscoveredBackwardMinFluxRecursive(Task task, Map<String, Map<String, Integer>> frontedge, Map<String, Map<String, Integer>> backedge, int min, boolean[] isEnd) {
        //discovered.put(task, 0);

/*        Task taskResult = task;
        int maxValue = Integer.MIN_VALUE;
        int result = min;

        result = searchDepthMin(taskResult, history, result);
        taskResult = history[0];

        if (maxValue < result) {
            maxValue = result;
        }*/

        Task next = task;

        if (task.getPredecessors().size() == 0) {
            isEnd[0] = true;
            return min;
        }

        for (Task neighbor : task.getPredecessors()) {

/*            if (isInCycle(graph, neighbor)) {
                continue;
            }*/

            if (frontedge.get(next.getTitle()).containsKey(neighbor.getTitle()) && frontedge.get(next.getTitle()).get(neighbor.getTitle()) > 0) {

                if (min > frontedge.get(next.getTitle()).get(neighbor.getTitle())) {
                    min = frontedge.get(next.getTitle()).get(neighbor.getTitle());
                }

                int result1 = searchOnlyDiscoveredBackwardMinFluxRecursive(neighbor, frontedge, backedge, min, isEnd);
                if (result1 == -1) {
                    return -1;
                } else if (result1 == Integer.MAX_VALUE) {
                    continue;
                }

                min = result1;

                if (result1 <= frontedge.get(next.getTitle()).get(neighbor.getTitle())) {
                    for (Task t1 : graph.get(neighbor)) {
                        frontedge.get(t1.getTitle()).put(neighbor.getTitle(), frontedge.get(t1.getTitle()).get(neighbor.getTitle()) - result1);
                        backedge.get(neighbor.getTitle()).put(t1.getTitle(), backedge.get(neighbor.getTitle()).get(t1.getTitle()) + result1);
                    }

/*                    frontedge.get(next).put(neighbor, frontedge.get(next).get(neighbor) - result1);
                    backedge.get(neighbor).put(next, backedge.get(neighbor).get(next) + result1);*/
                    break;
                } else {
                    for (Task t1 : graph.get(neighbor)) {
                        frontedge.get(t1.getTitle()).put(neighbor.getTitle(), frontedge.get(t1.getTitle()).get(neighbor.getTitle()) - frontedge.get(t1.getTitle()).get(neighbor.getTitle()));
                        backedge.get(neighbor.getTitle()).put(t1.getTitle(), backedge.get(neighbor.getTitle()).get(t1.getTitle()) + frontedge.get(t1.getTitle()).get(neighbor.getTitle()));
                    }
                    min = result1 - frontedge.get(next.getTitle()).get(neighbor.getTitle());

                }


            } else if (backedge.get(next.getTitle()).containsKey(neighbor.getTitle()) && backedge.get(next.getTitle()).get(neighbor.getTitle()) > 0) {

                if (min > backedge.get(next.getTitle()).get(neighbor.getTitle())) {
                    min = backedge.get(next.getTitle()).get(neighbor.getTitle());
                }

                int result1 = searchOnlyDiscoveredBackwardMinFluxRecursive(neighbor, frontedge, backedge, min, isEnd);
                if (result1 == -1) {
                    return -1;
                } else if (result1 == Integer.MAX_VALUE) {
                    return Integer.MAX_VALUE;
                }

                min = result1;

                if (result1 < backedge.get(next.getTitle()).get(neighbor.getTitle())) {
                    for (Task t1 : graph.get(neighbor)) {
                        frontedge.get(t1.getTitle()).put(neighbor.getTitle(), frontedge.get(t1.getTitle()).get(neighbor.getTitle()) + result1);
                        backedge.get(neighbor.getTitle()).put(t1.getTitle(), backedge.get(neighbor.getTitle()).get(t1.getTitle()) - result1);
                    }

                    break;
                } else {
                    for (Task t1 : graph.get(neighbor)) {
                        frontedge.get(t1.getTitle()).put(neighbor.getTitle(), frontedge.get(t1.getTitle()).get(neighbor.getTitle()) + backedge.get(t1.getTitle()).get(neighbor.getTitle()));
                        backedge.get(neighbor.getTitle()).put(t1.getTitle(), backedge.get(neighbor.getTitle()).get(t1.getTitle()) - backedge.get(t1.getTitle()).get(neighbor.getTitle()));
                    }

                    min = result1 - backedge.get(next.getTitle()).get(neighbor.getTitle());

                }


            }

        }

        if (!isEnd[0]) {
            return Integer.MAX_VALUE;
            // return -1;
        }

        return min;
    }

    public static void searchBreadthFirst(Task node, Map<Task, List<Task>> graph, List<List<Integer>> adjacencyList) {
        HashMap<Task, Integer> discovered = new HashMap<>();
        Queue<Task> queue = new LinkedList<>();

        queue.add(node);
        discovered.put(node, 0);

        while (!queue.isEmpty()) {
            Task next = queue.remove();

            if (next != node) {
                List<Integer> m1 = new LinkedList<>();
                m1.add(next.getEstimate());
                adjacencyList.add(m1);
            }

            for (Task neighbor : graph.get(next)) {
                if (!discovered.containsKey(neighbor)) {
                    queue.add(neighbor);
                    discovered.put(next, 0);
                }
            }
        }
    }


}