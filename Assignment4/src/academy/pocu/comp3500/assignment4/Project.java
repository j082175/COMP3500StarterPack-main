package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public final class Project {
    private final Map<Task, List<Task>> graph = new HashMap<>();
    private final Map<String, Map<String, Integer>> backedge = new HashMap<>();
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

            backedge.put(task.getTitle(), new HashMap<>());
            frontedge.put(task.getTitle(), new HashMap<>());
        }

/*        for (Task task : tasks) {
            graph.put(task, new ArrayList<>());
        }*/
        for (Task task : tasks) {
            for (Task predecessor : task.getPredecessors()) {
                graph.get(predecessor).add(task);
                frontedge.get(predecessor.getTitle()).put(task.getTitle(), predecessor.getEstimate());
                backedge.get(task.getTitle()).put(predecessor.getTitle(), 0);
            }
        }
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
        int totalFlux = 0;

        // 최단거리인 노드부터 찾기
        ArrayList<Task> startTaskBackup = new ArrayList<>();
        for (int j = 0; j < startTask.size(); j++) {
            startTaskBackup.add(startTask.get(j));
        }

        while (startTaskBackup.size() != 0) {
            int index = -1;
            {
                int maxValue = Integer.MIN_VALUE;
                for (int i = 0; i < startTaskBackup.size(); i++) {
                    if (maxValue < startTaskBackup.get(i).getEstimate()) {
                        maxValue = startTaskBackup.get(i).getEstimate();
                        index = i;
                    }
                }

            }

            while (true) {
                HashMap<String, Integer> distances = new HashMap<>();
                int result = findShortestDistance(startTaskBackup.get(index), task, distances);
                // int result = findShortestDistance2(startTaskBackup.get(index), task, distances);
                if (result == -1) {
                    break;
                }

                // 최솟값찾기
                int min = findMinimum(startTaskBackup.get(index), task, distances, startTaskBackup.get(index).getEstimate());

/*                for (Task task2 : tasks) {
                    for (Task predecessor : task2.getPredecessors()) {
                        frontedge.get(predecessor.getTitle()).put(task2.getTitle(), min);
                    }
                }*/

                reviseGraph(startTaskBackup.get(index), task, distances, min);

                totalFlux += min;
                int a = 1;

                if (min >= startTaskBackup.get(index).getEstimate()) {
                    break;
                }
            }

            startTaskBackup.remove(index);
        }


        if (hashMap.get(task).getEstimate() < totalFlux) {
            totalFlux = hashMap.get(task).getEstimate();
        }

        for (Task task2 : tasks) {
            for (Task predecessor : task2.getPredecessors()) {
                frontedge.get(predecessor.getTitle()).put(task2.getTitle(), predecessor.getEstimate());
                backedge.get(task2.getTitle()).put(predecessor.getTitle(), 0);
            }
        }

        return totalFlux;


