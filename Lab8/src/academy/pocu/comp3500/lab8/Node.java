package academy.pocu.comp3500.lab8;

import academy.pocu.comp3500.lab8.maze.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Node {
    public List<Point> point;
    public Queue<Node> children = new LinkedList<>();
    public Node(ArrayList<Point> point) {
        this.point = point;
    }

    public void insertPoint(Point point) {
        this.point.add(point);
    }

    public Node getChildrenNode() {
        return children.peek();
    }

    public void addChildren(Point point) {
        ArrayList<Point> arrayList = new ArrayList<>();
        arrayList.add(point);
        this.children.add(new Node(arrayList));
    }
}
