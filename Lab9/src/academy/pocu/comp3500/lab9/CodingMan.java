package academy.pocu.comp3500.lab9;

import academy.pocu.comp3500.lab9.data.VideoClip;

import java.util.ArrayList;

public class CodingMan {
    public static int findMinClipsCount(final VideoClip[] clips, int time) {
        //quickSort(clips);
        bubbleSort(clips);

        int sum = 0;
        int minClipsCount = 0;
        int lastEnd = 0;

        ArrayList<VideoClip> arrayList = new ArrayList<>();

        if (time < 0) {
            return -1;
        }

        if (clips.length != 0) {
            arrayList.add(clips[0]);
            sum = getInterval(clips[0], 0);
            ++minClipsCount;
            lastEnd = clips[0].getEndTime();

            if (clips[0].getStartTime() != 0) {
                return -1;
            }

            if (clips[0].getEndTime() >= time) {
                return 1;
            }

        }

        int index = 0;


        for (int i = 1; i < clips.length; i++) {

/*            if (i + 1 != clips.length && clips[i + 1].getStartTime() == clips[i].getStartTime()) {
                arrayList.add(clips[i]);
                ++minClipsCount;
                continue;
            }*/

            if (i + 1 != clips.length && clips[i + 1].getStartTime() > arrayList.get(arrayList.size() - 1).getEndTime()) {
                arrayList.add(clips[i]);
                ++minClipsCount;
            } else if (arrayList.get(arrayList.size() - 1).getEndTime() >= clips[i].getStartTime() && clips[i].getEndTime() >= time) {
                arrayList.add(clips[i]);
                ++minClipsCount;
            }

            if (arrayList.get(arrayList.size() - 1).getEndTime() >= time) {
                return minClipsCount;
            }

            if (i + 1 != clips.length && clips[i + 1].getStartTime() > arrayList.get(arrayList.size() - 1).getEndTime()) {
                return -1;
            }

        }


        return -1;
    }

    private static int getInterval(VideoClip clip, int width) {
        return clip.getEndTime() - clip.getStartTime() - width;
    }

    private static void bubbleSort(VideoClip[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j].getStartTime() == arr[j + 1].getStartTime()) {
                    if (arr[j].getEndTime() < arr[j + 1].getEndTime()) {
                        swap(arr, j, j + 1);
                    }
                }

                if (arr[j].getStartTime() > arr[j + 1].getStartTime()) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    private static void quickSort(VideoClip[] arr) {
        quickSortRecursive(arr, 0, arr.length - 1);
    }

    private static void quickSortRecursive(VideoClip[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(arr, left, right);
        quickSortRecursive(arr, pivot + 1, right);
        quickSortRecursive(arr, left, pivot - 1);
    }

    private static int partition(VideoClip[] arr, int left, int right) {
        int i = left;

        for (int j = left; j < right; j++) {
            if (arr[right].getStartTime() > arr[j].getStartTime()) {
                swap(arr, i, j);
                ++i;
            }
        }

        swap(arr, right, i);

        return i;
    }

    private static void swap(VideoClip[] arr, int i, int j) {
        VideoClip temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}