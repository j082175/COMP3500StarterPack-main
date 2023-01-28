package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.LinkedList;
import academy.pocu.comp3500.assignment2.datastructure.Stack;

public final class Indent {
    // private LinkedList<String> linkedList = new LinkedList<>();
    private LinkedList<LinkedList<String>> storage = new LinkedList<>();

    private int indentLevel;

    private static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

    public void discard() {
        storage.removeLast();
    }

    public LinkedList<LinkedList<String>> getStorage() {
        return this.storage;
    }

    public void setLinkedList(String text) {
        String source = padLeft(text, text.length() + indentLevel);

        if (storage.getSize() == 0) {
            storage.addLast(new LinkedList<>());
        }
        storage.getLast().add(source);
    }

    public void plus() {
        this.indentLevel = this.indentLevel + 2;
    }

    public void minus() {
        if (this.indentLevel >= 2) {
            this.indentLevel = this.indentLevel - 2;
        }
    }

    public int getIndentLevel() {
        return this.indentLevel;
    }
}