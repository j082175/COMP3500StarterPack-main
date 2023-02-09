package academy.pocu.comp3500.lab6.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import academy.pocu.comp3500.lab6.League;
import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class Program {

    public static void main(String[] args) {

        // test2();

        // findMatchTest();

        Player player1 = new Player(1, "player1", 20);
        Player player2 = new Player(2, "player2", 10);
        Player player3 = new Player(3, "player3", 30);
        Player player4 = new Player(4, "player4", 5);
        Player player5 = new Player(5, "player5", 15);
        Player player6 = new Player(6, "player6", 25);
        Player player7 = new Player(7, "player7", 35);
        Player player8 = new Player(8, "player8", 4);
        Player player9 = new Player(9, "player9", 9);
        Player player10 = new Player(10, "player10", 12);
        Player player11 = new Player(11, "player11", 19);
        Player player12 = new Player(12, "player12", 21);
        Player player13 = new Player(13, "player13", 27);
        Player player14 = new Player(14, "player14", 33);
        Player player15 = new Player(15, "player15", 40);
        Player player16 = new Player(16, "player16", 38);
        Player player17 = new Player(17, "player17", 39);
        Player player18 = new Player(18, "player18", 14);
        Player player19 = new Player(19, "player19", 13);

        Player player20 = new Player(20, "player20", 100);
        Player player21 = new Player(21, "player21", 50);
        Player player22 = new Player(22, "player22", 75);
        Player player23 = new Player(23, "player23", 60);
        Player player24 = new Player(24, "player24", 70);
        Player player25 = new Player(25, "player25", 65);
        Player player26 = new Player(26, "player26", 67);
        Player player27 = new Player(27, "player27", 2);

        League league = new League(new Player[] { player1, player1, player2, player3, player4,
                player5, player6, player7, player8, player9, player10, player11, player12, player13, player14,
                player15, player16, player17, player18, player19, player20, player21, player22, player23, player24,
                player25, player26, player27 });

        League league2 = new League(new Player[] { player1, player2, player3 });
        league2.join(player4);
        league2.join(player1);

        Player p1 = league2.findMatchOrNull(player1);
        Player p2 = league2.findMatchOrNull(player2);

        Player player1Match = league.findMatchOrNull(player1); // player12
        Player player2Match = league.findMatchOrNull(player2); // player9
        Player player3Match = league.findMatchOrNull(player3); // player14
        Player player4Match = league.findMatchOrNull(player4); // player8
        Player player5Match = league.findMatchOrNull(player5); // player18
        Player player6Match = league.findMatchOrNull(player6); // player13
        Player player7Match = league.findMatchOrNull(player7); // player14
        Player player8Match = league.findMatchOrNull(player8); // player4
        Player player9Match = league.findMatchOrNull(player9); // player2
        Player player10Match = league.findMatchOrNull(player10); // player19
        Player player11Match = league.findMatchOrNull(player11); // player5
        Player player12Match = league.findMatchOrNull(player12); // player6
        Player player13Match = league.findMatchOrNull(player13); // player6
        Player player14Match = league.findMatchOrNull(player14); // player7
        Player player15Match = league.findMatchOrNull(player15); // player17
        Player player16Match = league.findMatchOrNull(player16); // player17
        Player player17Match = league.findMatchOrNull(player17); // player15
        Player player18Match = league.findMatchOrNull(player18); // player19
        Player player19Match = league.findMatchOrNull(player19); // player18

        Player player20Match = league.findMatchOrNull(player20); // player22
        Player player21Match = league.findMatchOrNull(player21); // player23
        Player player22Match = league.findMatchOrNull(player22); // player24
        Player player23Match = league.findMatchOrNull(player23); // player25
        Player player24Match = league.findMatchOrNull(player24); // player26
        Player player25Match = league.findMatchOrNull(player25); // player26
        Player player26Match = league.findMatchOrNull(player26); // player25
        Player player27Match = league.findMatchOrNull(player27); // player25

        league.leave(new Player(0, "33", 1212121));
        league.leave(player5);

        // Player player1 = new Player(1, "player1", 12);
        // Player player2 = new Player(2, "player2", 17);
        // Player player3 = new Player(3, "player3", 11);
        // Player player4 = new Player(4, "player4", 18);
        // Player player5 = new Player(5, "player5", 10);

        // League league = new League(new Player[] { player1, player2, player3, player4,
        // player5 });

        // Player newPlayer = new Player(6, "player6", 13);
        // Player newPlayer2 = new Player(7, "player7", 23);
        // Player newPlayer3 = new Player(8, "player8", 24);
        // Player newPlayer4 = new Player(9, "player9", 25);

        // boolean success = league.join(newPlayer); // true
        // success = league.join(newPlayer); // false
        // success = league.join(player2); // false

        // success = league.join(newPlayer2); // true
        // success = league.join(newPlayer3); // true
        // success = league.join(newPlayer4); // true

        // success = league.join(player1);
        // success = league.join(player2);
        // success = league.join(player3);
        // success = league.join(player4);
        // success = league.join(player5);
        // success = league.join(new Player(20, "a", 33));
        // success = league.join(new Player(20, "a", 33));

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

    public static void findMatchTest() {
        final int playerCount = 10;
        Player[] players = new Player[playerCount];

        HashSet<Integer> uniqueRatings = new HashSet<>();
        Random generator = new Random();

        while (uniqueRatings.size() < playerCount) {
            int low = 10;
            int high = 1000;
            uniqueRatings.add(generator.nextInt(high - low) + low);
        }

        ArrayList<Integer> randomN = new ArrayList<>(uniqueRatings);

        for (int i = 0; i < playerCount; ++i) {
            players[i] = new Player(i, "", randomN.get(i));
        }

        League league = new League(players);

        ArrayList<Integer> sorted = new ArrayList<>(randomN);
        Collections.sort(sorted);

        int[] expectedRating = new int[playerCount];
        int[] actualRating = new int[playerCount];

        for (int i = 0; i < playerCount; ++i) {
            int rating = players[i].getRating();

            expectedRating[i] = getMatch(sorted, rating);
            assert (expectedRating[i] != -1);

            Player match = league.findMatchOrNull(players[i]);
            actualRating[i] = match.getRating();

            assert (expectedRating[i] == actualRating[i]);
        }
    }

    public static int getMatch(ArrayList<Integer> sorted, int targetRating) {
        // targetRating이 항상 존재한다고 가정

        int left = 0, right = sorted.size() - 1;

        int targetIndex = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (sorted.get(mid) == targetRating) {
                targetIndex = mid;
                break;
            }

            if (targetRating > sorted.get(mid)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        assert (targetIndex != -1);

        if (targetIndex == 0) {
            return sorted.get(1);
        }

        if (targetIndex == sorted.size() - 1) {
            return sorted.get(sorted.size() - 2);
        }

        int distanceToLeft = Math.abs(sorted.get(targetIndex - 1) - targetRating);
        int distanceToRight = Math.abs(sorted.get(targetIndex + 1) - targetRating);

        return distanceToLeft < distanceToRight ? sorted.get(targetIndex - 1) : sorted.get(targetIndex + 1);
    }

    public static void test2() {
        Player player1 = new Player(1, "player1", 864);
        Player player2 = new Player(2, "player2", 496);
        Player player3 = new Player(3, "player3", 913);
        Player player4 = new Player(4, "player4", 818);
        Player player5 = new Player(4, "player5", 218);
        Player player6 = new Player(4, "player6", 93);
        Player player7 = new Player(4, "player7", 510);
        Player player8 = new Player(4, "player8", 542);
        Player player9 = new Player(4, "player9", 879);
        Player player10 = new Player(4, "player10", 719);

        League league = new League(new Player[] { player1, player1, player2, player3, player4,
                player5, player6, player7, player8, player9, player10 });

        Player player1Match = league.findMatchOrNull(player1); // player9
        Player player2Match = league.findMatchOrNull(player2); // player7
        Player player3Match = league.findMatchOrNull(player3); // player9
        Player player4Match = league.findMatchOrNull(player4); // player8
        Player player5Match = league.findMatchOrNull(player5); // player11
        Player player6Match = league.findMatchOrNull(player6); // player13
        Player player7Match = league.findMatchOrNull(player7); // player14
        Player player8Match = league.findMatchOrNull(player8); // player15
        Player player9Match = league.findMatchOrNull(player9); // player2
        Player player10Match = league.findMatchOrNull(player10); // player19
    }

    public static void test3() {
        Player player1 = new Player(1, "player1", 496);
        Player player2 = new Player(2, "player2", 146);
        Player player3 = new Player(3, "player3", 786);
        Player player4 = new Player(4, "player4", 518);
        Player player5 = new Player(4, "player5", 902);
        Player player6 = new Player(4, "player6", 631);
        Player player7 = new Player(4, "player7", 790);
        Player player8 = new Player(4, "player8", 922);
        Player player9 = new Player(4, "player9", 307);
        Player player10 = new Player(4, "player10", 40);

        League league = new League(new Player[] { player1, player1, player2, player3, player4,
                player5, player6, player7, player8, player9, player10 });

        Player player1Match = league.findMatchOrNull(player1); // player9
        Player player2Match = league.findMatchOrNull(player2); // player7
        Player player3Match = league.findMatchOrNull(player3); // player9
        Player player4Match = league.findMatchOrNull(player4); // player8
        Player player5Match = league.findMatchOrNull(player5); // player11
        Player player6Match = league.findMatchOrNull(player6); // player13
        Player player7Match = league.findMatchOrNull(player7); // player14
        Player player8Match = league.findMatchOrNull(player8); // player15
        Player player9Match = league.findMatchOrNull(player9); // player2
        Player player10Match = league.findMatchOrNull(player10); // player19
    }
}
