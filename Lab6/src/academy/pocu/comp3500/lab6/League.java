package academy.pocu.comp3500.lab6;

import java.util.ArrayList;
import java.util.HashMap;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class League {
    private ArrayList<Player> players = new ArrayList<>();
    private HashMap<Integer, Integer> hashMap = new HashMap<>();

    private BinarySearchTree origin = new BinarySearchTree();
    private int size;

    public League() {
    }

    public League(Player[] players) {

        // int max = 0;
        // for (int i = 0; i < players.length; i++) {
        // if (max < players[i].getRating()) {
        // this.players.add(this.players.size(), players[i]);
        // max = players[i].getRating();
        // } else {
        // int index = SortAndFind.find2(this.players, players[i]);
        // this.players.add(index, players[i]);
        // }

        // }

        for (int i = 0; i < players.length; i++) {
            if (origin.insert(players[i])) {
                ++size;
            }
        }
    }

    public Player findMatchOrNull(final Player player) {
        if (this.size == 0 || this.size == 1) {
            return null;
        }

        if (this.size == 2) {
            if (origin.root.value.getId() == player.getId()) {
                if (origin.root.left != null) {
                    return origin.root.left.value;
                } else {
                    return origin.root.right.value;
                }
            } else {
                return origin.root.value;
            }
        }

        Node result = this.origin.findNode(player);

        if (result.left == null && result.right == null) {
            Node node1 = result.previous;

            if (node1.previous == null) {
                return node1.value;
            }

            int leftValue = node1.value.getRating() - result.value.getRating();

            Node node2 = node1;

            if (node2.value.getRating() < result.value.getRating()) {
                while (node2.previous != null && node2.previous.right == node2) {
                    node2 = node2.previous;

                }
            }

            if (node2.value.getRating() > result.value.getRating()) {
                while (node2.previous != null && node2.previous.left == node2) {
                    node2 = node2.previous;

                }
            }

            if (node2.previous != null) {
                node2 = node2.previous;
            }

            int rightValue = node2.value.getRating() - result.value.getRating();

            if (Math.abs(leftValue) < Math.abs(rightValue)) {
                return node1.value;
            } else if (Math.abs(leftValue) == Math.abs(rightValue)) {
                if (leftValue > 0) {
                    return node1.value;
                } else {
                    return node2.value;
                }
            } else {
                return node2.value;
            }

        }

        if (result.left == null) {
            Node node = result.right;
            while (node.left != null) {
                node = node.left;
            }

            if (result.previous != null) {

                Node node2 = result;

                while (node2.previous != null && node2.previous.left == node2) {
                    node2 = node2.previous;

                }

                if (node2.previous != null) {
                    node2 = node2.previous;
                }

                int rightValue = node2.value.getRating() - result.value.getRating();

                int leftValue = node.value.getRating() - result.value.getRating();

                if (Math.abs(leftValue) < Math.abs(rightValue)) {
                    return node.value;
                } else if (Math.abs(leftValue) == Math.abs(rightValue)) {
                    if (rightValue > 0) {
                        return node2.value;
                    } else {
                        return node.value;
                    }
                } else {
                    return node2.value;
                }
            }

            return node.value;
        }

        if (result.right == null) {
            Node node = result.left;
            while (node.right != null) {
                node = node.right;
            }

            if (result.previous != null) {
                Node node2 = result;

                while (node2.previous != null && node2.previous.right == node2) {
                    node2 = node2.previous;

                }

                if (node2.previous != null) {
                    node2 = node2.previous;
                }

                int rightValue = node2.value.getRating() - result.value.getRating();

                int leftValue = node.value.getRating() - result.value.getRating();

                if (Math.abs(leftValue) < Math.abs(rightValue)) {
                    return node.value;
                } else if (Math.abs(leftValue) == Math.abs(rightValue)) {
                    if (rightValue > 0) {
                        return node2.value;
                    } else {
                        return node.value;
                    }
                } else {
                    return node2.value;
                }
            }

            return node.value;
        }

        {
            Node nodeLeft = result.left;
            while (nodeLeft.right != null) {
                nodeLeft = nodeLeft.right;
            }

            Node nodeRight = result.right;
            while (nodeRight.left != null) {
                nodeRight = nodeRight.left;
            }

            if (Math.abs(nodeLeft.value.getRating() - result.value.getRating()) >= Math
                    .abs(nodeRight.value.getRating() - result.value.getRating())) {
                return nodeRight.value;
            } else {
                return nodeLeft.value;
            }
        }

        // Player p1;
        // Player p2;

        // if (players.size() == 0) {
        // return null;
        // }

        // if (players.size() == 1) {
        // return null;
        // }

        // int index = SortAndFind.find(players, player);

        // if (index == 0) {
        // return players.get(index + 1);
        // }

        // if (index == players.size() - 1) {
        // return players.get(index - 1);
        // }

        // p1 = players.get(index - 1);
        // p2 = players.get(index + 1);

        // if (Math.abs(player.getRating() - p1.getRating()) >= Math.abs(p2.getRating()
        // - player.getRating())) {
        // return p2;
        // } else {
        // return p1;
        // }

    }

    public Player[] getTop(final int count) {
        // if (this.players.size() == 0) {
        // return new Player[0];
        // }

        // int c = count;

        // if (count > this.players.size()) {
        // c = this.players.size();
        // }

        // Player[] p1 = new Player[c];

        // for (int i = 0; i < c; i++) {
        // p1[i] = this.players.get(players.size() - 1 - i);
        // }

        // return p1;

        if (this.size == 0) {
            return new Player[0];
        }

        int c = count;

        if (count > this.size) {
            c = this.size;
        }

        Player[] p1 = new Player[c];
        int[] index = new int[1];
        origin.traverseInOrderTop(origin.root, p1, index);

        return p1;
    }

    public Player[] getBottom(final int count) {
        // if (this.players.size() == 0) {
        // return new Player[0];
        // }

        // int c = count;

        // if (count > this.players.size()) {
        // c = this.players.size();
        // }

        // Player[] p1 = new Player[c];

        // for (int i = 0; i < c; i++) {
        // p1[i] = this.players.get(i);
        // }

        // return p1;

        if (this.size == 0) {
            return new Player[0];
        }

        int c = count;

        if (count > this.size) {
            c = this.size;
        }

        Player[] p1 = new Player[c];
        int[] index = new int[1];
        origin.traverseInOrderBottom(origin.root, p1, index);

        return p1;
    }

    public boolean join(final Player player) {

        if (origin.insert(player)) {
            ++size;
            return true;
        }

        return false;
    }

    public boolean leave(final Player player) {
        --size;
        return origin.deleteNode(player);
    }
}