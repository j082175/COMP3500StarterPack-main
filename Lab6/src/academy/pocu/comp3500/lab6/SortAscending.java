package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class SortAscending {
    public static void quickSort(Player[] players) {
        sortRecursive(players, 0, players.length - 1);
    }

    public static void sortRecursive(Player[] players, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivotPos = partition(players, left, right);

        sortRecursive(players, left, pivotPos - 1);
        sortRecursive(players, pivotPos + 1, right);
    }

    public static int partition(Player[] players, int left, int right) {
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

    public static void swap(Player[] players, int i, int j) {
        Player p1 = players[i];
        players[i] = players[j];
        players[j] = p1;
    }
}
