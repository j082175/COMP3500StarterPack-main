package academy.pocu.comp3500.lab10;

import academy.pocu.comp3500.lab10.project.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
        Map<Task, Integer> inDegrees = new HashMap<>();
        // HashMap<Task, HashSet<Task>> adjacencyList = new HashMap<>();
        for (Task task : tasks) {
            inDegrees.put(task, 0);
            graph.put(task, new ArrayList<>());
            // adjacencyList.put(task, new HashSet<>());
        }
        for (Task task : tasks) {
            for (Task predecessor : task.getPredecessors()) {
                graph.get(predecessor).add(task);
                inDegrees.put(task, inDegrees.get(task) + 1);
            }
        }

/*        for (Task task : tasks) {
            searchDepthFirst2(task, graph, adjacencyList.get(task));
        }*/

        HashSet<Task> discovered = new HashSet<>();
        List<String> list = new LinkedList<>();

        for (Task task : tasks) {
            if (inDegrees.get(task) == 0) {
                List<String> result = searchDepthFirst(task, graph, discovered, includeMaintenance);
                list.addAll(result);
            }
        }

        return list;






        /*// create graph using task dependencies
        Map<Task, List<Task>> graph = new HashMap<>();
        Map<Task, Integer> inDegrees = new HashMap<>();
        HashMap<Task, HashSet<Task>> adjacencyList = new HashMap<>();
        for (Task task : tasks) {
            inDegrees.put(task, 0);
            graph.put(task, new ArrayList<>());
            adjacencyList.put(task, new HashSet<>());
        }
        for (Task task : tasks) {
            for (Task predecessor : task.getPredecessors()) {
                Task title = predecessor;
                graph.get(title).add(task);
                inDegrees.put(task, inDegrees.get(task) + 1);
            }
        }

        for (Task task : tasks) {
            searchDepthFirst2(task, graph, adjacencyList.get(task));
        }*/



/*        // perform topological sort
        List<String> schedule = new ArrayList<>();
        Queue<Task> queue = new LinkedList<>();
        for (Task title : inDegrees.keySet()) {
            if (inDegrees.get(title) == 0 && (includeMaintenance || !isInCycle2(graph, title))) {
                queue.offer(title);
            }
        }

        if (!includeMaintenance) {
            while (!queue.isEmpty()) {
                Task title = queue.poll();
                schedule.add(title.getTitle());
                for (Task nextTitle : graph.get(title)) {
                    inDegrees.put(nextTitle, inDegrees.get(nextTitle) - 1);
                    if (inDegrees.get(nextTitle) == 0 && (includeMaintenance || !isInCycle2(graph, nextTitle))) {
                        queue.offer(nextTitle);
                    }
                }
            }
        } else {
            while (schedule.size() != graph.size()) {
                Task title = queue.poll();
                if (title == null) {

                    for(Task key : inDegrees.keySet()){
                        boolean check = false;
                        if (inDegrees.get(key) == 1) {
                            for (int i = 0; i < key.getPredecessors().size(); i++) {
                                if (schedule.contains(key.getPredecessors().get(i).getTitle())) {
                                    check = true;
                                    break;
                                }
                            }

                            if (check) {
                                title = key;
                                break;
                            }


                        }
                    }
                }


                schedule.add(title.getTitle());
                for (Task nextTitle : graph.get(title)) {
                    inDegrees.put(nextTitle, inDegrees.get(nextTitle) - 1);
                    if (inDegrees.get(nextTitle) == 0 && (includeMaintenance || !isInCycle2(graph, nextTitle))) {
                        queue.offer(nextTitle);
                    }
                }
            }
        }


        return schedule;*/


        //////////////////////////
