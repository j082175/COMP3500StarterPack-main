package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class LinkedList {
    private LinkedList() {
    }

    public static Node append(final Node rootOrNull, final int data) {
        if (rootOrNull == null) {
            return new Node(data);
        }

        Node n2 = rootOrNull;

        while (n2.getNextOrNull() != null) {
            n2 = n2.getNextOrNull();
        }

        n2.setNext(new Node(data));

        return rootOrNull;
    }

    public static Node prepend(final Node rootOrNull, final int data) {
        if (rootOrNull == null) {
            return new Node(data);
        }

        Node n2 = rootOrNull;

        Node n3 = new Node(data);

        n3.setNext(n2);

        return n3;
    }

    public static Node insertAt(final Node rootOrNull, final int index, final int data) {
        if (rootOrNull == null) {
            return new Node(data);
        }

        if (index < 0) {
            return rootOrNull;
        }

        boolean check = false;

        int count = 0;

        Node backup1 = rootOrNull;
        Node backup2 = rootOrNull;

        while (count < index) {
            backup1 = backup1.getNextOrNull();

            if (backup1 == null) {
                return rootOrNull;
            }

            if (count < index - 1) {
                backup2 = backup2.getNextOrNull();
            }

            ++count;
            check = true;
        }

        Node n3 = backup1;
        Node n4 = backup1;
        n4 = new Node(data);
        n4.setNext(n3);

        if (check) {
            backup2.setNext(n4);
            return rootOrNull;
        }

        return n4;
    }

    public static Node removeAt(final Node rootOrNull, final int index) {
        if (rootOrNull == null) {
            return rootOrNull;
        }

        boolean check = false;

        int count = 0;

        Node backup1 = rootOrNull;
        Node backup2 = rootOrNull;

        while (count < index) {
            backup1 = backup1.getNextOrNull();

            if (backup1 == null) {
                return rootOrNull;
            }

            if (count < index - 1) {
                backup2 = backup2.getNextOrNull();
            }

            ++count;
            check = true;
        }

        backup1 = backup1.getNextOrNull();

        if (check) {
            backup2.setNext(backup1);
            return rootOrNull;
        }

        return backup1;
    }

    public static int getIndexOf(final Node rootOrNull, final int data) {
        if (rootOrNull == null) {
            return -1;
        }

        Node backup1 = rootOrNull;
        int count = 0;
        while (backup1.getData() != data) {
            backup1 = backup1.getNextOrNull();

            if (backup1 == null) {
                return -1;
            }

            ++count;
        }

        return count;
    }

    public static Node getOrNull(final Node rootOrNull, final int index) {
        if (rootOrNull == null) {
            return rootOrNull;
        }

        if (index < 0) {
            return null;
        }

        Node backup1 = rootOrNull;
        int count = 0;
        while (count < index) {
            backup1 = backup1.getNextOrNull();

            if (backup1 == null) {
                return null;
            }

            ++count;
        }

        return backup1;
    }

    public static Node reverse(final Node rootOrNull) {
        if (rootOrNull == null) {
            return null;
        }

        Node backup1 = LinkedList.append(null, rootOrNull.getData());
        Node backup2 = rootOrNull;

        while (backup2.getNextOrNull() != null) {
            backup2 = backup2.getNextOrNull();
            backup1 = LinkedList.insertAt(backup1, 0, backup2.getData());
        }

        return backup1;
    }

    public static Node interleaveOrNull(final Node root0OrNull, final Node root1OrNull) {
        if (root0OrNull == null) {
            return root1OrNull;
        }

        if (root1OrNull == null) {
            return root0OrNull;
        }

        int count = 0;
        Node backup1 = root0OrNull;
        Node backup2 = root1OrNull;

        while (backup1 != null) {
            Node n1 = backup1;
            Node n2 = null;

            if (count % 2 == 0) {
                Node n3 = backup1.getNextOrNull();
                n2 = new Node(backup2.getData());
                n2.setNext(n3);
                n1.setNext(n2);
                backup2 = backup2.getNextOrNull();
            }

            backup1 = backup1.getNextOrNull();
            ++count;
        }

        return root0OrNull;
    }
}