/*        Map<String, Map<String, Integer>> backedge1 = new HashMap<>(backedge);
        Map<String, Map<String, Integer>> frontedge1 = new HashMap<>(frontedge);

        int min = this.hashMap.get(task).getEstimate();

        int sum = 0;
        int result = 0;
        while (true) {
            // result = findShortestDistance(this.hashMap.get(task), graph, frontedge1, backedge1);
            result = searchOnlyDiscoveredBackwardMinFlux(hashMap.get(task), min, frontedge1, backedge1);
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

*//*        for (Task task : tasks) {
            graph.put(task, new ArrayList<>());
        }*//*
        for (Task task2 : tasks) {
            for (Task predecessor : task2.getPredecessors()) {
                frontedge.get(task2.getTitle()).put(predecessor.getTitle(), predecessor.getEstimate());
                backedge.get(predecessor.getTitle()).put(task2.getTitle(), 0);
            }
        }

        return sum;*/
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

    public static int searchOnlyDiscoveredBackwardMin(Task task) {
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

    public static int searchOnlyDiscoveredBackwardMinRecursive(Task task, HashMap<Task, Integer> discovered, Task[] history, int min) {
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

    public static int searchDepthTotal(Task task) {
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

    public static int searchDepthMin(Task task, Task[] taskHistory, int min) {
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

    public static int findShortestDistance(Task s, Map<Task, List<Task>> graph, Map<Task, Map<Task, Integer>> frontedge, Map<Task, Map<Task, Integer>> backedge) {
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

    public static int findMinimumFlux(Task s) {
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

    public int searchOnlyDiscoveredBackwardMinFlux(Task task, int min, Map<String, Map<String, Integer>> frontedge, Map<String, Map<String, Integer>> backedge) {

        int result = searchOnlyDiscoveredBackwardMinFluxRecursive(task, frontedge, backedge, min, task, graph, new boolean[]{false});

        return result;
    }

    public int searchOnlyDiscoveredBackwardMinFluxRecursive(Task task, Map<String, Map<String, Integer>> frontedge, Map<String, Map<String, Integer>> backedge, int min, Task root, Map<Task, List<Task>> graph, boolean[] isEnd) {
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

            if (frontedge.get(next.getTitle()).containsKey(neighbor.getTitle()) && frontedge.get(next.getTitle()).get(neighbor.getTitle()) > 0) {

                if (min > frontedge.get(next.getTitle()).get(neighbor.getTitle())) {
                    min = frontedge.get(next.getTitle()).get(neighbor.getTitle());
                }

                int result1 = searchOnlyDiscoveredBackwardMinFluxRecursive(neighbor, frontedge, backedge, min, root, graph, isEnd);
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

                int result1 = searchOnlyDiscoveredBackwardMinFluxRecursive(neighbor, frontedge, backedge, min, root, graph, isEnd);
                if (result1 == -1) {
                    return -1;
                } else if (result1 == Integer.MAX_VALUE) {
                    return Integer.MAX_VALUE;
                }

                min = result1;

                if (result1 < backedge.get(next.getTitle()).get(neighbor.getTitle())) {
                    for (Task t1 : graph.get(neighbor)) {
                        backedge.get(t1.getTitle()).put(neighbor.getTitle(), backedge.get(t1.getTitle()).get(neighbor.getTitle()) - result1);
                        frontedge.get(neighbor.getTitle()).put(t1.getTitle(), frontedge.get(neighbor.getTitle()).get(t1.getTitle()) + result1);
                    }

                    break;
                } else {
                    for (Task t1 : graph.get(neighbor)) {
                        backedge.get(t1.getTitle()).put(neighbor.getTitle(), backedge.get(t1.getTitle()).get(neighbor.getTitle()) - backedge.get(t1.getTitle()).get(neighbor.getTitle()));
                        frontedge.get(neighbor.getTitle()).put(t1.getTitle(), frontedge.get(neighbor.getTitle()).get(t1.getTitle()) + backedge.get(t1.getTitle()).get(neighbor.getTitle()));
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

    private int findMinimum(Task s, String d, HashMap<String, Integer> distances, int min) {
        Queue<Task> queue = new LinkedList<>();

        queue.add(s);

        while (!queue.isEmpty()) {
            Task next = queue.remove();

            if (next.getTitle().equals(d)) {

                if (min > next.getEstimate()) {
                    min = next.getEstimate();
                }
                return min;
            }

            for (Task neighbor : graph.get(next)) {
                if (distances.containsKey(neighbor.getTitle())) {
                    queue.add(neighbor);

                    if (frontedge.get(next.getTitle()).containsKey(neighbor.getTitle()) && frontedge.get(next.getTitle()).get(neighbor.getTitle()) > 0) {

                        if (min > frontedge.get(next.getTitle()).get(neighbor.getTitle())) {
                            min = frontedge.get(next.getTitle()).get(neighbor.getTitle());
                        }

                    } else if (backedge.get(next.getTitle()).containsKey(neighbor.getTitle()) && backedge.get(next.getTitle()).get(neighbor.getTitle()) > 0) {

                        if (min > backedge.get(next.getTitle()).get(neighbor.getTitle())) {
                            min = backedge.get(next.getTitle()).get(neighbor.getTitle());
                        }

                    }

                }
            }

            for (Task neighbor : next.getPredecessors()) {
                if (distances.containsKey(neighbor.getTitle())) {
                    queue.add(neighbor);

                    if (frontedge.get(next.getTitle()).containsKey(neighbor.getTitle()) && frontedge.get(next.getTitle()).get(neighbor.getTitle()) > 0) {

                        if (min > frontedge.get(next.getTitle()).get(neighbor.getTitle())) {
                            min = frontedge.get(next.getTitle()).get(neighbor.getTitle());
                        }

                    } else if (backedge.get(next.getTitle()).containsKey(neighbor.getTitle()) && backedge.get(next.getTitle()).get(neighbor.getTitle()) > 0) {

                        if (min > backedge.get(next.getTitle()).get(neighbor.getTitle())) {
                            min = backedge.get(next.getTitle()).get(neighbor.getTitle());
                        }

                    }

                }
            }
        }

        return -1;
    }

    private int findShortestDistance(Task s, String d, HashMap<String, Integer> distances) {
        HashMap<Task, Task> history = new HashMap<>();
        Queue<Task> queue = new LinkedList<>();
        int min = Integer.MAX_VALUE;
        queue.add(s);
        distances.put(s.getTitle(), 0);
        while (!queue.isEmpty()) {
            Task next = queue.remove();
            int distance = distances.get(next.getTitle());

            if (next.getTitle().equals(d)) {

                distances.clear();
                Task t = next;
                while (t != null) {
                    distances.put(t.getTitle(), 0);
                    t = history.get(t);
                    int a = 1;
                }

                return distance;
            }

            boolean check = false;

            for (Task neighbor : graph.get(next)) {
                if (!distances.containsKey(neighbor.getTitle())) {
                    history.put(neighbor, next);

                    if (frontedge.get(next.getTitle()).containsKey(neighbor.getTitle()) && frontedge.get(next.getTitle()).get(neighbor.getTitle()) > 0) {
                        queue.add(neighbor);
                        distances.put(neighbor.getTitle(), distance + 1);
                        check = true;
                    } else if (backedge.get(next.getTitle()).containsKey(neighbor.getTitle()) && backedge.get(next.getTitle()).get(neighbor.getTitle()) > 0) {
                        queue.add(neighbor);
                        distances.put(neighbor.getTitle(), distance + 1);
                        check = true;
                    }

                }
            }

            if (check) {
                continue;
            }

            for (Task neighbor : next.getPredecessors()) {
                if (!distances.containsKey(neighbor.getTitle())) {
                    history.put(neighbor, next);

                    if (frontedge.get(next.getTitle()).containsKey(neighbor.getTitle()) && frontedge.get(next.getTitle()).get(neighbor.getTitle()) > 0) {
                        queue.add(neighbor);
                        distances.put(neighbor.getTitle(), distance + 1);

                    } else if (backedge.get(next.getTitle()).containsKey(neighbor.getTitle()) && backedge.get(next.getTitle()).get(neighbor.getTitle()) > 0) {
                        queue.add(neighbor);
                        distances.put(neighbor.getTitle(), distance + 1);

                    }

                }
            }
        }

        return -1;
    }

    private int reviseGraph(Task s, String d, HashMap<String, Integer> distances, int min) {
        Queue<Task> queue = new LinkedList<>();

        queue.add(s);
        while (!queue.isEmpty()) {
            Task currentTask = queue.remove();
            int distance = distances.get(currentTask.getTitle());

            if (currentTask.getTitle().equals(d)) {
                return distance;
            }

            for (Task neighbor : graph.get(currentTask)) {
                if (distances.containsKey(neighbor.getTitle())) {
                    queue.add(neighbor);


                    if (frontedge.get(currentTask.getTitle()).containsKey(neighbor.getTitle()) && frontedge.get(currentTask.getTitle()).get(neighbor.getTitle()) > 0) {

                        if (min <= frontedge.get(currentTask.getTitle()).get(neighbor.getTitle())) {

/*                            for (Task next : neighbor.getPredecessors()) {
                                frontedge.get(next.getTitle()).put(neighbor.getTitle(), frontedge.get(next.getTitle()).get(neighbor.getTitle()) - min);
                                backedge.get(neighbor.getTitle()).put(next.getTitle(), backedge.get(neighbor.getTitle()).get(next.getTitle()) + min);
                            }*/

                            for (Task next : graph.get(currentTask)) {
                                frontedge.get(currentTask.getTitle()).put(next.getTitle(), frontedge.get(currentTask.getTitle()).get(next.getTitle()) - min);

                            }

                            for (Task next2 : currentTask.getPredecessors()) {
                                if (distances.containsKey(next2.getTitle())) {
                                    backedge.get(currentTask.getTitle()).put(next2.getTitle(), backedge.get(currentTask.getTitle()).get(next2.getTitle()) + min);
                                }

                            }

/*                            frontedge.get(currentTask.getTitle()).put(neighbor.getTitle(), frontedge.get(currentTask.getTitle()).get(neighbor.getTitle()) - min);
                            backedge.get(neighbor.getTitle()).put(currentTask.getTitle(), backedge.get(neighbor.getTitle()).get(currentTask.getTitle()) + min);*/

                        } else {

/*                            for (Task next : neighbor.getPredecessors()) {
                                frontedge.get(next.getTitle()).put(neighbor.getTitle(), frontedge.get(next.getTitle()).get(neighbor.getTitle()) - frontedge.get(next.getTitle()).get(neighbor.getTitle()));
                                backedge.get(neighbor.getTitle()).put(next.getTitle(), backedge.get(neighbor.getTitle()).get(next.getTitle()) + frontedge.get(next.getTitle()).get(neighbor.getTitle()));
                            }*/

                            for (Task next : graph.get(currentTask)) {
                                frontedge.get(currentTask.getTitle()).put(next.getTitle(), frontedge.get(currentTask.getTitle()).get(next.getTitle()) - frontedge.get(currentTask.getTitle()).get(next.getTitle()));

                            }

                            for (Task next2 : currentTask.getPredecessors()) {
                                if (distances.containsKey(next2.getTitle())) {
                                    backedge.get(currentTask.getTitle()).put(next2.getTitle(), backedge.get(currentTask.getTitle()).get(next2.getTitle()) + frontedge.get(next2.getTitle()).get(currentTask.getTitle()));
                                }

                            }

/*                            frontedge.get(currentTask.getTitle()).put(neighbor.getTitle(), frontedge.get(currentTask.getTitle()).get(neighbor.getTitle()) - frontedge.get(currentTask.getTitle()).get(neighbor.getTitle()));
                            backedge.get(neighbor.getTitle()).put(currentTask.getTitle(), backedge.get(neighbor.getTitle()).get(currentTask.getTitle()) + frontedge.get(currentTask.getTitle()).get(neighbor.getTitle()));*/
                        }


                    } else if (backedge.get(currentTask.getTitle()).containsKey(neighbor.getTitle()) && backedge.get(currentTask.getTitle()).get(neighbor.getTitle()) > 0) {

                        if (min < backedge.get(currentTask.getTitle()).get(neighbor.getTitle())) {

/*                            for (Task next : neighbor.getPredecessors()) {
                                backedge.get(next.getTitle()).put(neighbor.getTitle(), backedge.get(next.getTitle()).get(neighbor.getTitle()) - min);
                                frontedge.get(neighbor.getTitle()).put(next.getTitle(), frontedge.get(neighbor.getTitle()).get(next.getTitle()) + min);
                            }*/

                            for (Task next : graph.get(currentTask)) {
                                backedge.get(currentTask.getTitle()).put(next.getTitle(), backedge.get(currentTask.getTitle()).get(next.getTitle()) - min);

                            }

                            for (Task next2 : currentTask.getPredecessors()) {
                                if (distances.containsKey(next2.getTitle())) {
                                    frontedge.get(currentTask.getTitle()).put(next2.getTitle(), frontedge.get(currentTask.getTitle()).get(next2.getTitle()) + min);
                                }

                            }

/*                            backedge.get(currentTask.getTitle()).put(neighbor.getTitle(), backedge.get(currentTask.getTitle()).get(neighbor.getTitle()) - min);
                            frontedge.get(neighbor.getTitle()).put(currentTask.getTitle(), frontedge.get(neighbor.getTitle()).get(currentTask.getTitle()) + min);*/

                            break;
                        } else {

/*                            for (Task next : neighbor.getPredecessors()) {
                                backedge.get(next.getTitle()).put(neighbor.getTitle(), backedge.get(next.getTitle()).get(neighbor.getTitle()) - backedge.get(next.getTitle()).get(neighbor.getTitle()));
                                frontedge.get(neighbor.getTitle()).put(next.getTitle(), frontedge.get(neighbor.getTitle()).get(next.getTitle()) + backedge.get(next.getTitle()).get(neighbor.getTitle()));
                            }*/

                            for (Task next : graph.get(currentTask)) {
                                backedge.get(currentTask.getTitle()).put(next.getTitle(), backedge.get(currentTask.getTitle()).get(next.getTitle()) - backedge.get(currentTask.getTitle()).get(next.getTitle()));

                            }

                            for (Task next2 : currentTask.getPredecessors()) {
                                if (distances.containsKey(next2.getTitle())) {
                                    frontedge.get(currentTask.getTitle()).put(next2.getTitle(), frontedge.get(currentTask.getTitle()).get(next2.getTitle()) + backedge.get(next2.getTitle()).get(currentTask.getTitle()));
                                }

                            }

/*                            backedge.get(currentTask.getTitle()).put(neighbor.getTitle(), backedge.get(currentTask.getTitle()).get(neighbor.getTitle()) - backedge.get(currentTask.getTitle()).get(neighbor.getTitle()));
                            frontedge.get(neighbor.getTitle()).put(currentTask.getTitle(), frontedge.get(neighbor.getTitle()).get(currentTask.getTitle()) + backedge.get(currentTask.getTitle()).get(neighbor.getTitle()));*/
                        }

                    }
                }
            }
        }

        return -1;
    }

    private int findShortestDistance2(Task s, String d, HashMap<String, Integer> distances) {
        HashMap<Task, Task> history = new HashMap<>();
        Queue<Task> queue = new LinkedList<>();
        int min = Integer.MAX_VALUE;
        queue.add(s);
        distances.put(s.getTitle(), 0);
        while (!queue.isEmpty()) {
            Task next = queue.remove();
            int distance = distances.get(next.getTitle());

            if (next.getTitle().equals(d)) {

                distances.clear();
                Task t = next;
                while (t != null) {
                    distances.put(t.getTitle(), 0);
                    t = history.get(t);
                    int a = 1;
                }

                return distance;
            }

            for (Task neighbor : graph.get(next)) {
                if (!distances.containsKey(neighbor.getTitle())) {
                    history.put(neighbor, next);
                    queue.add(neighbor);
                    distances.put(neighbor.getTitle(), distance + 1);

                }
            }

        }

        return -1;
    }

    private int findMinimum2(Task s, String d, HashMap<String, Integer> distances, int min) {
        Queue<Task> queue = new LinkedList<>();

        queue.add(s);

        while (!queue.isEmpty()) {
            Task next = queue.remove();

            if (next.getTitle().equals(d)) {

                if (min > next.getEstimate()) {
                    min = next.getEstimate();
                }
                return min;
            }

            for (Task neighbor : graph.get(next)) {
                if (distances.containsKey(neighbor.getTitle())) {
                    queue.add(neighbor);

                    if (frontedge.get(next.getTitle()).containsKey(neighbor.getTitle()) && frontedge.get(next.getTitle()).get(neighbor.getTitle()) > 0) {

                        if (min > frontedge.get(next.getTitle()).get(neighbor.getTitle())) {
                            min = frontedge.get(next.getTitle()).get(neighbor.getTitle());
                        }

                    } else if (backedge.get(next.getTitle()).containsKey(neighbor.getTitle()) && backedge.get(next.getTitle()).get(neighbor.getTitle()) > 0) {

                        if (min > backedge.get(next.getTitle()).get(neighbor.getTitle())) {
                            min = backedge.get(next.getTitle()).get(neighbor.getTitle());
                        }

                    }

                }
            }
        }

        return -1;
    }
}