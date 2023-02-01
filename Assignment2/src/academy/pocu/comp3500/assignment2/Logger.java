package academy.pocu.comp3500.assignment2;

import java.io.BufferedWriter;
import java.io.IOException;

public final class Logger {

    private static Indent current;
    private static Indent origin;
    private static int indentLevel;

    private static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

    public static void log(final String text) {

        if (origin == null) {
            origin = new Indent();
            current = origin;

            String s = padLeft(text, indentLevel + text.length());
            current.setData(s);

            current.setIndentLevel(indentLevel);
            return;
        }

        if (indentLevel != 0) {

            Indent ind = new Indent();
            current.setNext(ind);

            current = current.getNext();

            String s = padLeft(text, indentLevel + text.length());
            current.setData(s);

            current.setIndentLevel(indentLevel);
        } else {

            Indent ind = new Indent();
            current.setNext(ind);

            current = current.getNext();

            current.setData(text);
            current.setIndentLevel(indentLevel);
        }

    }

    public static void printTo(final BufferedWriter writer) throws IOException {
        String result = null;

        Indent ind = origin;

        while (ind != null) {

            if (!ind.getDiscarede()) {

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

                result = ind.getData();
                if (result != null && result.contains(filter)) {
                    writer.write(result + System.lineSeparator());
                }
            }

            ind = ind.getNext();
        }
    }

    public static void clear() {
        origin = null;
        indentLevel = 0;
    }

    public static Indent indent() {
        // 들여쓰기는 빈칸 2개

        if (origin == null) {
            origin = new Indent();
            current = origin;
        }

        Indent indent = null;

        if (current != null) {
            indent = new Indent();
            current.setNext(indent);

            current = current.getNext();
        }

        indentLevel = indentLevel + 2;
        current.setIndentLevel(indentLevel);

        return indent;
    }

    public static void unindent() {
        if (indentLevel != 0) {
            indentLevel = indentLevel - 2;
        }
    }
}