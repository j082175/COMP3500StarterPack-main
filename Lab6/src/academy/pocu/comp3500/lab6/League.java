package academy.pocu.comp3500.lab6;

import java.util.ArrayList;
import java.util.HashMap;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class League {
    private ArrayList<Player> players = new ArrayList<>();
    private HashMap<Integer, Integer> hashMap = new HashMap<>();

    public League() {
    }

    public League(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            this.players.add(players[i]);
        }

        // SortAndFind.quickSort(players);

        // for (int i = 0; i < players.length; i++) {
        // this.hashMap.put(players[i].getId(), i);
        // }
    }

    public Player findMatchOrNull(final Player player) {
        Player p1;
        Player p2;

        if (players.size() == 0) {
            return null;
        }

        if (players.size() == 1) {
            return null;
        }

        SortAndFind.quickSort(players);

        // int index = hashMap.get(player.getId());

        int index = SortAndFind.find(players, player);

        if (index == 0) {
            return players.get(index + 1);
        }

        if (index == players.size() - 1) {
            return players.get(index - 1);
        }

        p1 = players.get(index - 1);
        p2 = players.get(index + 1);

        if (player.getRating() - p1.getRating() >= p2.getRating() - player.getRating()) {
            return p2;
        } else {
            return p1;
        }

    }

    public Player[] getTop(final int count) {
        if (this.players.size() == 0) {
            return new Player[0];
        }

        SortAndFind.quickSort(players);

        Player[] p1 = new Player[count];

        for (int i = 0; i < count; i++) {
            p1[i] = this.players.get(players.size() - 1 - i);
        }

        return p1;
    }

    public Player[] getBottom(final int count) {
        if (this.players.size() == 0) {
            return new Player[0];
        }

        SortAndFind.quickSort(players);

        Player[] p1 = new Player[count];

        for (int i = 0; i < count; i++) {
            p1[i] = this.players.get(i);
        }

        return p1;
    }

    public boolean join(final Player player) {
        int index = SortAndFind.find(players, player);

        if (index != -1) {
            return false;
        }

        this.players.add(player);

        return true;
    }

    public boolean leave(final Player player) {
        int index = SortAndFind.find(players, player);

        if (index == -1) {
            return false;
        }

        this.players.remove(index);

        return true;
    }
}