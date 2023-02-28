package academy.pocu.comp3500.lab7;

import java.util.ArrayList;
import java.util.List;

public class Decryptor {

    private final String[] arr;
    private final ArrayList<String> arrayList = new ArrayList<>();
    private final String[] sortedArr;
    private final Tri tri = new Tri();

    public Decryptor(final String[] codeWords) {
        arr = new String[codeWords.length];
        sortedArr = new String[arr.length];

        for (int i = 0; i < codeWords.length; i++) {
            char[] charArr = codeWords[i].toLowerCase().toCharArray();
            String str2 = new String(charArr);

            arrayList.add(str2);
            arr[i] = str2;

            // Arrays.sort(charArr);
            sortString(charArr);

            String str3 = new String(charArr);
            sortedArr[i] = str3;
            // tri.inputData(str3);
        }

        // Arrays.sort(sortedArr);
        sortStringArr(sortedArr);

        for (int i = 0; i < codeWords.length; i++) {
            tri.inputData(sortedArr[i]);
        }

    }

    public String[] findCandidates(final String word) {
        String str = word.toLowerCase();
        char[] charArr = str.toCharArray();
        // Arrays.sort(charArr);
        sortString(charArr);
        String str2 = new String(charArr);

        boolean result = tri.isExist(str2);

        List<String> resultStr = new ArrayList<>();
        // String[] resultStr = new String[arr.length];

        if (result) {
            for (int i = 0; i < arr.length; i++) {
                char[] ch2 = arrayList.get(i).toCharArray();
                // Arrays.sort(ch2);
                sortString(ch2);
                String ss = new String(ch2);

                if (ss.equals(str2)) {
                    resultStr.add(arr[i]);
                    // resultStr[i] = arr[i];
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
}