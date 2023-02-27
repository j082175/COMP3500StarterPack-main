package academy.pocu.comp3500.lab7;

import java.util.ArrayList;

public class Tri {

    private Node node;

    public void inputData(String data) {
        this.node = inputRecursive(this.node, data);
    }

    private Node inputRecursive(Node node, String data) {
        if (data == "") {
            return node;
        }

        char ch = data.charAt(0);
        String data2 = data.substring(1);

        if (ch >= 97 && ch <= 122) {
        } else {
            ch = (char)(ch + 32);
        }

        if (node == null) {
            node = new Node();
            node.data = ' ';
            node.nodes = new ArrayList<>();
            node.nodes.add(new Node(ch));
            Node node2 = inputRecursive(node.nodes.get(node.nodes.size() - 1), data2);
            return node;

        }


        if (node.nodes.size() == 0) {
            node.nodes = new ArrayList<>();
        }

        for (int i = 0; i < node.nodes.size(); i++) {
            if (node.nodes.get(i).data == ch) {
                // node.nodes.set(i, inputRecursive(node.nodes.get(i), data2));
                inputRecursive(node.nodes.get(i), data2);
                return node;
            }
        }

        node.nodes.add(new Node(ch));
        inputRecursive(node.nodes.get(node.nodes.size() - 1), data2);

        return node;
    }
}
