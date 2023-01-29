package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.LinkedList;
import academy.pocu.comp3500.assignment2.datastructure.Queue;
import academy.pocu.comp3500.assignment2.datastructure.Stack;

public final class Indent {
    // private LinkedList<String> linkedList = new LinkedList<>();
    // private LinkedList<LinkedList<String>> storage = new LinkedList<>();

    // private LinkedList<String> linkedList = new LinkedList<>();

    private Indent next;

    private String data;

    private static int count = -1;
    private int index;

    private int indentLevel;

    private boolean isDiscarded;

    public Indent() {
        isDiscarded = false;
        index = count;
        count++;

    }

    public boolean getDiscarede() {
        return isDiscarded;
    }

    public void setNext(Indent i) {
        this.next = i;
    }

    public Indent getNext() {
        return this.next;
    }

    public void setData(String text) {
        // String source = padLeft(text, text.length() + indentLevel);

        if (text == null) {
            this.data = null;
            return;
        }

        if (data == null) {
            this.data = text;
            // linkedList.addLast(text);
            return;
        }

        this.data += text;
        // this.data += System.lineSeparator();
    }

    public String getData() {
        return this.data;
    }

    private static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

    public void discard() {

        Indent start = this;

        if (start.isDiscarded == true) {
            return;
        }

        int currentIndentLevel = indentLevel;

        while (start != null) {
            if (currentIndentLevel <= start.getIndentLevel()) {
                start.isDiscarded = true;

            } else {
                break;
            }
            start = start.getNext();
        }

        // Indent ind = this;
        // if (ind.getLinkedList().getSize() == 0) {
        // return;
        // }

        // while (ind.getNext() != null) {
        // ind.getLinkedList().clear();

        // ind = ind.getNext();
        // }

    }

    // public LinkedList<String> getLinkedList() {
    // return this.linkedList;
    // }

    // public void setLinkedList(String text) {
    // String source = padLeft(text, text.length() + indentLevel);

    // // if (storage.getSize() == 0) {
    // // storage.addLast(new LinkedList<>());
    // // }
    // // storage.getLast().add(source);

    // linkedList.addLast(source);
    // }

    public void plusIndentLevel() {
        indentLevel = indentLevel + 2;
    }

    public void minusIndentLevel() {
        if (indentLevel >= 2) {
            indentLevel = indentLevel - 2;
        }
    }

    public void setIndentLevel(int value) {
        this.indentLevel = value;
    }

    public int getIndentLevel() {
        return indentLevel;
    }

    public void resetIndentLevel() {
        indentLevel = 0;
    }
}