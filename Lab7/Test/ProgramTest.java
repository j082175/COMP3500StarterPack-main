import academy.pocu.comp3500.lab7.Decryptor;
import org.junit.jupiter.api.Test;


class ProgramTest {

    @Test
    void main() {


        String[] codeWords = new String[]{"cat", "CATS", "AcTS", "SCAN", "acre", "aNTS"};

        Decryptor decryptor = new Decryptor(codeWords);

        String[] candidates = decryptor.findCandidates("cat");

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

        B06_TEST();
        test2();
    }

    private static void B06_TEST() {
        String[] codeWords = new String[] {"ART", "KIO", "jio"};

        Decryptor decryptor = new Decryptor(codeWords);
        String[] candidates = decryptor.findCandidates("jio");

        assert (candidates.length == 1);
        assert (candidates[0].equals("jio"));
    }

    void test2(){
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