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
        return deleteRecursive(root, player);
    }

    private boolean deleteRecursive(Node node, Player player) {
        // pointer to store the parent of the current node
        Node parent = null;
 
        // start with the root node
        Node curr = node;
 
        // search key in the BST and set its parent pointer
        while (curr != null && curr.value.getRating() != player.getRating())
        {
            // update the parent to the current node
            parent = curr;
 
            // if the given key is less than the current node, go to the left subtree;
            // otherwise, go to the right subtree
            if (player.getRating() < curr.value.getRating()) {
                curr = curr.left;
            }
            else {
                curr = curr.right;
            }
        }
 
        // return if the key is not found in the tree
        if (curr == null) {
            return false;
        }
 
        // Case 1: node to be deleted has no children, i.e., it is a leaf node
        if (curr.left == null && curr.right == null)
        {
            // if the node to be deleted is not a root node, then set its
            // parent left/right child to null
            if (curr != node)
            {
                if (parent.left == curr) {
                    parent.left = null;
                }
                else {
                    parent.right = null;
                }
            }
            // if the tree has only a root node, set it to null
            else {
                node = null;
            }
        }
 
        // Case 2: node to be deleted has two children
        else if (curr.left != null && curr.right != null)
        {
            // find its inorder successor node
            Node successor = getMinimumKey(curr.right);
 
            // store successor value
            Player val = successor.value;
 
            // recursively delete the successor. Note that the successor
            // will have at most one child (right child)
            deleteRecursive(node, successor.value);
 
            // copy value of the successor to the current node
            curr.value = val;
        }
 
        // Case 3: node to be deleted has only one child
        else {
            // choose a child node
            Node child = (curr.left != null)? curr.left: curr.right;
 
            // if the node to be deleted is not a root node, set its parent
            // to its child
            if (curr != node)
            {
                if (curr == parent.left) {
                    parent.left = child;
                }
                else {
                    parent.right = child;
                }
            }
 
            // if the node to be deleted is a root node, then set the root to the child
            else {
                node = child;
            }
        }
 
        return true;

    }

    public static Node getMinimumKey(Node curr)
    {
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr;
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
