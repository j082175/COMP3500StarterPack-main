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

    public ArrayList<String> isExist(String str) {
        ArrayList<String> array = new ArrayList<>();
        isExistRecursive(node, str, str, false, new String(), array);
        return array;
    }

    private boolean isExistRecursive(Node node, String str, String compareStr, boolean isCheck, String total, ArrayList<String> array) {
        if (node == null) {
            return false;
        }

        if (str.equals("")) {
            return false;
        }

        if (node.data != ' ') {
            if (compareStr.contains(String.valueOf(node.data))) {
                compareStr = compareStr.replace(node.data, '-');
                isCheck = true;
                total += node.data;


            } else {
                isCheck = false;
                return false;
            }
        }

        if (node.isEnd == true) {
            if (isCheck) {

                if (total.length() == str.length()) {
                    // total 저장
                    array.add(total);
                    total = "";

                    compareStr = str;
                    return true;
                }
            }
        }

        for (int i = 0; i < node.nodes.size(); i++) {
            isExistRecursive(node.nodes.get(i), str, compareStr, isCheck, total, array);
        }

/*        Stack<Node> stack = new Stack<>();

        stack.push(node);

        while (!stack.empty()) {
            Node next = stack.pop();

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
            }
        }*/

/*        int left = 0;
        int right = node.nodes.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;

            if (node.nodes.get(mid).data == ch) {
                return isExistRecursive(node.nodes.get(mid), str2);
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
