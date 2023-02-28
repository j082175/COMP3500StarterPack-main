package academy.pocu.comp3500.lab7;

import java.util.ArrayList;

public class Node {
    public char data;
    public ArrayList<Node> nodes = new ArrayList<>();
    public boolean isEnd = false;


    public Node() {
    }

    public Node(char data) {
        this.data = data;
    }
}
