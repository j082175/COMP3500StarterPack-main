package academy.pocu.comp3500.lab7;

import java.util.ArrayList;

public class Decryptor {

    private final String[] codeWords2;
    private final Tri tri = new Tri();

    public Decryptor(final String[] codeWords) {
        codeWords2 = new String[codeWords.length];

        for (int i = 0; i < codeWords.length; i++) {
            char[] charArr = codeWords[i].toLowerCase().toCharArray();
            String str2 = new String(charArr);

            codeWords2[i] = str2;
        }

        for (int i = 0; i < codeWords.length; i++) {
            tri.inputData(codeWords2[i]);
        }
    }

    public String[] findCandidates(final String word) {
        String compare = charToString(word.toLowerCase().toCharArray());

        ArrayList<String> listResult = tri.isExist(compare);

        String[] result = new String[listResult.size()];
        for (int i = 0; i < listResult.size(); i++) {
            result[i] = listResult.get(i);
        }

        return result;
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

    private String charToString(char[] charArr) {
        sortString(charArr);
        return new String(charArr);
    }
}