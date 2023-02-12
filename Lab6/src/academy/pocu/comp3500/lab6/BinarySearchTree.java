package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class BinarySearchTree {
    public Node root;

    public BinarySearchTree() {
        root = null;
    }

    public boolean insert(Player value) {
        boolean[] isSame = new boolean[1];
        Node result = insertRecursive(root, value, isSame);

        if (isSame[0] == true) {
            return false;
        }

        root = result;
        return true;
    }

    private Node insertRecursive(Node current, Player value, boolean[] isSame) {

        if (current == null) {
            return new Node(value);
        }

        if (value.getId() == current.value.getId()) {
            isSame[0] = true;
            return current;
        }

        if (value.getRating() <= current.value.getRating()) {
            current.left = insertRecursive(current.left, value, isSame);
            current.left.previous = current;
        } else if (value.getRating() > current.value.getRating()) {
            current.right = insertRecursive(current.right, value, isSame);
            current.right.previous = current;
        } else {
            // value already exists in the tree
            isSame[0] = true;
            return current;
        }

        return current;

    }

    public Node findNode(Player value) {
        return findNodeRecursive(root, value);
    }

    private Node findNodeRecursive(Node current, Player value) {
        if (current == null) {
            return null;
        }

        if (value.getId() == current.value.getId()) {
            return current;
        }

        if (value.getRating() < current.value.getRating()) {
            return findNodeRecursive(current.left, value);
        }

        return findNodeRecursive(current.right, value);
    }

    public boolean deleteNode(Player player) {
        Node goal = findNode(player);
        boolean[] isCheck = new boolean[1];
        deleteRecursive(goal, player, isCheck);

        if (isCheck[0] == true) {
            return false;
        } else {
            return true;
        }

    }

    private Node deleteRecursive(Node goal, Player player, boolean[] isCheck) {

        if (goal == null) {
            isCheck[0] = true;
            return null;
        }

        // goal 의 모든 자식이 없을경우
        if (goal.left == null && goal.right == null) {

            if (goal.previous == null) {
                root = null;
                return null;
            }

            if (goal.value.getId() == goal.previous.value.getId()) {
                
                if (goal.previous.left == goal) {
                    goal.previous.left = null;
                    return goal;
                } else {
                    goal.previous.right = null;
                    return goal;
                }
            }

            if (goal.previous.value.getRating() > goal.value.getRating()) {

                goal.previous.left = null;
            } else {

                goal.previous.right = null;
            }

            return goal;
        }

        // goal 의 왼쪽 자식이 없을경우
        if (goal.left == null) {
            Node node = goal.right;
            while (node.left != null) {
                node = node.left;
            }

            goal.value = node.value;

            return deleteRecursive(node, player, isCheck);
        }

        // goal 의 오른쪽 자식이 없을경우
        if (goal.right == null) {
            Node node = goal.left;
            while (node.right != null) {
                node = node.right;
            } 

            goal.value = node.value;

            return deleteRecursive(node, player, isCheck);
        }

        // goal 의 모든 자식이 있을경우
        {
            Node node = goal.right;
            while (node.left != null) {
                node = node.left;
            }

            goal.value = node.value;

            return deleteRecursive(node, player, isCheck);
        }
    }

    public void traverseInOrderBottom(Node startNode, Player[] players, int[] index) {

        if (startNode == null) {
            return;
        }

        traverseInOrderBottom(startNode.left, players, index);
        if (index[0] < players.length) {
            players[index[0]] = startNode.value;
            ++index[0];
        }
        traverseInOrderBottom(startNode.right, players, index);

        return;
    }

    public void traverseInOrderTop(Node startNode, Player[] players, int[] index) {

        if (startNode == null) {
            return;
        }

        traverseInOrderTop(startNode.right, players, index);
        if (index[0] < players.length) {
            players[index[0]] = startNode.value;
            ++index[0];
        }
        traverseInOrderTop(startNode.left, players, index);

        return;
    }
}
