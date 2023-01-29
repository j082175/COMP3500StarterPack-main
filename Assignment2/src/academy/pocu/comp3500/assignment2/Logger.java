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

    private static Indent start;
    private static Indent end = new Indent();

    private static int indentLevel;

    private static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

    public static void resetEnd(Indent before) {
        end.setBefore(before);
    }

    public static void log(final String text) {

        // if (storage.getSize() == 0) {
        // storage.add(new Indent());
        // }
        // storage.getLast().setLinkedList(text);

        if (start == null) {
            start = new Indent();

            String s = padLeft(text, indentLevel + text.length());
            start.setData(s);

            start.setNext(end);
            start.setBefore(null);
            end.setBefore(start);
            end.setNext(null);
            return;
        }

        Indent obj = end.getBefore();

        if (indentLevel != 0) {
            String s = padLeft(text, indentLevel + text.length());
            obj.setData(s);
        } else {
            obj.setData(text);
        }

    }

    public static void printTo(final BufferedWriter writer) throws IOException {
        // int length = storage.getSize();

        // if (length == 0) {
        // return;
        // }

        String result = null;

        // Iterator<Indent> iter1 = storage.iterator();
        // while (iter1.hasNext()) {
        // Indent q = iter1.next();
        // int length2 = q.getQueue().getSize();

        // for (int i = 0; i < length2; i++) {
        // result = q.getQueue().dequeue();
        // writer.write(result);
        // writer.newLine();
        // }
        // }

        Indent ind = start;
        

        while (ind != null) {

            Iterator<String> iter = ind.getQueue().iterator();
            while (iter.hasNext()) {
                result = iter.next();
                writer.write(result + System.lineSeparator());
            }

            ind = ind.getNext();
        }

    }

    public static void printTo(final BufferedWriter writer, final String filter) throws IOException {
        // int length = storage.getSize();

        // if (length == 0) {
        //     return;
        // }

        String result = null;
        // Iterator<Indent> iter1 = storage.iterator();

        // while (iter1.hasNext()) {

        //     Indent q = iter1.next();
        //     int length2 = q.getQueue().getSize();

        //     for (int i = 0; i < length2; i++) {
        //         result = q.getQueue().dequeue();
        //         if (result.contains(filter)) {
        //             writer.write(result);
        //             writer.newLine();
        //         }
        //     }
        // }


        Indent ind = start;

        while (ind != null) {

            Iterator<String> iter = ind.getQueue().iterator();
            while (iter.hasNext()) {
                result = iter.next();
                if (result.contains(filter)) {
                    writer.write(result + System.lineSeparator());
                }
            }

            ind = ind.getNext();
        }
    }

    public static void clear() {

        // if (storage.getSize() != 0) {
        //     storage.getFirst().resetIndentLevel();
        //     storage.clear();
        // }

        start = null;
        end = new Indent();

    }

    public static Indent indent() {
        // 들여쓰기는 빈칸 2개

        // Indent indent = new Indent();
        // indent.plus();
        // storage.add(indent);

        Indent indent = null;

        if (start != null) {
            indent = new Indent();

            Indent before = end.getBefore();
            before.setNext(indent);
            indent.setNext(end);
            indent.setBefore(before);
            end.setBefore(indent);
        }


        //indent.setData("  ");

        indentLevel = indentLevel + 2;

        return indent;
    }

    public static void unindent() {

        // Indent indent = new Indent();
        // indent.minus();
        // storage.add(indent);
//---------------------------------------------
        // Indent indent = end.getBefore();

        // String str = indent.getData();
        // str = str.stripLeading(); // 앞의 공백 제거
        // indent.setData(null);
        // indent.setData(str);

        indentLevel = indentLevel - 2;
    }
}