/*        List<String> result = sortTopologically(tasks, includeMaintenance);
        return result;*/
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
                                    if (overlap.contains(t2) && includeMaintenance) {
                                        break;
                                    }
                                    t2 = t2.getPredecessors().get(0);
                                }

                                if (t2.getTitle().equals(pivot.getTitle())) {
                                    isLoop.add(task.getTitle());
                                    break;
                                }
                            }
                            break;
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

    ////////////////

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

    private static boolean isInCycleMultiple(Map<Task, List<Task>> graph, Task title) {
        Set<Task> visited = new HashSet<>();
        Stack<Task> stack = new Stack<>();
        int count = 0;

        for (int i = 0; i < graph.get(title).size(); i++) {
            Task t = graph.get(title).get(i);
            while (t != title) {
                t = graph.get(t).get(0);
                if (t == title) {
                    ++count;
                    break;
                }
            }
        }

        if (count > 1) {
            return true;
        }

        return false;
    }

    public static List<String> searchDepthFirst(Task node, Map<Task, List<Task>> graph, HashSet<Task> discovered, boolean includeMaintenance) {
        Stack<Task> stack = new Stack<>();
        List<String> list = new LinkedList<>();
        Queue<Task> predecessorCheck = new LinkedList<>();


        stack.push(node);
        discovered.add(node);

        while (!stack.empty()) {
            Task next = stack.pop();

            // System.out.println(next);
/*            if (isInCycle(graph, next) && next.getPredecessors().size() > 1) {
                boolean check = false;
                for (int i = 0; i < next.getPredecessors().size(); i++) {
                    if (list.contains(next.getPredecessors().get(i).getTitle())) {
                        check = true;
                    } else {
                        check = false;
                        break;
                    }
                }

                if (check) {
                    list.add(next.getTitle());
                }

            } else {

            }*/

            list.add(next.getTitle());

            for (Task neighbor : graph.get(next)) {
                boolean isCheck = false;
                boolean isCycle = false;
/*                boolean isMultipleCycle = false;

                if (isInCycle(graph, neighbor)) {
                    isCycle = true;
                    if (!includeMaintenance) {
                        continue;
                    }

                    if (isInCycleMultiple(graph, neighbor)) {
                        isMultipleCycle = true;
                    }
                }*/


                if (!discovered.contains(neighbor)) {
                    if (neighbor.getPredecessors().size() > 1) {

                        if (isInCycle(graph, neighbor)) {
                            isCycle = true;
                            if (!includeMaintenance) {
                                continue;
                            } else {
                                for (Task succ : neighbor.getPredecessors()) {
                                    if (!discovered.contains(succ)) {
                                        if (succ.getPredecessors().size() > 1) {
                                            for (int i = 0; i < succ.getPredecessors().size(); i++) {
                                                if (stack.contains(succ.getPredecessors().get(i)) || !discovered.contains(succ.getPredecessors().get(i))) {
                                                    // isCheck = true;
                                                    discovered.add(succ);
                                                    predecessorCheck.add(succ);
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                        else if (!isCycle) {
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


            if (stack.empty() && predecessorCheck.size() != 0) {
                stack.push(predecessorCheck.poll());
            }

        }

        return list;
    }

    private static boolean isInCycle2(Map<Task, List<Task>> graph, Task title) {
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
                }
            }
        }
        return false;
    }

    public static void searchDepthFirst2(Task node, Map<Task, List<Task>> graph, HashSet<Task> adjacencyListArr)  {
        HashSet<Task> discovered = new HashSet<>();
        Stack<Task> stack = new Stack<>();
        boolean check = false;

        stack.push(node);
        // discovered.add(node);

        while (!stack.empty())  {
            Task next = stack.pop();

            // System.out.println(next.data);
            if (adjacencyListArr.contains(next)) {
                check = true;
            }
            adjacencyListArr.add(next);

            for (Task neighbor : graph.get(next))  {
                if (!discovered.contains(neighbor))  {
                    stack.push(neighbor);
                    discovered.add(neighbor);
                }
            }
        }

        if (!check) {
            adjacencyListArr.remove(node);
        }

    }
}