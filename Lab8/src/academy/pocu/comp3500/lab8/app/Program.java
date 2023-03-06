package academy.pocu.comp3500.lab8.app;

import academy.pocu.comp3500.lab8.MazeSolver;
import academy.pocu.comp3500.lab8.maze.Point;

import java.util.List;

public class Program {

    public static void main(String[] args) {
        char[][] maze = new char[][]{
                {'x', 'x', 'x', 'x', 'x'},
                {'x', ' ', ' ', ' ', 'x'},
                {'x', ' ', 'x', 'E', 'x'},
                {'x', 'x', 'x', 'x', 'x'}
        };

        List<Point> result = MazeSolver.findPath(maze, new Point(1, 2));
// [Point(1, 2), Point(1, 1), Point(2, 1), Point(3, 1), Point(3, 2)]
    }
}
