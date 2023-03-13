package academy.pocu.comp3500.lab9;

import academy.pocu.comp3500.lab9.data.Task;

public class ProfitCalculator {
    public static int findMaxProfit(final Task[] tasks, final int[] skillLevels) {
        quickSort(tasks);

        int sum = 0;
        for (int i = 0; i < skillLevels.length; i++) {
            for (int j = 0; j < tasks.length; j++) {
                if (skillLevels[i] >= tasks[j].getDifficulty()) {
                    sum += tasks[j].getProfit();
                    break;
                }
            }
        }

        return sum;
    }

    private static void quickSort(Task[] arr) {
        quickSortRecursive(arr, 0, arr.length - 1);
    }

    private static void quickSortRecursive(Task[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(arr, left, right);
        quickSortRecursive(arr, pivot + 1, right);
        quickSortRecursive(arr, left, pivot - 1);
    }

    private static int partition(Task[] arr, int left, int right) {
        int i = left;

        for (int j = left; j < right; j++) {
            if (arr[right].getProfit() < arr[j].getProfit()) {
                swap(arr, i, j);
                ++i;
            }
        }

        swap(arr, right, i);

        return i;
    }

    private static void swap(Task[] arr, int i, int j) {
        Task temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}