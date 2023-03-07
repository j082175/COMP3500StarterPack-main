package academy.pocu.comp3500.lab8;

import academy.pocu.comp3500.lab8.maze.Point;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public final class MazeSolver {
    public static List<Point> findPath(final char[][] maze, final Point start) {
        List<Point> list = new ArrayList<>();
        Stack<List<Point>> ableToGo = new Stack<>();
        Queue<Integer> q;
        boolean isEnd = false;
        list.add(start);
        Point before = null;

        while (!isEnd) {
            isEnd = getNextMove(maze, start, ableToGo);
        }


        return list;
    }

    private static boolean getNextMove(char[][] maze, Point start, Stack<List<Point>> ableToGo) {
        char wall = 'x';
        char exit = 'E';

        int x = start.getX();
        int y = start.getY();
        boolean check;
        boolean isNull = false;

        int wayCount = 0;




/*        if (!isNull) {
            //check = afterY != before.getY() && afterX != before.getX();
        } else {
            check = true;
        }*/

        List<List<Point>> list = new ArrayList<>();

        ableToGo.push(new ArrayList<>());

        int historyCount = 0;

        int newX = x;
        int newY = y;

        while (true) { // 순서대로 왼쪽 오른쪽 위 아래
            x = newX;
            y = newY;

            int afterX = x - 1;
            int afterY = y;

            boolean isWay = false;
            wayCount = 0;
            maze[y][x] = '!';


            if (maze[afterY][afterX] != wall && maze[afterY][afterX] != '!') {
                isWay = true;

                if (isWay) {
                    ableToGo.push(new ArrayList<>());
                    ableToGo.peek().add(new Point(afterX, afterY));
                } else {
                    ableToGo.peek().add(new Point(afterX, afterY));

                    newY = afterY;
                    newX = afterX;
                }

                if (maze[afterY][afterX] == exit) {
                    return true;
                }

            } else {
                ++wayCount;
            }

            afterX = x + 1;
            afterY = y;


/*        if (!isNull) {
            check = afterY != before.getY() && afterX != before.getX();
        }*/

            if (maze[afterY][afterX] != wall && maze[afterY][afterX] != '!') {
                if (isWay) {
                    ableToGo.push(new ArrayList<>());
                    ableToGo.peek().add(new Point(afterX, afterY));
                } else {
                    ableToGo.peek().add(new Point(afterX, afterY));

                    newY = afterY;
                    newX = afterX;
                    isWay = true;
                }

                if (maze[afterY][afterX] == exit) {
                    return true;
                }

            } else {
                ++wayCount;
            }

            afterX = x;
            afterY = y - 1;


/*        if (!isNull) {
            check = afterY != before.getY() && afterX != before.getX();
        }*/

            if (maze[afterY][afterX] != wall && maze[afterY][afterX] != '!') {
                if (isWay) {
                    ableToGo.push(new ArrayList<>());
                    ableToGo.peek().add(new Point(afterX, afterY));
                } else {
                    ableToGo.peek().add(new Point(afterX, afterY));

                    newY = afterY;
                    newX = afterX;
                    isWay = true;
                }

                if (maze[afterY][afterX] == exit) {
                    return true;
                }

            } else {
                ++wayCount;
            }

            afterX = x;
            afterY = y + 1;


/*        if (!isNull) {
            check = afterY != before.getY() && afterX != before.getX();
        }*/

            if (maze[afterY][afterX] != wall && maze[afterY][afterX] != '!') {
                if (isWay) {
                    ableToGo.push(new ArrayList<>());
                    ableToGo.peek().add(new Point(afterX, afterY));
                } else {
                    ableToGo.peek().add(new Point(afterX, afterY));

                    newY = afterY;
                    newX = afterX;
                    isWay = true;
                }

                if (maze[afterY][afterX] == exit) {
                    return true;
                }
            } else {
                ++wayCount;
            }

            if (wayCount == 4) {
                ableToGo.pop();
                int size = ableToGo.peek().size();
                newX = ableToGo.peek().get(size - 1).getX();
                newY = ableToGo.peek().get(size - 1).getY();
            } /*else {
                list.add(ableToGo.getFirst());
                newX = ableToGo.getFirst().get(0).getX();
                newY = ableToGo.getFirst().get(0).getY();
                ableToGo.removeFirst();
            }*/


/*            else {
                newX = ableToGo.peek().get(0).getX();
                newY = ableToGo.peek().get(0).getY();
            }*/


        }


    }
}