package academy.pocu.comp3500.assignment2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;

import academy.pocu.comp3500.assignment2.datastructure.LinkedList;
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
        int length = indent.getStorage().getSize();

        if (length == 0) {
            return;
        }

        String result = null;
        Iterator<LinkedList<String>> iter1 = indent.getStorage().iterator();

        while (iter1.hasNext()) {
            Iterator<String> iter2 = iter1.next().iterator();

            while (iter2.hasNext()) {
                result = iter2.next();
                writer.write(result);
                writer.write(System.lineSeparator());
            }
        }

    }

    public static void printTo(final BufferedWriter writer, final String filter) {

    }

    public static void clear() {
        int length = indent.getStorage().getSize();
        for (int i = 0; i < length; i++) {
            indent.getStorage().removeLast();
        }
    }

    public static Indent indent() {
        // 들여쓰기는 빈칸 2개

        indent.plus();
        indent.getStorage().addLast(new LinkedList<>());

        return indent;
    }

    public static void unindent() {
        indent.minus();
    }
}