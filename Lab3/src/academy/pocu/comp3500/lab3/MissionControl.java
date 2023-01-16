package academy.pocu.comp3500.lab3;

import java.util.ArrayList;

public final class MissionControl {
    private MissionControl() {
    }

    private static int findArr(final int[] altitudes) {
        return findArrRecursive(altitudes, 0, altitudes.length - 1, 0, 0);
    }

    private static int findArrRecursive(final int[] altitudes, int start, int end, int maxValue, int index) {
        if (altitudes.length == 1) {
            return 0;
        }

        if (altitudes.length == 2) {
            return altitudes[0] > altitudes[1] ? 0 : 1;
        }

        int mid = (start + end) / 2;

        if (start > end) {
            return index;
        }

        if (maxValue < altitudes[mid]) {
            maxValue = altitudes[mid];
            index = mid;
        }

        // if (mid == altitudes.length - 1 || mid == 0) {
        //     return index;
        // }

        if (mid != altitudes.length - 1) {
            if (altitudes[mid] < altitudes[mid + 1]) {
                return findArrRecursive(altitudes, mid + 1, end, maxValue, index);
            }
        } else {
            return index;
        }


        if (mid != 0) {
            if (altitudes[mid] < altitudes[mid - 1]) {
                return findArrRecursive(altitudes, start, mid - 1, maxValue, index);
            }
        } else {
            return index;
        }


        return index;
    }

    private static ArrayList<Integer> findArr2(final int[] altitudes, final int targetAltitude) {
        int pivot = findArr(altitudes); // logn

        int left = findArrRecursiveAscending(altitudes, 0, pivot, targetAltitude); // logn
        int right = findArrRecursiveDescending(altitudes, pivot, altitudes.length - 1, targetAltitude); // logn

        // total = 3logn;

        ArrayList<Integer> result = new ArrayList<>();

        boolean checkLeft = false;
        boolean checkRight = false;

        if (left != -1) {
            checkLeft = true;
        }

        if (right != -1) {
            checkRight = true;
        }

        if (left == right && checkLeft & checkRight) {
            result.add(left);
        } else if (checkLeft & checkRight) {
            result.add(left);
            result.add(right);
        } else if (checkLeft) {
            result.add(left);
        } else if (checkRight) {
            result.add(right);
        }

        return result;
    }

    private static int findArrRecursiveAscending(final int[] altitudes, int start, int end, int targetAltitude) {
        int mid = (start + end) / 2;

        if (start > end) {
            return -1;
        }

        if (mid == altitudes.length) {
            return mid - 1;
        }

        if (targetAltitude == altitudes[mid]) {
            return mid;
        }

        if (targetAltitude > altitudes[mid]) {
            return findArrRecursiveAscending(altitudes, mid + 1, end, targetAltitude);
        }

        if (targetAltitude < altitudes[mid]) {
            return findArrRecursiveAscending(altitudes, start, mid - 1, targetAltitude);
        }

        return -1;
    }

    private static int findArrRecursiveDescending(final int[] altitudes, int start, int end, int targetAltitude) {
        int mid = (start + end) / 2;

        if (start > end) {
            return -1;
        }

        if (targetAltitude == altitudes[mid]) {
            return mid;
        }

        if (targetAltitude < altitudes[mid]) {
            return findArrRecursiveDescending(altitudes, mid + 1, end, targetAltitude);
        }

        if (targetAltitude > altitudes[mid]) {
            return findArrRecursiveDescending(altitudes, start, mid - 1, targetAltitude);
        }

        return -1;
    }



    public static int findMaxAltitudeTime(final int[] altitudes) {
        return findArr(altitudes);
    }

    public static ArrayList<Integer> findAltitudeTimes(final int[] altitudes, final int targetAltitude) {
        return findArr2(altitudes, targetAltitude);
    }
}