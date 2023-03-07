package academy.pocu.comp3500.lab8;

import academy.pocu.comp3500.lab8.maze.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class MazeSolver {
    private static Node ROOT;
    private final static int LENGTH = 4;
    private final static int[] X_CASE = {0, -1, 0, 1};
    private final static int[] Y_CASE = {-1, 0, 1, 0};
    private final static char WALL = 'x';
    private final static char PAST = '!';
    private final static char WAY = ' ';
    private final static char EXIT = 'E';

    public static List<Point> findPath(final char[][] maze, final Point start) {
        LinkedList<Point> totalList = new LinkedList<>();
        ROOT = null;

        if (maze[start.getY()][start.getX()] == EXIT) {
            totalList.add(new Point(start.getX(), start.getY()));
            return totalList;
        }

        if (ROOT == null) {
            ArrayList<Point> a = new ArrayList<>();
            a.add(start);
            ROOT = new Node(a);
        }


        findRecursive(ROOT, maze, totalList);







        return totalList;
    }

    private static boolean findRecursive(Node node, char[][] maze, LinkedList<Point> totalList) {
        int x = node.point.get(node.point.size() - 1).getX();
        int y = node.point.get(node.point.size() - 1).getY();

        int count = 0;
        Queue<Point> pointStorage = new LinkedList<>();

        maze[y][x] = PAST;

        for (int i = 0; i < LENGTH; i++) {
            int afterX = x;
            int afterY = y;

            afterX += X_CASE[i];
            afterY += Y_CASE[i];

            if (afterY < 0 || afterY >= maze.length && afterX < 0 || afterX >= maze[0].length) {
                return false;
            }

            if (maze[afterY][afterX] != WALL && maze[afterY][afterX] != PAST) {
                if (maze[afterY][afterX] == EXIT) {
                    totalList.addFirst(new Point(afterX, afterY));
                    totalList.addFirst(new Point(x, y));
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
                    if (node.point.get(node.point.size() - 1 - i).getX() == totalList.getFirst().getX() && node.point.get(node.point.size() - 1 - i).getY() == totalList.getFirst().getY()) {
                        continue;
                    }
                    totalList.addFirst(new Point(node.point.get(node.point.size() - 1 - i).getX(), node.point.get(node.point.size() - 1 - i).getY()));
                }

                return true;
            } else {
                return false;
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