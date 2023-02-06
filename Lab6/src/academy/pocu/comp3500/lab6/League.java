package academy.pocu.comp3500.lab6;

import java.util.HashMap;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class League {
    private Player[] players;
    private HashMap<Integer, Integer> hashMap = new HashMap<>();

    public League(Player[] players) {
        this.players = players;

        for (int i = 0; i < players.length; i++) {
            this.hashMap.put(players[i].getId(), players[i].getRating());
        }
    }

    public Player findMatchOrNull(final Player player) {


        return null;
    }

    public Player[] getTop(final int count) {
        return null;
    }

    public Player[] getBottom(final int count) {
        return null;
    }

    public boolean join(final Player player) {
        return false;
    }

    public boolean leave(final Player player) {
        return false;
    }
}