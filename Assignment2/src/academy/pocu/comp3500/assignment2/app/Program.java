package academy.pocu.comp3500.assignment2.app;

import static academy.pocu.comp3500.assignment2.Logger.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import academy.pocu.comp3500.assignment2.Indent;
import academy.pocu.comp3500.assignment2.Logger;
import academy.pocu.comp3500.assignment2.datastructure.Sort;

public class Program {
    private static final String path = "C://Users//aa//Documents//POCU//COMP3500StarterPack-main//COMP3500StarterPack-main//Assignment2//src//academy//pocu//comp3500//assignment2//mylog.log";
    private static final String quicksort1Path = "C://Users//aa//Documents//POCU//COMP3500StarterPack-main//COMP3500StarterPack-main//Assignment2//src//academy//pocu//comp3500//assignment2//quicksort1.log";
    private static final String quicksort2Path = "C://Users//aa//Documents//POCU//COMP3500StarterPack-main//COMP3500StarterPack-main//Assignment2//src//academy//pocu//comp3500//assignment2//quicksort2.log";

    public static void main(String[] args) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(path));

        testCombined();

        /////////////////////////////////////////////////
        // log("1");
        // Indent i1 = Logger.indent();
        // log("2");
        // Indent i2 = Logger.indent();
        // log("3");
        // Logger.unindent();
        // log("4");
        // Indent i3 = Logger.indent();
        // log("5");
        // Indent i4 = Logger.indent();
        // Indent i5 = Logger.indent();
        // Indent i6 = Logger.indent();
        // Indent i7 = Logger.indent();
        // Indent i8 = Logger.indent();
        // log("?");
        // i1.discard();
        // log("???");
        // i3.discard();
        // Logger.printTo(writer);
        // Logger.clear();
        // Logger.indent();
        // log("6");
        // Logger.printTo(writer);
        // Logger.clear();
        // Logger.printTo(writer);
        // writer.close();

        // testTotal();

        // testCombined();
    }

    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

    private static void doMagic() {
        Indent indent = Logger.indent();
        {
            log("you can also nest an indent");
            log("like this!");
        }
        Logger.unindent();
    }

    private static void test2() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));

        int[] nums = new int[] { 1, 2, 3, 4 };

        log("call sum()");
        int sum = sum(nums);

        log("call average()");
        double average = calculateAverage(nums);

        Logger.printTo(writer);

        writer.close();
    }

    private static void test1() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(
                path));

        log("hello");
        log("world");

        Logger.printTo(writer);
        writer.close();
    }

    private static void test3() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));

        log("this is not indented");

        Logger.indent();
        {
            log("but this is");
            log("so is this");
        }
        Logger.unindent();

        log("but not this");
        Logger.printTo(writer);

        writer.close();
    }

    private static void test4() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));

        log("first level 1");

        Logger.indent();
        {
            log("second level 1");
            log("second level 2");

            Logger.indent();
            {
                log("third level 1");
                log("third level 2");
            }
            Logger.unindent();

            log("second level 3");
        }
        Logger.unindent();

        log("first level 2");
        Logger.printTo(writer);
        writer.close();
    }

    private static void test5() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));

        int x = 10;

        log("first level 1");

        Indent indent = Logger.indent();
        {
            log("second level 1");
            log("second level 2");

            if (x % 2 == 0) {
                indent.discard();
            }
        }
        Logger.unindent();

        log("first level 2");
        Logger.printTo(writer);
        writer.close();
    }

    private static void test6() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));

        log("hello");
        log("world");
        log("this is logging at the top level");

        Logger.indent();
        {
            log("using indent, you can indent to organize your logs");
            log("call unindent() to decrease the indentation level");
        }
        Logger.unindent();

        Indent indent = Logger.indent();
        {
            log("whatever I say here");
            log("is discarded!");
            log("too bad!");

            indent.discard();
        }
        Logger.unindent();

        Logger.indent();
        {
            log("this won't be discarded");
            log("it's true!");

            doMagic();
        }
        Logger.unindent();

        log("back to the top level!");
        log("and let's print the logs");

        Logger.printTo(writer);

        Logger.clear();

        log("log was just cleared");
        log("so you start logging from the top level again");

        Logger.printTo(writer);

        writer.close();
    }

    private static int sum(int[] nums) {
        int sum = 0;
        for (int i = 0; i < nums.length; ++i) {
            log(String.format("sum + %d", nums[i]));
            sum += nums[i];
            log(String.format("sum: %d", sum));
        }

        log(String.format("return sum: %d", sum));
        return sum;
    }

    private static double calculateAverage(int[] nums) {
        log("call sum()");
        int sum = sum(nums);

        log(String.format("sum / nums.length: %d / %d", sum, nums.length));
        double average = sum / (double) nums.length;

        log(String.format("return average: %f", average));
        return average;
    }

    private static void testTotal() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));

        log("hello");
        log("world");
        log("this is logging at the top level");

        Logger.indent();
        {
            log("using indent, you can indent to organize your logs");
            log("call unindent() to decrease the indentation level");
        }
        Logger.unindent();

        Indent indent = Logger.indent();
        {
            log("whatever I say here");
            log("is discarded!");
            log("too bad!");

            indent.discard();
        }
        Logger.unindent();

        Logger.indent();
        {
            log("this won't be discarded");
            log("it's true!");

            doMagic();
        }
        Logger.unindent();

        log("back to the top level!");
        log("and let's print the logs");

        Logger.printTo(writer);

        Logger.clear();

        log("log was just cleared");
        log("so you start logging from the top level again");

        Logger.printTo(writer);

        writer.close();

        Logger.clear();

        {
            final BufferedWriter writer1 = new BufferedWriter(new FileWriter(quicksort1Path));
            final BufferedWriter writer2 = new BufferedWriter(new FileWriter(quicksort2Path));

            int[] nums = new int[] { 30, 10, 80, 90, 50, 70, 40 };

            Sort.quickSort(nums);

            Logger.printTo(writer1);
            writer1.close();

            Logger.printTo(writer2, "90");

            writer2.close();
        }
    }

    private static void testCombined() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));

        log("a");
        Indent indent = Logger.indent();
        log("b");
        Indent indent2 = Logger.indent();
        log("c");
        log("d");
        log("e");
        log("f");
        Indent indent3 = Logger.indent();
        log("g");
        log("h");
        log("i");
        Logger.unindent();
        log("a");
        log("b");
        Logger.unindent();
        log("c");
        log("d");
        Indent indent4 = Logger.indent();
        log("e");
        log("f");
        log("g");
        Logger.unindent();
        log("hoho");

        indent.discard();
        log("start");
        Indent indent5 = Logger.indent();
        log("start2");
        indent5.discard();
        Logger.indent();
        log("start3");

        Logger.printTo(writer);
        writer.close();
    }

    private static void testtaehyunjo1() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));

        log("first level 1");

        Indent indent = Logger.indent();
        {
            log("second level 1");
            log("second level 2");

            doMagic();

            log("second level 3");
        }
        Logger.unindent();
        indent.discard(); // ### 추가 부분 ###

        log("first level 2");

        Logger.printTo(writer);
        writer.close();
    }

    private static void testtaehyunjo2() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        log("first level 1");
        Indent indent0 = Logger.indent();
        log("second level 1");
        log("second level 2");
        Indent indent1 = Logger.indent();
        log("third level 1");
        log("third level 2");
        Logger.unindent();
        log("second level 3");
        Logger.unindent();
        log("first level 2");
        Indent indent2 = Logger.indent();
        log("second level 4");
        Indent indent3 = Logger.indent();
        log("third level 3");

        indent0.discard();
        Logger.printTo(writer);
        writer.close();
    }

    private static void testZip() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));

        Indent level1 = Logger.indent();
        {
            log("Richard the Third");
            log("Best bib and tucker");

            Indent level2 = Logger.indent();
            {
                log("Which witch is which?");
            }
            Logger.unindent();

            log("You can lead a horse to water but you can't make it drink");
            log("Chinless wonder");
            log("(In the) nick of time");
            log("Double entendre");
        }
        Logger.unindent();

        log("Cut off without a penny");
        log("Let not poor Nelly starve");

        level1.discard();

        Logger.printTo(writer);

        writer.close();
    }
}
