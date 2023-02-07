package academy.pocu.comp3500.lab6;

import java.util.HashMap;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class League {
    private Player[] players;
    private HashMap<Integer, Integer> hashMap = new HashMap<>();

    public League(Player[] players) {
        this.players = players;

        SortAscending.quickSort(players);

        for (int i = 0; i < players.length; i++) {
            this.hashMap.put(players[i].getId(), i);
        }
    }

    public Player findMatchOrNull(final Player player) {
        Player p1;
        Player p2;

        if (hashMap.containsKey(player.getId())) {
            int index = hashMap.get(player.getId());

            if (index == 0) {
                return players[index + 1];
            }

            if (index == players.length - 1) {
                return players[index - 1];
            }

            p1 = players[index - 1];
            p2 = players[index + 1];

            if (player.getRating() - p1.getRating() > p2.getRating() - player.getRating()) {
                return p2;
            } else {
                return p1;
            }
        }

        return null;
    }

    public Player[] getTop(final int count) {
        return players;
    }

    public Player[] getBottom(final int count) {
        return players;
    }

    public boolean join(final Player player) {
        return true;
    }

    public boolean leave(final Player player) {
        return true;
    }
}