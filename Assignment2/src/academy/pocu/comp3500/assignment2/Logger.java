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

    //private static Indent indent = new Indent();

    private static LinkedList<Indent> storage = new LinkedList<>();

    public static void log(final String text) {

        //indent.setLinkedList(text);

        if (storage.getSize() == 0) {
            storage.add(new Indent());
        }
        storage.getLast().setLinkedList(text);
    }

    public static void printTo(final BufferedWriter writer) throws IOException {
        int length = storage.getSize();

        if (length == 0) {
            return;
        }

        String result = null;
        Iterator<Indent> iter1 = storage.iterator();
        while (iter1.hasNext()) {
            Indent q = iter1.next();
            int length2 = q.getQueue().getSize();

            for (int i = 0; i < length2; i++) {
                result = q.getQueue().dequeue();
                writer.write(result);
                writer.newLine();
            }
        }

    }

    public static void printTo(final BufferedWriter writer, final String filter) throws IOException {
        int length = storage.getSize();

        if (length == 0) {
            return;
        }

        String result = null;
        Iterator<Indent> iter1 = storage.iterator();

        while (iter1.hasNext()) {

            Indent q = iter1.next();
            int length2 = q.getQueue().getSize();

            for (int i = 0; i < length2; i++) {
                result = q.getQueue().dequeue();
                if (result.contains(filter)) {
                    writer.write(result);
                    writer.newLine();
                }
            }
        }
    }

    public static void clear() {
        // int length = indent.getStorage().getSize();
        // for (int i = 0; i < length; i++) {
        //     indent.getStorage().removeLast();
        // }

        storage.getFirst().resetIndentLevel();
        storage.clear();
    }

    public static Indent indent() {
        // 들여쓰기는 빈칸 2개

        // indent.plus();
        // indent.getStorage().addLast(new LinkedList<>());

        Indent indent = new Indent();
        indent.plus();
        storage.add(indent);

        return indent;
    }

    public static void unindent() {
        
        Indent indent = new Indent();
        indent.minus();
        storage.add(indent);
    }
}