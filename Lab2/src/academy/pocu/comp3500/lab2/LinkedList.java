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
        if (rootOrNull == null && index < 0) {
            //return new Node(data);
            return rootOrNull;
        }

        if (rootOrNull == null || index == 0) {
            return new Node(data);
        }

        if (rootOrNull != null && index < 0) {
            return rootOrNull;
        }

        // if (index < 0) {
        //     return rootOrNull;
        // }

        Node head = rootOrNull;
        Node newNode = new Node(data);
        newNode.setNext(null);

        if (index < 0) {
            return head;
        } else if (index == 0) {
            newNode.setNext(head);
            head = newNode;
        } else {
            Node temp = new Node(data);
            temp = head;
            for (int i = 1; i < index; i++) {
                if (temp != null) {
                    temp = temp.getNextOrNull();
                }
            }

            if (temp != null) {
                newNode.setNext(temp.getNextOrNull());
                temp.setNext(newNode);
            } else {
                return head;
            }
        }

        return head;

        // if (index == 0) {
        //     Node n = new Node(data);
        //     n.setNext(rootOrNull);
        //     return n;
        // }

        // Node x = rootOrNull;
        // for (int i = 0; i < index - 1; i++) {
        //     if (x.getNextOrNull() == null) {
        //         return rootOrNull;
        //     }
        //     x = x.getNextOrNull();
        // }

        // Node temp1 = x;
        // Node temp2 = temp1.getNextOrNull();
        // Node newNode = new Node(data);
        // temp1.setNext(newNode);
        // newNode.setNext(temp2);
        // return rootOrNull;

/////////////////////////////////////////////////////////

        // Node exNode = rootOrNull;
        // Node newNode = new Node(data);
        // Node prevNode = rootOrNull;
        // int count = 0;
        // boolean check = false;

        // while (count < index) {
        //     if (exNode == null) {
        //         return rootOrNull;
        //     }

        //     exNode = exNode.getNextOrNull();

        //     if (count < index - 1) {
        //         prevNode = prevNode.getNextOrNull();
        //     }

        //     ++count;
        //     check = true;
        // }

        // newNode.setNext(exNode);

        // if (check) {
        //     prevNode.setNext(newNode);
        //     return rootOrNull;
        // }

        // return newNode;
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

        // Node backup1 = LinkedList.append(null, rootOrNull.getData());
        // Node backup2 = rootOrNull;

        // while (backup2.getNextOrNull() != null) {
        //     backup2 = backup2.getNextOrNull();
        //     backup1 = LinkedList.insertAt(backup1, 0, backup2.getData());
        // }

        // return backup1;

        Node head = rootOrNull;
        Node nextNode = head;
        Node currentNode = null;
        Node preNode = null;

        while (nextNode != null) {
            preNode = currentNode;
            currentNode = nextNode;
            nextNode = nextNode.getNextOrNull();
            currentNode.setNext(preNode);
        }

        head = currentNode;

        return head;
    }

    public static Node interleaveOrNull(final Node root0OrNull, final Node root1OrNull) {
        if (root0OrNull == null) {
            return root1OrNull;
        }

        if (root1OrNull == null) {
            return root0OrNull;
        }

        Node n1 = root0OrNull;
        Node n2 = root1OrNull;

        Node result = n1;
        while (n1 != null && n2 != null) {
            Node temp1 = n1.getNextOrNull();
            Node temp2 = n2.getNextOrNull();

            if (n1.getNextOrNull() != null) {
                n2.setNext(n1.getNextOrNull());
            }
            n1.setNext(n2);
            n1 = temp1;
            n2 = temp2;
        }

        return result;

        // int count = 0;
        // Node backup1 = root0OrNull;
        // Node backup2 = root1OrNull;

        // while (backup1 != null) {
        //     Node n1 = backup1;
        //     Node n2 = null;

        //     if (count % 2 == 0) {
        //         if (backup2 != null) {
        //             Node n3 = backup1.getNextOrNull();
        //             n2 = new Node(backup2.getData());
        //             n2.setNext(n3);
        //             n1.setNext(n2);

        //             backup2 = backup2.getNextOrNull();
        //         } else {
        //             break;
        //         }

        //     }

        //     backup1 = backup1.getNextOrNull();

        //     if (backup1.getNextOrNull() == null) {
        //         backup1.setNext(backup2);
        //         break;
        //     }
        //     ++count;
        // }

        // return root0OrNull;
    }
}