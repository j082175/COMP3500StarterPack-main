package academy.pocu.comp3500.lab6.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import academy.pocu.comp3500.lab6.League;
import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class Program {

    public static void main(String[] args) {

        Player player1 = new Player(1, "player1", 12);
        Player player2 = new Player(2, "player2", 17);
        Player player3 = new Player(3, "player3", 11);
        Player player4 = new Player(4, "player4", 18);
        Player player5 = new Player(5, "player5", 10);

        League league = new League(new Player[] { player1, player2, player3, player4, player5 });

        Player newPlayer = new Player(6, "player6", 13);
        Player newPlayer2 = new Player(7, "player7", 23);
        Player newPlayer3 = new Player(8, "player8", 24);
        Player newPlayer4 = new Player(9, "player9", 25);

        boolean success = league.join(newPlayer); // true
        success = league.join(newPlayer); // false
        success = league.join(player2); // false

        success = league.join(newPlayer2); // true
        success = league.join(newPlayer3); // true
        success = league.join(newPlayer4); // true

        success = league.join(player1);
        success = league.join(player2);
        success = league.join(player3);
        success = league.join(player4);
        success = league.join(player5);
        success = league.join(new Player(20, "a", 33));
        success = league.join(new Player(20, "a", 33));


        test1();
    }

    public static void test1() {
        // Constructors
        League emptyLeague = new League();

        Player[] emptyLeaguePlayers = emptyLeague.getTop(10);

        assert (emptyLeaguePlayers.length == 0);

        Player player1 = new Player(1, "player1", 4);
        Player player2 = new Player(2, "player2", 6);
        Player player3 = new Player(3, "player3", 7);
        Player player4 = new Player(4, "player4", 9);
        Player player5 = new Player(5, "player5", 11);
        Player player6 = new Player(6, "player6", 12);

        League league = new League(new Player[] { player6, player4, player1, player2, player5, player3 });

        // findMatchOrNull()
        Player match = league.findMatchOrNull(player3);
        assert (match.getId() == player2.getId());

        match = league.findMatchOrNull(player4);
        assert (match.getId() == player5.getId());

        match = league.findMatchOrNull(player6);
        assert (match.getId() == player5.getId());

        // getTop(), getBottom()
        Player[] topPlayers = league.getTop(3);

        assert (topPlayers[0].getId() == player6.getId());
        assert (topPlayers[1].getId() == player5.getId());
        assert (topPlayers[2].getId() == player4.getId());

        Player[] bottomPlayers = league.getBottom(3);

        assert (bottomPlayers[0].getId() == player1.getId());
        assert (bottomPlayers[1].getId() == player2.getId());
        assert (bottomPlayers[2].getId() == player3.getId());

        // join()
        boolean joinSuccess = league.join(new Player(7, "player7", 10));
        assert (joinSuccess);

        joinSuccess = league.join(new Player(1, "player1", 4));
        assert (!joinSuccess);

        // leave()
        boolean leaveSuccess = league.leave(new Player(5, "player5", 11));
        assert (leaveSuccess);

        leaveSuccess = league.leave(new Player(5, "player5", 11));
        assert (!leaveSuccess);

        leaveTest();
    }

    public static void leaveTest() { // delete from head
        final int playerCount = 50;
        Player[] players = new Player[playerCount];

        HashSet<Integer> uniqueRatings = new HashSet<>();
        Random generator = new Random();

        while (uniqueRatings.size() < playerCount) {
            int low = 10;
            int high = 1000;
            uniqueRatings.add(generator.nextInt(high - low) + low);
        }

        ArrayList<Integer> randomN = new ArrayList<>(uniqueRatings);
        ArrayList<Integer> p = new ArrayList<>(1024);

        for (int i = 0; i < playerCount; ++i) {
            players[i] = new Player(i, "", randomN.get(i));
            p.add(players[i].getRating());
        }

        League league = new League(players);
        for (int i = 0; i < playerCount; ++i) {
            league.leave(players[i]);
            p.remove(0);

            // test equality
            ArrayList<Integer> expected = new ArrayList<>(p);
            Collections.sort(expected);

            Player[] actual = league.getBottom(p.size()); // higher rating -> top
            for (int j = 0; j < p.size(); ++j) {
                int a = expected.get(j);
                int b = actual[j].getRating();
                assert (expected.get(j) == actual[j].getRating());
            }
        }
    }
}
