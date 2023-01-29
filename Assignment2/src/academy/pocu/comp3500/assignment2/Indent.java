package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.LinkedList;
import academy.pocu.comp3500.assignment2.datastructure.Queue;
import academy.pocu.comp3500.assignment2.datastructure.Stack;

public final class Indent {
    // private LinkedList<String> linkedList = new LinkedList<>();
    // private LinkedList<LinkedList<String>> storage = new LinkedList<>();

    private LinkedList<String> queue = new LinkedList<>();

    private Indent next;
    private Indent before;
    private String data;

    private static int count;
    private int index;

    private static int indentLevel;

    public Indent() {
        index = count;
        count++;
    }

    public void setNext(Indent i) {
        this.next = i;
    }

    public Indent getNext() {
        return this.next;
    }

    public void setBefore(Indent i) {
        this.before = i;
    }

    public Indent getBefore() {
        return this.before;
    }

    public void setData(String text) {
        // String source = padLeft(text, text.length() + indentLevel);

        if (text == null) {
            this.data = null;
            return;
        }

        if (data == null) {
            //this.data = text + System.lineSeparator();
            queue.addLast(text);
            return;
        }

        this.data += text;
        //this.data += System.lineSeparator();
    }

    public String getData() {
        return this.data;
    }

    private static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

    public void discard() {
        before.setNext(null);
        Logger.resetEnd(before);
        indentLevel = index;
    }

    public LinkedList<String> getQueue() {
        return this.queue;
    }

    public void setLinkedList(String text) {
        String source = padLeft(text, text.length() + indentLevel);

        // if (storage.getSize() == 0) {
        // storage.addLast(new LinkedList<>());
        // }
        // storage.getLast().add(source);


        queue.addLast(source);
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