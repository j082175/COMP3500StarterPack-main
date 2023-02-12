package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class League {
    public BinarySearchTree origin = new BinarySearchTree();
    private int size;

    public League() {
    }

    public League(Player[] players) {
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

        // if (this.size == 2) {
        //     if (origin.root.value.getId() == player.getId()) {
        //         if (origin.root.left != null) {
        //             return origin.root.left.value;
        //         } else {
        //             return origin.root.right.value;
        //         }
        //     } else {
        //         return origin.root.value;
        //     }
        // }

        Node result = this.origin.findNode(player);

        if (result.left == null && result.right == null) {
            Node node = result.previous;

            if (node.previous == null) {
                return node.value;
            }

            int leftValue = node.value.getRating() - result.value.getRating();

            Node node2 = node;

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
                return node.value;
            } else if (Math.abs(leftValue) == Math.abs(rightValue)) {
                if (leftValue > 0) {
                    return node.value;
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
    }

    public Player[] getTop(final int count) {
        if (this.size == 0) {
            return new Player[0];
        }

        int size = count;

        if (count > this.size) {
            size = this.size;
        }

        Player[] p1 = new Player[size];
        int[] index = new int[1];
        origin.traverseInOrderTop(origin.root, p1, index);

        return p1;
    }

    public Player[] getBottom(final int count) {
        if (this.size == 0) {
            return new Player[0];
        }

        int size = count;

        if (count > this.size) {
            size = this.size;
        }

        Player[] p1 = new Player[size];
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
        if (origin.deleteNode(player)) {
            --size;
            return true;
        }

        return false;
    }
}