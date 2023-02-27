package academy.pocu.comp3500.lab7.app;

import academy.pocu.comp3500.lab7.Decryptor;
import academy.pocu.comp3500.lab7.Tri;

public class Program {

    public static void main(String[] args) {

        Tri tri = new Tri();
        tri.inputData("aah");
        tri.inputData("aback");
        tri.inputData("abacus");
        tri.inputData("abandon");
        tri.inputData("able");
        tri.inputData("ably");
        tri.inputData("babble");

        String[] codeWords = new String[]{"cat", "CATS", "AcTS", "SCAN", "acre", "aNTS"};

        Decryptor decryptor = new Decryptor(codeWords);
    }
}
