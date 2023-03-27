package academy.pocu.comp3500.lab11;

import academy.pocu.comp3500.lab11.data.Point;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class BallBoy {
    public static List<Point> findPath(final Point[] points) {
        final Point origin = new Point(0, 0);

        final List<Point> path = new ArrayList<>();
        path.add(origin);

        if (points.length == 0) {
            return path;
        }

        final List<Point> remainingPoints = new ArrayList<>();
        for (Point point : points) {
            remainingPoints.add(point);
        }

        while (!remainingPoints.isEmpty()) {
            final Point nextPoint = findNearestPoint(path.get(path.size() - 1), remainingPoints);
            remainingPoints.remove(nextPoint);
            path.add(nextPoint);
        }

        path.add(origin);
        return path;
    }

    private static Point findNearestPoint(final Point current, final List<Point> candidates) {
        Point nearestPoint = null;
        int nearestDistance = Integer.MAX_VALUE;

        for (Point candidate : candidates) {
            final int distance = getSquaredDistanceTo(current, candidate);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestPoint = candidate;
            }
        }

        return nearestPoint;
    }

    public static List<Point> findPath2(final Point[] points) {
        final Point origin = new Point(0, 0);

        final List<Point> path = new LinkedList<>();
        path.add(origin);

        if (points.length == 0) {
            path.add(origin);
            return path;
        }

        final PriorityQueue<Point> remainingPoints = new PriorityQueue<>(Comparator.comparingInt(o -> getSquaredDistanceTo(o, origin)));
        for (Point point : points) {
            remainingPoints.add(point);
        }

        while (!remainingPoints.isEmpty()) {
            final Point nextPoint = remainingPoints.poll();
            path.add(nextPoint);
        }

        path.add(origin);
        return path;
    }

    public static int getSquaredDistanceTo(Point p1, Point p2) {
        int dx = p1.getX() - p2.getX();
        int dy = p1.getY() - p2.getY();
        return dx * dx + dy * dy;
    }
}