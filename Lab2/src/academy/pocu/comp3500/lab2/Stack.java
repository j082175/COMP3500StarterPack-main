package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class Stack {
    private Node next;
    private int size;

    public Stack() {
        // this.size = 0;
        // this.next = null;
    }

    public void push(final int data) {
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
        return this.next.getData();
    }

    public int pop() {
        Node backup1 = this.next.getNextOrNull();
        int data = this.next.getData();

        this.next = backup1;
        --this.size;
        return data;
    }

    public int getSize() {
        return this.size;
    }
}