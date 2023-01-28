package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.LinkedList;

public final class Indent {
    private LinkedList<String> linkedList = new LinkedList<>();

    private int indentLevel;

    private static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

    public void discard() {
        for (int i = 0; i < 2; i++) {
            this.linkedList.removeLast();
        }
    }

    public LinkedList<String> getLinkedList() {
        return this.linkedList;
    }

    public void setLinkedList(String text) {
        String source = padLeft(text, text.length() + indentLevel);

        linkedList.addLast(source);
    }

    public void plus() {
        this.indentLevel = this.indentLevel + 2;
    }

    public void minus() {
        if (this.indentLevel >= 2) {
            this.indentLevel = this.indentLevel - 2;
        }
    }

    public int get() {
        return this.indentLevel;
    }
}