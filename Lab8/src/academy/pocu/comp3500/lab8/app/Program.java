package academy.pocu.comp3500.lab8.app;

import academy.pocu.comp3500.lab8.MazeSolver;
import academy.pocu.comp3500.lab8.maze.Point;
import org.junit.Test;

import java.util.List;

public class Program {

    public static void main(String[] args) {
        char[][] maze8x6 = new char[][]{
                {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
                {'x', ' ', ' ', 'x', ' ', ' ', ' ', 'x'},
                {'x', ' ', 'x', 'x', ' ', 'x', 'x', 'x'},
                {'x', ' ', ' ', ' ', ' ', 'x', ' ', 'x'},
                {'x', ' ', 'x', 'x', ' ', ' ', ' ', 'x'},
                {'x', ' ', ' ', ' ', 'x', 'x', 'x', 'x'},
                {'x', 'x', ' ', 'x', 'x', ' ', 'x', 'x'},
                {'x', ' ', ' ', 'x', ' ', ' ', 'E', 'x'},
                {'x', 'x', ' ', 'x', ' ', 'x', 'x', 'x'},
                {'x', ' ', ' ', ' ', ' ', 'x', ' ', 'x'},
                {'x', 'x', ' ', 'x', ' ', ' ', ' ', 'x'},
                {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}
        };

/*        char[][] maze8x6 = new char[][]{
                {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
                {'x', ' ', ' ', ' ', ' ', ' ', ' ', 'x'},
                {'x', ' ', 'x', 'x', 'x', 'x', 'x', 'x'},
                {'x', ' ', 'x', 'E', ' ', 'x', ' ', 'x'},
                {'x', ' ', 'x', 'x', ' ', ' ', ' ', 'x'},
                {'x', ' ', ' ', ' ', ' ', 'x', 'x', 'x'},
                {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}
        };*/

        List<Point> result = MazeSolver.findPath(maze8x6, new Point(6, 1));

        assert (result.size() == 21);
        assert (result.get(0).getX() == 6 && result.get(0).getY() == 1);
        assert (result.get(1).getX() == 5 && result.get(1).getY() == 1);
        assert (result.get(2).getX() == 4 && result.get(2).getY() == 1);
        assert (result.get(3).getX() == 4 && result.get(3).getY() == 2);
        assert (result.get(4).getX() == 4 && result.get(4).getY() == 3);
        assert (result.get(5).getX() == 3 && result.get(5).getY() == 3);
        assert (result.get(6).getX() == 2 && result.get(6).getY() == 3);
        assert (result.get(7).getX() == 1 && result.get(7).getY() == 3);
        assert (result.get(8).getX() == 1 && result.get(8).getY() == 4);
        assert (result.get(9).getX() == 1 && result.get(9).getY() == 5);
        assert (result.get(10).getX() == 2 && result.get(10).getY() == 5);
        assert (result.get(11).getX() == 2 && result.get(11).getY() == 6);
        assert (result.get(12).getX() == 2 && result.get(12).getY() == 7);
        assert (result.get(13).getX() == 2 && result.get(13).getY() == 8);
        assert (result.get(14).getX() == 2 && result.get(14).getY() == 9);
        assert (result.get(15).getX() == 3 && result.get(15).getY() == 9);
        assert (result.get(16).getX() == 4 && result.get(16).getY() == 9);
        assert (result.get(17).getX() == 4 && result.get(17).getY() == 8);
        assert (result.get(18).getX() == 4 && result.get(18).getY() == 7);
        assert (result.get(19).getX() == 5 && result.get(19).getY() == 7);
        assert (result.get(20).getX() == 6 && result.get(20).getY() == 7);
    }


    @Test
    public void test1() {
        char[][] maze3x3 = new char[][]{
                {'x', 'x', 'x'},
                {'x', 'E', 'x'},
                {'x', 'x', 'x'}
        };

        List<Point> result = MazeSolver.findPath(maze3x3, new Point(1, 1));

        assert (result.size() == 1);
        assert (result.get(0).getX() == 1 && result.get(0).getY() == 1);

        char[][] maze3x5 = new char[][]{
                {'x', 'x', 'x'},
                {'x', ' ', 'x'},
                {'x', ' ', 'x'},
                {'x', 'E', 'x'},
                {'x', 'x', 'x'}
        };

        result = MazeSolver.findPath(maze3x5, new Point(1, 1));

        assert (result.size() == 3);
        assert (result.get(0).getX() == 1 && result.get(0).getY() == 1);
        assert (result.get(1).getX() == 1 && result.get(1).getY() == 2);
        assert (result.get(2).getX() == 1 && result.get(2).getY() == 3);

        char[][] maze7x5 = new char[][]{
                {'x', 'x', 'x', 'x', 'x', 'x', 'x'},
                {'x', 'E', 'x', ' ', ' ', ' ', 'x'},
                {'x', ' ', 'x', ' ', 'x', ' ', 'x'},
                {'x', ' ', ' ', ' ', 'x', ' ', 'x'},
                {'x', 'x', 'x', 'x', 'x', 'x', 'x'}
        };

        result = MazeSolver.findPath(maze7x5, new Point(5, 3));

        assert (result.size() == 11);
        assert (result.get(0).getX() == 5 && result.get(0).getY() == 3);
        assert (result.get(1).getX() == 5 && result.get(1).getY() == 2);
        assert (result.get(2).getX() == 5 && result.get(2).getY() == 1);
        assert (result.get(3).getX() == 4 && result.get(3).getY() == 1);
        assert (result.get(4).getX() == 3 && result.get(4).getY() == 1);
        assert (result.get(5).getX() == 3 && result.get(5).getY() == 2);
        assert (result.get(6).getX() == 3 && result.get(6).getY() == 3);
        assert (result.get(7).getX() == 2 && result.get(7).getY() == 3);
        assert (result.get(8).getX() == 1 && result.get(8).getY() == 3);
        assert (result.get(9).getX() == 1 && result.get(9).getY() == 2);
        assert (result.get(10).getX() == 1 && result.get(10).getY() == 1);

        char[][] maze8x6 = new char[][]{
                {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
                {'x', 'x', ' ', 'x', ' ', 'E', ' ', 'x'},
                {'x', 'x', ' ', 'x', ' ', 'x', 'x', 'x'},
                {'x', ' ', ' ', ' ', ' ', 'x', ' ', 'x'},
                {'x', 'x', ' ', 'x', ' ', ' ', ' ', 'x'},
                {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}
        };

        result = MazeSolver.findPath(maze8x6, new Point(2, 2));

        assert (result.size() == 7);
        assert (result.get(0).getX() == 2 && result.get(0).getY() == 2);
        assert (result.get(6).getX() == 5 && result.get(6).getY() == 1);


    }

    @Test
    public void test2() {
        {
            char[][] maze1x1 = new char[][]{
                    {' '}
            };
            List<Point> result = MazeSolver.findPath(maze1x1, new Point(0, 0));
            assert (result.size() == 0);
        }

        {
            char[][] maze1x1 = new char[][]{
                    {'E'}
            };
            List<Point> result = MazeSolver.findPath(maze1x1, new Point(0, 0));
            assert (result.size() == 1);
            assert (result.get(0).getX() == 0 && result.get(0).getY() == 0);
        }

        {
            char[][] maze7x5 = new char[][]{
                    {'x', 'x', 'x', 'x', 'x', 'x', 'x'},
                    {'x', 'E', 'x', ' ', ' ', ' ', 'x'},
                    {'x', ' ', 'x', ' ', 'x', ' ', 'x'},
                    {'x', ' ', 'x', ' ', 'x', ' ', 'x'},
                    {'x', 'x', 'x', 'x', 'x', 'x', 'x'}
            };

            List<Point> result = MazeSolver.findPath(maze7x5, new Point(5, 3));
            assert (result.size() == 0);
        }

        {
            char[][] maze7x7 = new char[][]{
                    {'x', 'x', 'x', 'x', 'x', 'x', 'x'},
                    {'x', ' ', ' ', ' ', 'x', ' ', 'x'},
                    {'x', ' ', 'x', ' ', 'x', ' ', 'x'},
                    {'x', ' ', 'x', ' ', ' ', ' ', 'x'},
                    {'x', 'E', 'x', ' ', 'x', ' ', 'x'},
                    {'x', ' ', ' ', ' ', 'x', ' ', 'x'},
                    {'x', 'x', 'x', 'x', 'x', 'x', 'x'}
            };

            List<Point> result = MazeSolver.findPath(maze7x7, new Point(5, 5));
            assert (result.size() == 12 || result.size() == 10);
            if (result.size() == 12) {
                assert (result.get(0).getX() == 5 && result.get(0).getY() == 5);
                assert (result.get(1).getX() == 5 && result.get(1).getY() == 4);
                assert (result.get(2).getX() == 5 && result.get(2).getY() == 3);
                assert (result.get(3).getX() == 4 && result.get(3).getY() == 3);
                assert (result.get(4).getX() == 3 && result.get(4).getY() == 3);
                assert (result.get(5).getX() == 3 && result.get(5).getY() == 2);
                assert (result.get(6).getX() == 3 && result.get(6).getY() == 1);
                assert (result.get(7).getX() == 2 && result.get(7).getY() == 1);
                assert (result.get(8).getX() == 1 && result.get(8).getY() == 1);
                assert (result.get(9).getX() == 1 && result.get(9).getY() == 2);
                assert (result.get(10).getX() == 1 && result.get(10).getY() == 3);
                assert (result.get(11).getX() == 1 && result.get(11).getY() == 4);

            } else if (result.size() == 10) {
                assert (result.get(0).getX() == 5 && result.get(0).getY() == 5);
                assert (result.get(1).getX() == 5 && result.get(1).getY() == 4);
                assert (result.get(2).getX() == 5 && result.get(2).getY() == 3);
                assert (result.get(3).getX() == 4 && result.get(3).getY() == 3);
                assert (result.get(4).getX() == 3 && result.get(4).getY() == 3);
                assert (result.get(5).getX() == 3 && result.get(5).getY() == 4);
                assert (result.get(6).getX() == 3 && result.get(6).getY() == 5);
                assert (result.get(7).getX() == 2 && result.get(7).getY() == 5);
                assert (result.get(8).getX() == 1 && result.get(8).getY() == 5);
                assert (result.get(9).getX() == 1 && result.get(9).getY() == 4);
            }
        }
    }

    @Test
    public void test3() {
        char[][] maze8x6 = new char[][]{
                {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
                {'x', ' ', ' ', 'x', ' ', ' ', ' ', 'x'},
                {'x', ' ', 'x', 'x', ' ', 'x', 'x', 'x'},
                {'x', ' ', ' ', ' ', ' ', 'x', ' ', 'x'},
                {'x', ' ', 'x', 'x', ' ', ' ', ' ', 'x'},
                {'x', ' ', ' ', ' ', 'x', 'x', 'x', 'x'},
                {'x', 'x', ' ', 'x', 'x', ' ', 'x', 'x'},
                {'x', ' ', ' ', 'x', ' ', ' ', 'E', 'x'},
                {'x', 'x', ' ', 'x', ' ', 'x', 'x', 'x'},
                {'x', ' ', ' ', ' ', ' ', 'x', ' ', 'x'},
                {'x', 'x', ' ', 'x', ' ', ' ', ' ', 'x'},
                {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}
        };

        List<Point> result = MazeSolver.findPath(maze8x6, new Point(6, 1));

        assert (result.size() == 21);
        assert (result.get(0).getX() == 6 && result.get(0).getY() == 1);
        assert (result.get(1).getX() == 5 && result.get(1).getY() == 1);
        assert (result.get(2).getX() == 4 && result.get(2).getY() == 1);
        assert (result.get(3).getX() == 4 && result.get(3).getY() == 2);
        assert (result.get(4).getX() == 4 && result.get(4).getY() == 3);
        assert (result.get(5).getX() == 3 && result.get(5).getY() == 3);
        assert (result.get(6).getX() == 2 && result.get(6).getY() == 3);
        assert (result.get(7).getX() == 1 && result.get(7).getY() == 3);
        assert (result.get(8).getX() == 1 && result.get(8).getY() == 4);
        assert (result.get(9).getX() == 1 && result.get(9).getY() == 5);
        assert (result.get(10).getX() == 2 && result.get(10).getY() == 5);
        assert (result.get(11).getX() == 2 && result.get(11).getY() == 6);
        assert (result.get(12).getX() == 2 && result.get(12).getY() == 7);
        assert (result.get(13).getX() == 2 && result.get(13).getY() == 8);
        assert (result.get(14).getX() == 2 && result.get(14).getY() == 9);
        assert (result.get(15).getX() == 3 && result.get(15).getY() == 9);
        assert (result.get(16).getX() == 4 && result.get(16).getY() == 9);
        assert (result.get(17).getX() == 4 && result.get(17).getY() == 8);
        assert (result.get(18).getX() == 4 && result.get(18).getY() == 7);
        assert (result.get(19).getX() == 5 && result.get(19).getY() == 7);
        assert (result.get(20).getX() == 6 && result.get(20).getY() == 7);
    }
}

