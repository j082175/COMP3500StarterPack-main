package academy.pocu.comp3500.lab8;

import academy.pocu.comp3500.lab8.maze.Point;

import java.util.ArrayList;
import java.util.List;

public final class MazeSolver {
    public static List<Point> findPath(final char[][] maze, final Point start) {
        List<Point> list = new ArrayList<>();
        boolean isEnd = false;
        list.add(start);
        Point before = null;

        while (!isEnd) {
            isEnd = getNextMove(maze, list.get(list.size() - 1), list, before);
            before = list.get(list.size() - 2);
        }


        return list;
    }

    private static boolean getNextMove(char[][] maze, Point start, List<Point> list, Point before) {
        char wall = 'x';
        char exit = 'E';

        int x = start.getX();
        int y = start.getY();

        if (maze[y][x - 1] != wall) {
            list.add(new Point(x - 1, y));
            if (maze[y][x - 1] == exit) {
                return true;
            }
        }

        else if (maze[y][x + 1] != wall) {
            list.add(new Point(x + 1, y));
            if (maze[y][x + 1] == exit) {
                return true;
            }
        }

        else if (maze[y - 1][x] != wall) {
            list.add(new Point(x, y - 1));
            if (maze[y - 1][x] == exit) {
                return true;
            }
        }

        else if (maze[y + 1][x] != wall) {
            list.add(new Point(x, y + 1));
            if (maze[y + 1][x] == exit) {
                return true;
            }
        }

        return false;
    }
}