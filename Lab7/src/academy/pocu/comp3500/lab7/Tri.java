package academy.pocu.comp3500.lab7;

import java.util.ArrayList;
import java.util.Stack;

public class Tri {

    private Node node;

    public void inputData(String data) {
        this.node = inputRecursive(this.node, data);
    }

    private Node inputRecursive(Node node, String data) {
        if (data.equals("")) {
            node.isEnd = true;
            return node;
        }

        char ch = data.charAt(0);
        String data2 = data.substring(1);

        if (ch >= 97 && ch <= 122) {
        } else {
            ch = (char) (ch + 32);
        }

        if (node == null) {
            node = new Node();
            node.data = ' ';
            node.nodes = new ArrayList<>();
            node.nodes.add(new Node(ch));
            inputRecursive(node.nodes.get(node.nodes.size() - 1), data2);
            return node;

        }


        if (node.nodes.size() == 0) {
            node.nodes = new ArrayList<>();
        }

        for (int i = 0; i < node.nodes.size(); i++) {
            if (node.nodes.get(i).data == ch) {
                inputRecursive(node.nodes.get(i), data2);
                return node;
            }
        }

        node.nodes.add(new Node(ch));
        inputRecursive(node.nodes.get(node.nodes.size() - 1), data2);

        return node;
    }

    public boolean isExist(String str, ArrayList<String> arrayList) {
        String total = "";
        return isExistRecursive(node, str, arrayList, total);
    }

    private boolean isExistRecursive(Node node, String str, ArrayList<String> arrayList, String total) {
        if (node == null) {
            return false;
        }

        if (node.isEnd == true) {
            arrayList.add(total);
            return true;
        }

        if (str.equals("")) {
            return false;
        }

        char ch = str.charAt(0);
        String str2 = str.substring(1);

        Stack<Node> stack = new Stack<>();

        stack.push(node);

        int depth = 1;

        while (!stack.empty()) {
            Node next = stack.pop();
            depth--;

            if (next.data != ' ') {
                total += next.data;
            }

            if (next.isEnd) {
                arrayList.add(total);
            }

            if (next.nodes.size() == 0) {

            }

            for (Node child : next.nodes) {
                stack.push(child);
                ++depth;
            }
        }

/*        int left = 0;
        int right = node.nodes.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;

            if (node.nodes.get(mid).data == ch) {
                return isExistRecursive(node.nodes.get(mid), str2, arrayList);
            }

            if (node.nodes.get(mid).data < ch) {
                left = mid + 1;
                continue;
            }

            right = mid - 1;
        }*/



        return false;
    }

}
