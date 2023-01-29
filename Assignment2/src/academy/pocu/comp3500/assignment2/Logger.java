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

    // private static Indent indent = new Indent();

    private static LinkedList<Indent> storage = new LinkedList<>();

    private static Indent current;
    //private static Indent end = new Indent();
    private static Indent origin;

    private static int indentLevel;

    private static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

    public static void setCurrent(Indent cur) {
        current = cur;
    }

    public static void log(final String text) {

        // if (storage.getSize() == 0) {
        // storage.add(new Indent());
        // }
        // storage.getLast().setLinkedList(text);


        if (origin == null) {
            origin = new Indent();
            current = origin;

            String s = padLeft(text, indentLevel + text.length());
            current.setData(s);

            //current.setNext(end);
            //current.setBefore(null);
            // end.setBefore(current);
            // end.setNext(null);
            return;
        }

        //Indent obj = end.getBefore();

        if (indentLevel != 0) {

            Indent ind = new Indent();
            current.setNext(ind);

            current = current.getNext();

            String s = padLeft(text, indentLevel + text.length());
            current.setData(s);
        } else {

            Indent ind = new Indent();
            current.setNext(ind);

            current = current.getNext();

            current.setData(text);
        }

    }

    public static void printTo(final BufferedWriter writer) throws IOException {
        String result = null;

        Indent ind = origin;

        while (ind != null) {

            if (!ind.getDiscarede()) {
                // Iterator<String> iter = ind.getLinkedList().iterator();
                // while (iter.hasNext()) {
                //     result = iter.next();
                //     writer.write(result + System.lineSeparator());
                // }

                result = ind.getData();
                if (result != null) {
                    writer.write(result + System.lineSeparator());
                }

            }

            ind = ind.getNext();
        }

    }

    public static void printTo(final BufferedWriter writer, final String filter) throws IOException {
        String result = null;

        Indent ind = origin;

        while (ind != null) {

            if (!ind.getDiscarede()) {
                // Iterator<String> iter = ind.getLinkedList().iterator();
                // while (iter.hasNext()) {
                //     result = iter.next();
                //     if (result.contains(filter)) {
                //         writer.write(result + System.lineSeparator());
                //     }
                // }

                result = ind.getData();
                if (result != null && result.contains(filter)) {
                    writer.write(result + System.lineSeparator());
                }
            }

            ind = ind.getNext();
        }
    }

    public static void clear() {

        // if (storage.getSize() != 0) {
        // storage.getFirst().resetIndentLevel();
        // storage.clear();
        // }

        origin = null;

        indentLevel = 0;
    }

    public static Indent indent() {
        // 들여쓰기는 빈칸 2개

        Indent indent = null;

        if (current != null) {
            indent = new Indent();
            current.setNext(indent);


            current = current.getNext();

        }


        indentLevel = indentLevel + 2;

        return indent;
    }

    public static void unindent() {

        // Indent indent = new Indent();
        // indent.minus();
        // storage.add(indent);
        // ---------------------------------------------
        // Indent indent = end.getBefore();

        // String str = indent.getData();
        // str = str.stripLeading(); // 앞의 공백 제거
        // indent.setData(null);
        // indent.setData(str);

        indentLevel = indentLevel - 2;
    }
}