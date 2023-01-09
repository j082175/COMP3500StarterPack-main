package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class Queue {
    private Node next;
    private Node origin;
    private int size;

    public Queue() {
        this.origin = null;
        this.next = null;
        this.size = 0;
    }

    public void enqueue(final int data) {
        if (this.next == null) {
            this.next = new Node(data);
            ++this.size;
            return;
        }

        Node backup1 = this.next;
        Node n1 = new Node(data);
        n1.setNext(backup1);

        this.next = n1;
        ++this.size;
    }

    public int peek() {

        Node backup1 = this.next;
        while (backup1.getNextOrNull() != null) {
            backup1 = backup1.getNextOrNull();
        }
        return backup1.getData();
    }

    public int dequeue() {

        Node backup1 = this.next;
        int data = 0;

        if (backup1.getNextOrNull() == null) {
            data = backup1.getData();
            this.next = null;
            --this.size;
            return data;
        }

        while (backup1.getNextOrNull().getNextOrNull() != null) {
            backup1 = backup1.getNextOrNull();
        }

        data = backup1.getNextOrNull().getData();
        backup1.setNext(null);

        --this.size;
        return data;
    }

    public int getSize() {
        return this.size;
    }
}