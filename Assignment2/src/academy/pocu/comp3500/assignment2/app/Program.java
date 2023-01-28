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
}
