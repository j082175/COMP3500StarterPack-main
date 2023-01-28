package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.LinkedList;
import academy.pocu.comp3500.assignment2.datastructure.Queue;
import academy.pocu.comp3500.assignment2.datastructure.Stack;

public final class Indent {
    // private LinkedList<String> linkedList = new LinkedList<>();
    // private LinkedList<LinkedList<String>> storage = new LinkedList<>();

    private Queue<String> queue = new Queue<>();

    private static int count;
    private int index;

    private static int indentLevel;

    public Indent() {
        index = count;
        count++;
    }

    private static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

    public void discard() {
        //storage.removeLast();

        int length = queue.getSize();
        for (int i = 0; i < length; i++) {
            queue.dequeue();
        }
    }

    public Queue<String> getQueue() {
        return this.queue;
    }

    public void setLinkedList(String text) {
        String source = padLeft(text, text.length() + indentLevel);

        // if (storage.getSize() == 0) {
        //     storage.addLast(new LinkedList<>());
        // }
        // storage.getLast().add(source);

        queue.enqueue(source);
    }

    public void plus() {
        Indent.indentLevel = Indent.indentLevel + 2;
    }

    public void minus() {
        if (Indent.indentLevel >= 2) {
            Indent.indentLevel = Indent.indentLevel - 2;
        }
    }

    public int getIndentLevel() {
        return Indent.indentLevel;
    }

    public void resetIndentLevel() {
        Indent.indentLevel = 0;
    }
}