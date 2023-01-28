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

    public static void main(String[] args) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(
                "C://Users//aa//Documents//POCU//COMP3500StarterPack-main//COMP3500StarterPack-main//Assignment2//src//academy//pocu//comp3500//assignment2//mylog.log"));

        BufferedWriter writer2 = new BufferedWriter(new FileWriter(
                "C://Users//aa//Documents//POCU//COMP3500StarterPack-main//COMP3500StarterPack-main//Assignment2//src//academy//pocu//comp3500//assignment2//mylog2.log"));

        // int[] nums = new int[] { 30, 10, 80, 90, 50, 70, 40 };

        // Sort.quickSort(nums);

        // Logger.printTo(writer);
        // Logger.printTo(writer2, "90");

        log("hoho");
        Logger.indent();
        Logger.printTo(writer);
        Logger.clear();
        Logger.unindent();
        log("hoho2");
        Logger.printTo(writer);

        writer.close();
        writer2.close();
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
}
