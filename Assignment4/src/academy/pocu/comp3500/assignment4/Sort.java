package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

import java.util.ArrayList;

public class Sort {

    public static void quickSort(ArrayList<Task> arrayList) {
        quickSortRecursive(arrayList, 0, arrayList.size() - 1);
    }

    private static void quickSortRecursive(ArrayList<Task> arrayList, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivotPos = partition(arrayList, left, right);

        quickSortRecursive(arrayList, left, pivotPos - 1);
        quickSortRecursive(arrayList, pivotPos + 1, right);
    }

    private static int partition(ArrayList<Task> arrayList, int left, int right) {
        int pivot = arrayList.get(right).getEstimate();

        int i = left;
        for (int j = left; j < right; ++j) {
            if (arrayList.get(j).getEstimate() > pivot) {
                swap(arrayList, i, j);
                ++i;
            }
        }

        int pivotPos = i;
        swap(arrayList, pivotPos, right);

        return pivotPos;
    }

    private static void swap(ArrayList<Task> arrayList, int left, int right) {
        Task task = arrayList.get(left);
        arrayList.set(left, arrayList.get(right));
        arrayList.set(right, task);
    }
}
