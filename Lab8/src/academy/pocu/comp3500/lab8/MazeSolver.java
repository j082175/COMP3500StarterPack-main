package academy.pocu.comp3500.lab8;

import academy.pocu.comp3500.lab8.maze.Point;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public final class MazeSolver {
    private static Node root;
    private final static int length = 4;
    private final static int[] xCase = {0, -1, 0, 1};
    private final static int[] yCase = {-1, 0, 1, 0};
    private final static char wall = 'x';
    private final static char past = '!';
    private final static char way = ' ';
    private final static char exit = 'E';

    public static List<Point> findPath(final char[][] maze, final Point start) {
        LinkedList<Point> totalList = new LinkedList<>();
        root = null;

        if (maze[start.getY()][start.getX()] == exit) {
            totalList.add(new Point(start.getX(), start.getY()));
            return totalList;
        }

        if (root == null) {
            ArrayList<Point> a = new ArrayList<>();
            a.add(start);
            root = new Node(a);
        }


        findRecursive(root, maze, totalList);







        return totalList;
    }

    private static boolean findRecursive(Node node, char[][] maze, LinkedList<Point> totalList) {
        int x = node.point.get(node.point.size() - 1).getX();
        int y = node.point.get(node.point.size() - 1).getY();

        int count = 0;
        Queue<Point> pointStorage = new LinkedList<>();

        maze[y][x] = past;

        for (int i = 0; i < length; i++) {
            int afterX = x;
            int afterY = y;

            afterX += xCase[i];
            afterY += yCase[i];

            if (afterY < 0 || afterY >= maze.length && afterX < 0 || afterX >= maze[0].length) {
                return false;
            }

            if (maze[afterY][afterX] != wall && maze[afterY][afterX] != past) {
                if (maze[afterY][afterX] == exit) {
                    totalList.addFirst(new Point(afterX, afterY));
                    return true;
                }

                pointStorage.add(new Point(afterX, afterY));
            } else {
                ++count;
            }
        }

        if (count == 4) {
            node.point.clear();
            return false;
        }

        if (count == 3) {
            node.point.add(pointStorage.peek());
            boolean result = findRecursive(node, maze, totalList);
            if (result) {
                if (node.point.get(0).getX() == totalList.getFirst().getX() && node.point.get(0).getY() == totalList.getFirst().getY()) {
                    return true;
                }

                for (int i = 0; i < node.point.size(); i++) {
                    totalList.addFirst(new Point(node.point.get(node.point.size() - 1 - i).getX(), node.point.get(node.point.size() - 1 - i).getY()));
                }

                return true;
            }

        }

        {
            int size = pointStorage.size();
            for (int i = 0; i < size; i++) {
                node.addChildren(pointStorage.remove());
            }
        }


        {
            int size = node.children.size();
            for (int j = 0; j < size; j++) {
                boolean result = findRecursive(node.getChildrenNode(), maze, totalList);

                if (result) {
                    for (int i = 0; i < node.point.size(); i++) {
                        totalList.addFirst(new Point(node.point.get(node.point.size() - 1 - i).getX(), node.point.get(node.point.size() - 1 - i).getY()));
                    }

                    return true;
                }

                if (node.getChildrenNode().point.size() == 0) {
                    node.children.remove();
                }
            }
        }




        return false;
    }


}