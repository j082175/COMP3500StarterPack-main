package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class Queue {
    private Node tail;
    private Node head;
    private int size;

    public Queue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void enqueue(final int data) {
        Node newNode = new Node(data);

        if (this.size == 0) {
            this.head = newNode;
        } else {
            this.tail.setNext(newNode);
        }

        this.tail = newNode;
        this.size++;
    }

    public int peek() {
        return this.head.getData();
    }

    public int dequeue() {
        int data = this.head.getData();
        this.head = this.head.getNextOrNull();

        --this.size;
        return data;
    }

    public int getSize() {
        return this.size;
    }
}