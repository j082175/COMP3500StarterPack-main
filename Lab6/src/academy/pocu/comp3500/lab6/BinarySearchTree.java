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

        if (value == current.value) {
            return current;
        }

        if (value.getRating() < current.value.getRating()) {
            return findNodeRecursive(current.left, value);
        }

        return findNodeRecursive(current.right, value);
    }

    public boolean deleteNode(Player player) {
        Node node = deleteRecursive(root, player);

        if (node == null) {
            return false;
        } else {
            return true;
        }
    }

    private Node deleteRecursive(Node node, Player player) {
        // 기본 케이스: 키가 트리에서 발견되지 않음
        if (node == null) {
            return null;
        }

        // 주어진 키가 루트 노드보다 작으면 왼쪽 하위 트리에 대해 반복
        if (player.getRating() < node.value.getRating()) {
            Node node2 = deleteRecursive(node.left, player);

            if (node2 == null) {
                return null;
            }

            node.left = node2;

        }

        // 주어진 키가 루트 노드보다 크면 오른쪽 하위 트리에 대해 반복
        else if (player.getRating() > node.value.getRating()) {
            Node node2 = deleteRecursive(node.right, player);

            if (node2 == null) {
                return null;
            }

            node.right = node2;
        }

        // 키 발견
        else {
            // 사례 1: 삭제할 노드에 자식이 없음(리프 노드)
            if (node.left == null && node.right == null) {
                // 루트를 null로 업데이트
                return null;
            }

            // 사례 2: 삭제할 노드에 두 개의 자식이 있는 경우
            else if (node.left != null && node.right != null) {
                // 순서가 없는 선행 노드를 찾습니다.
                Node predecessor = findMaximumKey(node.left);

                // 선행 노드의 값을 현재 노드에 복사
                node.value = predecessor.value;

                // 선행 작업을 재귀적으로적으로 삭제합니다. 참고로
                // 선행 작업에는 최대 하나의 자식(왼쪽 자식)이 있습니다.
                node.left = deleteRecursive(node.left, predecessor.value);
            }

            // Case 3: 삭제할 노드에 자식이 하나만 있는 경우
            else {
                // 자식 노드 선택
                Node child = (node.left != null) ? node.left : node.right;
                node = child;
            }
        }

        return node;

    }

    public static Node findMaximumKey(Node ptr) {
        while (ptr.right != null) {
            ptr = ptr.right;
        }
        return ptr;
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
