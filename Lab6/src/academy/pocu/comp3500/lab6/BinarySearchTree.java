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
      
          if (value.getRating() < current.value.getRating()) {
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

        if (value == current.value) {
            return current;
        }

        if (value.getRating() < current.value.getRating()) {
            return findNodeRecursive(current.left, value);
        }

        return findNodeRecursive(current.right, value);
    }

    public boolean deleteNode(Player player) {
        return deleteRecursive(player);
    }

    private boolean deleteRecursive(Player player) {
        Node goal = findNode(player);

        if (goal == null) {
            return false;
        }

        if (goal.left == null && goal.right == null) {

            if (goal.previous.value.getRating() > goal.value.getRating()) {

                goal.previous.value = goal.value;

                goal.previous.left = null;
            } else {
                goal.previous.value = goal.value;

                goal.previous.right = null;
            }

            return true;
        }

        if (goal.left == null) {
            Node node = goal.right;
            while (node.left != null) {
                node = node.left;
            }

            goal.value = node.value;

            node.previous.left = null;
            return true;
        }

        if (goal.right == null) {
            Node node = goal.left;
            while (node.right != null) {
                node = node.right;
            }

            goal.value = node.value;

            node.previous.right = null;
            return true;
        }

        {
            Node node = goal.right;
            while (node.left != null) {
                node = node.left;
            }

            goal.value = node.value;

            node.previous.left = node.right;

            return true;
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
        // v.push_back(startNode->Data);
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
        // v.push_back(startNode->Data);
        traverseInOrderTop(startNode.left, players, index);

        return;
    }
}
