package academy.pocu.comp3500.assignment2.app;

import static academy.pocu.comp3500.assignment2.Logger.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import academy.pocu.comp3500.assignment2.Indent;
import academy.pocu.comp3500.assignment2.Logger;

public class Program {

    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

    public static void main(String[] args) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(
                "C://Users//aa//Documents//POCU//COMP3500StarterPack-main//COMP3500StarterPack-main//Assignment2//src//academy//pocu//comp3500//assignment2//mylog.log"));

        int x = 10;

        log("first level 1");

        Indent indent = Logger.indent();
        {
            log("second level 1");
            log("second level 2");

            if (x % 2 == 1) {
                indent.discard();
            }
        }
        Logger.unindent();

        log("first level 2");
        Logger.printTo(writer);

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
