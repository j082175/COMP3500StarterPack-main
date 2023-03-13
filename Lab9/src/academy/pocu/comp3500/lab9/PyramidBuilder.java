package academy.pocu.comp3500.lab9;

public class PyramidBuilder {
    public static int findMaxHeight(final int[] widths, int statue) {
        quickSort(widths);

        // 피라미드의 돌 개수는 최소 2개부터 시작
        // 피라미드 너비보다 돌 총 너비가 더 커야됨. 초과

        int height = 0;
        int totalHeight;
        int rockCount = 2;
        int count = 2;

        if (widths.length > 1) {
            totalHeight = widths[0];
        } else {
            return height;
        }

        for (int i = 1; i < widths.length; i++) {
            totalHeight += widths[i];

            if (totalHeight > statue && count >= rockCount) {
                ++height;
                totalHeight = 0;
                ++rockCount;
                count = 0;
            }

            ++count;
        }

        return height;
    }

    private static void quickSort(int[] arr) {
        quickSortRecursive(arr, 0, arr.length - 1);
    }

    private static void quickSortRecursive(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(arr, left, right);
        quickSortRecursive(arr, pivot + 1, right);
        quickSortRecursive(arr, left, pivot - 1);
    }

    private static int partition(int[] arr, int left, int right) {
        int i = left;

        for (int j = left; j < right; j++) {
            if (arr[right] > arr[j]) {
                swap(arr, i, j);
                ++i;
            }
        }

        swap(arr, right, i);

        return i;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}