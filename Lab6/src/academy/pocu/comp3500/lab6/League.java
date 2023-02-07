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

        int max = 0;
        for (int i = 0; i < players.length; i++) {
            if (max < players[i].getRating()) {
                this.players.add(this.players.size(), players[i]);
                max = players[i].getRating();
            } else {
                int index = SortAndFind.find2(this.players, players[i]);
                this.players.add(index, players[i]);
            }


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

        // SortAndFind.quickSort(players);

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

        if (Math.abs(player.getRating() - p1.getRating()) >= Math.abs(p2.getRating() - player.getRating())) {
            return p2;
        } else {
            return p1;
        }

    }

    public Player[] getTop(final int count) {
        if (this.players.size() == 0) {
            return new Player[0];
        }

        int c = count;

        if (count > this.players.size()) {
            c = this.players.size();
        }

        Player[] p1 = new Player[c];

        for (int i = 0; i < c; i++) {
            p1[i] = this.players.get(players.size() - 1 - i);
        }

        return p1;
    }

    public Player[] getBottom(final int count) {
        if (this.players.size() == 0) {
            return new Player[0];
        }

        int c = count;

        if (count > this.players.size()) {
            c = this.players.size();
        }

        Player[] p1 = new Player[c];

        for (int i = 0; i < c; i++) {
            p1[i] = this.players.get(i);
        }

        return p1;
    }

    public boolean join(final Player player) {
        if (this.players.size() == 0) {
            this.players.add(player);
            return true;
        }

        // int index = SortAndFind.find(players, player); // logn

        // if (index != -1) {
        //     return false;
        // }

        int index = SortAndFind.find2(players, player); // logn

        if (index == -1) {
            return false;
        }

        if (this.players.size() == 1) {
            if (this.players.get(0).getRating() < player.getRating()) {
                this.players.add(player);
            } else {
                this.players.add(0, player);
            }

            return true;
        }
        


        this.players.add(index, player);

        return true;
    }

    public boolean leave(final Player player) {
        if (this.players.size() == 0) {
            return false;
        }

        int index = SortAndFind.find(players, player); //logn

        if (index == -1) {
            return false;
        }

        this.players.remove(index);

        return true;
    }
}