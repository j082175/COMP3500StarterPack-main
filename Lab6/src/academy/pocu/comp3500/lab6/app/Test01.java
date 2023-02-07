package academy.pocu.comp3500.lab6.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import org.junit.Test;

import academy.pocu.comp3500.lab6.League;
import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class Test01 {
    @Test
    public void test1() {
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
    }

    @Test
    public void joinTest() {
        final int playerCount = 100;
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
        ArrayList<Integer> expected = new ArrayList<>(uniqueRatings);
        Collections.sort(expected);

        Player[] actual = league.getBottom(playerCount); // higher rating -> top
        for (int i = 0; i < playerCount; ++i) {
            assert (expected.get(i) == actual[i].getRating());
        }

        ArrayList<Integer> expected2 = new ArrayList<>(uniqueRatings);
        Collections.sort(expected2, Collections.reverseOrder());
        actual = league.getTop(playerCount);

        for (int i = 0; i < playerCount; ++i) {
            assert (expected2.get(i) == actual[i].getRating());
        }
    }

    @Test
    public void leaveTest() { // delete from head
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
                assert (expected.get(j) == actual[j].getRating());
            }
        }
    }

    @Test
    public void findMatchTest() {
        final int playerCount = 100;
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

        for (int i = 0; i < playerCount; ++i) {
            int rating = players[i].getRating();

            int expectedRating = getMatch(sorted, rating);
            assert (expectedRating != -1);

            Player match = league.findMatchOrNull(players[i]);
            int actualRating = match.getRating();

            assert (expectedRating == actualRating);
        }
    }

    public int getMatch(ArrayList<Integer> sorted, int targetRating) {
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
}
