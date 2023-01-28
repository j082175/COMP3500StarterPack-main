package academy.pocu.comp3500.assignment2;

import java.io.BufferedWriter;
import java.io.IOException;

import academy.pocu.comp3500.assignment2.datastructure.Queue;
import academy.pocu.comp3500.assignment2.datastructure.Stack;

public final class Logger {
    // private static Queue<String> queue = new Queue<>();
    private static Stack<Indent> stack = new Stack<>();

    private static int indentLevel;

    private static Indent indent = new Indent();

    public static void log(final String text) {

        indent.setLinkedList(text);
    }

    public static void printTo(final BufferedWriter writer) throws IOException {
        int length = indent.getLinkedList().getSize();

        if (length == 0) {
            return;
        }

        String result = null;
        for (int i = 0; i < length - 1; i++) {
            result = indent.getLinkedList().removeFirst();
            writer.write(result);
            writer.write(System.lineSeparator());
        }

        result = indent.getLinkedList().removeFirst();
        writer.write(result);

        writer.flush();
        writer.close();
    }

    public static void printTo(final BufferedWriter writer, final String filter) {

    }

    public static void clear() {
        int length = indent.getLinkedList().getSize();
        for (int i = 0; i < length; i++) {
            indent.getLinkedList().removeFirst();
        }
    }

    public static Indent indent() {
        // 들여쓰기는 빈칸 2개

        indent.plus();

        return indent;
    }

    public static void unindent() {
        indent.minus();
    }
}