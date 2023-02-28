package academy.pocu.comp3500.lab7;

import java.util.ArrayList;
import java.util.Arrays;

public class Decryptor {

    private final String[] arr;
    private final ArrayList<String> arrayList = new ArrayList<>();
    private final Tri tri = new Tri();
    public Decryptor(final String[] codeWords) {
        arr = new String[codeWords.length];
        for (int i = 0; i < codeWords.length; i++) {
            char[] charArr = codeWords[i].toLowerCase().toCharArray();
            String str2 = new String(charArr);

            arrayList.add(str2);
            arr[i] = str2;

            Arrays.sort(charArr);
            String str3 = new String(charArr);
            tri.inputData(str3);
        }
        // Arrays.sort(arr);

    }

    public String[] findCandidates(final String word) {
        String str = word.toLowerCase();
        char[] charArr = str.toCharArray();
        Arrays.sort(charArr);
        String str2 = new String(charArr);

        boolean result = tri.isExist(str2);

        ArrayList<String> resultStr = new ArrayList<>();
        if (result) {
            for (int i = 0; i < arr.length; i++) {
                char[] ch2 = arrayList.get(i).toCharArray();
                Arrays.sort(ch2);
                String ss = new String(ch2);

                if (ss.equals(str2)) {
                    resultStr.add(arr[i]);
                }
            }

            String[] s = new String[resultStr.size()];
            for (int i = 0; i < resultStr.size(); i++) {
                s[i] = resultStr.get(i);
            }

            return s;
        }


        return new String[0];
    }
}