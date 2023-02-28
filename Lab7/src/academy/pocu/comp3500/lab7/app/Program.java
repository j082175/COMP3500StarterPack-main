package academy.pocu.comp3500.lab7.app;

import academy.pocu.comp3500.lab7.Decryptor;

public class Program {

    public static void main(String[] args) {
        // test2();

/*        Tri tri = new Tri();
        tri.inputData("aah");
        tri.inputData("aback");
        tri.inputData("abacus");
        tri.inputData("abandon");
        tri.inputData("able");
        tri.inputData("ably");
        tri.inputData("babble");

        String[] codeWords = new String[]{"cat", "CATS", "AcTS", "SCAN", "acre", "aNTS"};

        Decryptor decryptor = new Decryptor(codeWords);
        String[] result = decryptor.findCandidates("cat");*/

       //  String[] codeWords = new String[]{"cat", "CATS", "AcTS", "SCAN", "acre", "aNTS"};
        String[] codeWords = new String[]{"TcA", "tcaa", "tcaaa"};

        Decryptor decryptor = new Decryptor(codeWords);

        String[] candidates = decryptor.findCandidates("cataa");

        assert (candidates.length == 1);
        assert (candidates[0].equals("cat"));

        candidates = decryptor.findCandidates("race");

        assert (candidates.length == 1);
        assert (candidates[0].equals("acre"));

        candidates = decryptor.findCandidates("ca");

        assert (candidates.length == 0);

        candidates = decryptor.findCandidates("span");

        assert (candidates.length == 0);

        candidates = decryptor.findCandidates("ACT");

        assert (candidates.length == 1);
        assert (candidates[0].equals("cat"));

        candidates = decryptor.findCandidates("cats");

        assert (candidates.length == 2);
        assert (candidates[0].equals("cats") || candidates[0].equals("acts"));
        assert (candidates[1].equals("cats") || candidates[1].equals("acts"));

        candidates = decryptor.findCandidates("SCAt");

        assert (candidates.length == 2);
        assert (candidates[0].equals("cats") || candidates[0].equals("acts"));
        assert (candidates[1].equals("cats") || candidates[1].equals("acts"));
    }

    static void test2(){
        String[] codeWords = new String[]{
                "aaabcbcbaaabbbcccc",
                "aaabbbbbcccaaabccc",
                "aaabbbcccaaabbbccc",
                "aaaabbbcccbbbcaacc",
                "aaabbbbccbcaaabccc",
                "aacabbbcccaacabbcb",
                "aaccabbbcbccbaacab",
                "aacaccbcaacbabbcbb",
                "aabcccQWERTccaaaa",
                "aaacbOIUKJHbbabcca",
                "aacabHJKBNMbbcaaab",
                "aaabbJKLIOPaabbbccc",
        };
        Decryptor decryptor = new Decryptor(codeWords);
        String[] candidates = decryptor.findCandidates("aaabbbcccaaabbbccc");
        assert (candidates.length == 8);

    }
}
