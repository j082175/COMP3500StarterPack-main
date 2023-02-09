package academy.pocu.comp3500.lab6;

import java.util.ArrayList;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class SortAndFind {

    public static boolean searchJoin(ArrayList<Player> players, Player player) {

        return searchJoinRecursive(players, 0, players.size() - 1, player, new ArrayList<>(), false);
    }

    private static boolean searchJoinRecursive(ArrayList<Player> players, int start, int end, Player player, ArrayList<Player> newArrayList, boolean isCheck) {

        int s = (start + end) / 2; // 중간 값 (middle)

        if (players.get(s).getId() == player.getId()) {
            return false;
        }

        if (players.get(s).getRating() > player.getRating() && !isCheck) {
            // newArrayList.add(player);
            // isCheck = true;

            players.add(s, player);
        }

        // newArrayList.add(players.get(s));

        if (start >= end) {
            // if (!isCheck) {
            // newArrayList.add(player);
            // }

            players = newArrayList;

            return true;
        } else if (player.getRating() < players.get(s).getRating()) {
            return searchJoinRecursive(players, start, s - 1, player, newArrayList, isCheck);
        } else if (player.getRating() > players.get(s).getRating()) {
            return searchJoinRecursive(players, s + 1, end, player, newArrayList, isCheck);
        }

        return true;
    }

    public static int find2(ArrayList<Player> players, Player player) {

        return findRecursive2(players, 0, players.size() - 1, player, 0);
    }

    private static int findRecursive2(ArrayList<Player> players, int start, int end, Player player, int in) {

        int s = (start + end) / 2; // 중간 값 (middle)

        if (players.get(s).getId() == player.getId()) {
            return -1;
        }

        if (s != 0) {
            if (players.get(s).getRating() > player.getRating()
                    && players.get(s - 1).getRating() < player.getRating()) {
                in = s;
            }
        }

        if (start >= end) {
            if (players.get(s).getRating() < player.getRating()) {
                in = s + 1;
            }
            return in; // 충족시키지 못해 여기로 왔으면 못찾음(-1) 리턴
        } // 마지막 하나로 압축됐는데 위 1번 탈출 조건을

        else if (player.getRating() < players.get(s).getRating()) {
            return findRecursive2(players, start, s - 1, player, in);
        } else if (player.getRating() > players.get(s).getRating()) {
            return findRecursive2(players, s + 1, end, player, in);
        }

        return s;
    }

    public static int find(ArrayList<Player> players, Player player) {
        return findRecursive(players, 0, players.size() - 1, player);
    }

    private static int findRecursive(ArrayList<Player> players, int start, int end, Player player) {
        int s = (start + end) / 2; // 중간 값 (middle)

        if (players.get(s).getId() == player.getId()) {
            return s;
        }

        if (start >= end) // 마지막 하나로 압축됐는데 위 1번 탈출 조건을
            return -1; // 충족시키지 못해 여기로 왔으면 못찾음(-1) 리턴

        else if (player.getRating() < players.get(s).getRating()) {
            return findRecursive(players, start, s - 1, player);
        } else if (player.getRating() > players.get(s).getRating()) {
            return findRecursive(players, s + 1, end, player);
        }

        return -1;
    }

    public static void quickSort(ArrayList<Player> players) {
        sortRecursive(players, 0, players.size() - 1);
    }

    private static void sortRecursive(ArrayList<Player> players, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivotPos = partition(players, left, right);

        sortRecursive(players, left, pivotPos - 1);
        sortRecursive(players, pivotPos + 1, right);
    }

    private static int partition(ArrayList<Player> players, int left, int right) {

        int pivot = players.get(right).getRating();

        int i = left;
        for (int j = left; j < right; ++j) {
            if (players.get(j).getRating() < pivot) {
                swap(players, i, j);
                ++i;
            }
        }

        int pivotPos = i;
        swap(players, pivotPos, right);

        return pivotPos;
    }

    public static void swap(ArrayList<Player> players, int i, int j) {
        Player p1 = players.get(i);
        players.set(i, players.get(j));
        players.set(j, p1);
    }
}
