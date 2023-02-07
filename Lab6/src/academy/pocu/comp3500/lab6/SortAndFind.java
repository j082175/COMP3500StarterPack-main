package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class SortAndFind {

    public static int find(Player[] players, Player player) {
        return findRecursive(players, 0, players.length - 1, player);
    }

    private static int findRecursive(Player[] players, int start, int end, Player player) {
        int s = (start + end) / 2; // 중간 값 (middle)

        if (players[s] == player) {
            return s;
        }

        if (start >= end) // 마지막 하나로 압축됐는데 위 1번 탈출 조건을
            return -1; // 충족시키지 못해 여기로 왔으면 못찾음(-1) 리턴

        else if (player.getRating() < players[s].getRating()) {
            return findRecursive(players, start, s - 1, player);
        } else if (player.getRating() > players[s].getRating()) {
            return findRecursive(players, s + 1, end, player);
        }

        return -1;
    }

    public static void quickSort(Player[] players) {
        sortRecursive(players, 0, players.length - 1);
    }

    private static void sortRecursive(Player[] players, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivotPos = partition(players, left, right);

        sortRecursive(players, left, pivotPos - 1);
        sortRecursive(players, pivotPos + 1, right);
    }

    private static int partition(Player[] players, int left, int right) {
        int pivot = players[right].getRating();

        int i = left;
        for (int j = left; j < right; ++j) {
            if (players[j].getRating() < pivot) {
                swap(players, i, j);
                ++i;
            }
        }

        int pivotPos = i;
        swap(players, pivotPos, right);

        return pivotPos;
    }

    private static void swap(Player[] players, int i, int j) {
        Player p1 = players[i];
        players[i] = players[j];
        players[j] = p1;
    }
}
