package academy.pocu.comp3500.lab7;

import java.util.ArrayList;

public class Decryptor {

    private final String[] codeWords2;
    private final String[] sortedCodeWords2;
    private final ArrayList<String> arrayList = new ArrayList<>();
    private final Tri tri = new Tri();

    public Decryptor(final String[] codeWords) {
        codeWords2 = new String[codeWords.length];
        sortedCodeWords2 = new String[codeWords2.length];

        for (int i = 0; i < codeWords.length; i++) {
            char[] charArr = codeWords[i].toLowerCase().toCharArray();
            String str2 = new String(charArr);

            // arrayList.add(str2);
            codeWords2[i] = str2;

            // tri.inputData(codeWords2[i]);

/*            sortString(charArr);
            String str3 = new String(charArr);
            sortedCodeWords2[i] = str3;*/
        }

        sortStringArr(codeWords2);

        for (int i = 0; i < codeWords.length; i++) {
            tri.inputData(codeWords2[i]);
        }

    }

    public String[] findCandidates(final String word) {

        String compare = charToString(word.toLowerCase().toCharArray());

        ArrayList<String> a = new ArrayList<>();

        boolean is = tri.isExist(compare, a);

/*        for (int i = 0; i < codeWords2.length; i++) {
            if (compare.equals(sortedCodeWords2[i])) {
                a.add(codeWords2[i]);
            }
        }*/

        String[] result = new String[a.size()];
        for (int i = 0; i < a.size(); i++) {
            result[i] = a.get(i);
        }

        return result;


        /*String str = charToString(word.toCharArray());

        ArrayList<String> nodeArrayList = new ArrayList<>();
        boolean result = tri.isExist(str, nodeArrayList); // logn

        List<String> resultStr = new ArrayList<>();
        // String[] resultStr = new String[arr.length];

        if (result) {
            for (int i = 0; i < codeWords2.length; i++) { // n
                char[] ch2 = arrayList.get(i).toCharArray();
                // Arrays.sort(ch2);
                sortString(ch2);
                String ss = new String(ch2);

                if (ss.equals(str)) {
                    resultStr.add(codeWords2[i]);
                    // resultStr[i] = arr[i];
                }
            }

            String[] s = new String[resultStr.size()]; // n
            for (int i = 0; i < resultStr.size(); i++) {
                s[i] = resultStr.get(i);
            }

            return s;
        }*/


        // return new String[0];
    }

    private void sortString(char[] charArr) {
        quickSortRecursive(charArr, 0, charArr.length - 1);
    }

    private void quickSortRecursive(char[] charArr, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(charArr, left, right);

        quickSortRecursive(charArr, left, pivot - 1);
        quickSortRecursive(charArr, pivot + 1, right);
    }

    private int partition(char[] charArr, int left, int right) {
        int i = left;
        for (int j = left; j < right; j++) {
            if (charArr[j] < charArr[right]) {
                char temp = charArr[j];
                charArr[j] = charArr[i];
                charArr[i] = temp;

                ++i;
            }
        }

        char temp2 = charArr[i];
        charArr[i] = charArr[right];
        charArr[right] = temp2;

        return i;
    }

    private void sortStringArr(String[] strings) {
        quickSortRecursive(strings, 0, strings.length - 1);
    }

    private void quickSortRecursive(String[] strings, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(strings, left, right);

        quickSortRecursive(strings, left, pivot - 1);
        quickSortRecursive(strings, pivot + 1, right);
    }

    private int partition(String[] strings, int left, int right) {
        int i = left;
        for (int j = left; j < right; j++) {

            if (strings[j].compareTo(strings[right]) < 0) {
                String temp = strings[j];
                strings[j] = strings[i];
                strings[i] = temp;

                ++i;
            }


        }

        String temp2 = strings[i];
        strings[i] = strings[right];
        strings[right] = temp2;

        return i;
    }

    private String charToString(char[] charArr) {
        sortString(charArr);
        return new String(charArr);
    }
}