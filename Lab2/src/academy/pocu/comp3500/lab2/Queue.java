package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class Queue {
    private Node next;
    private int size;

    public Queue() {
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

        while (backup1.getNextOrNull() != null) {
            backup1 = backup1.getNextOrNull();
        }

        backup1.setNext(new Node(data));
        ++this.size;
    }

    public int peek() {
        return this.next.getData();
    }

    public int dequeue() {
        int data = this.next.getData();
        this.next = this.next.getNextOrNull();
        --this.size;
        return data;
    }

    public int getSize() {
        return this.size;
    }
}