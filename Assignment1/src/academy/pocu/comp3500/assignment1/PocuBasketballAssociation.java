package academy.pocu.comp3500.assignment1;

import academy.pocu.comp3500.assignment1.pba.Player;
import academy.pocu.comp3500.assignment1.pba.GameStat;

public final class PocuBasketballAssociation {
    private static int teamwork = 0;
    private static int playerCount = 0;

    private static void Combination(Player[] players, Player[] scratch, int r, int index, int depth,
            Player[] outPlayers, int length) {

        if (r == 0) {
            int min = scratch[0].getAssistsPerGame();
            int sum = 0;
            for (int i = 0; i < scratch.length; i++) {

                if (scratch[i] == null) {
                    break;
                }

                System.out.print(scratch[i].getName());
                System.out.print(" ");
                if (min > scratch[i].getAssistsPerGame()) {
                    min = scratch[i].getAssistsPerGame();
                }
                sum += scratch[i].getPassesPerGame();
            }

            if (teamwork < sum * min) {
                teamwork = sum * min;
                playerCount = length;

                if (outPlayers != null) {
                    for (int i = 0; i < length; i++) {
                        outPlayers[i] = scratch[i];
                    }
                }

            }

            System.out.println();
        } else if (depth == players.length) // depth == n // 계속 안뽑다가 r 개를 채우지 못한 경우는 이 곳에 걸려야 한다.
        {
            return;
        } else {
            // arr[depth] 를 뽑은 경우
            scratch[index] = players[depth];
            Combination(players, scratch, r - 1, index + 1, depth + 1, outPlayers, length);

            // arr[depth] 를 뽑지 않은 경우
            Combination(players, scratch, r, index, depth + 1, outPlayers, length);
        }
    }

    private PocuBasketballAssociation() {
    }

    public static void processGameStats(final GameStat[] gameStats, final Player[] outPlayers) {
        // we need these informations -> playerName, points, assists, numPasses, goals
        if (gameStats.length == 0) {
            return;
        }

        int points2 = 0;
        int assists2 = 0;
        int numPasses2 = 0;
        int goals2 = 0;
        int goalAttempts2 = 0;

        int goalsRate = 0;
        int count = 0;
        String str = null;
        int strCount = 0;
        int playerCount = 0;
        while (count < gameStats.length) {

            if (gameStats[count] != null) {
                str = gameStats[count].getPlayerName();
            } else {
                ++count;
                continue;
            }

            for (int i = 0; i < gameStats.length; i++) {
                if (gameStats[i] != null) {
                    if (gameStats[i].getPlayerName().equals(str)) {
                        ++strCount;

                        points2 += gameStats[i].getPoints();
                        assists2 += gameStats[i].getAssists();
                        numPasses2 += gameStats[i].getNumPasses();
                        goals2 += gameStats[i].getGoals();
                        goalAttempts2 += gameStats[i].getGoalAttempts();

                        gameStats[i] = null;
                    }
                }
            }

            outPlayers[playerCount].setName(str);
            outPlayers[playerCount].setPointsPerGame(points2 / strCount);
            outPlayers[playerCount].setAssistsPerGame(assists2 / strCount);
            outPlayers[playerCount].setPassesPerGame(numPasses2 / strCount);
            goalsRate = 100 * goals2 / goalAttempts2;
            outPlayers[playerCount].setShootingPercentage(goalsRate);

            strCount = 0;
            points2 = 0;
            assists2 = 0;
            numPasses2 = 0;
            goals2 = 0;
            goalAttempts2 = 0;
            str = null;
            ++count;
            ++playerCount;
        }

    }

    public static Player findPlayerPointsPerGame(final Player[] players, int targetPoints) {
        // players[0].getPointsPerGame();
        Player p1 = null;

        // int min = players[0].getPointsPerGame() - targetPoints;
        // int minAbs = Math.abs(players[0].getPointsPerGame() - targetPoints);
        // p1 = players[0];
        // for (int i = 1; i < players.length; i++) {
        // if (minAbs > Math.abs(players[i].getPointsPerGame() - targetPoints)) {
        // minAbs = Math.abs(players[i].getPointsPerGame() - targetPoints);
        // p1 = players[i];
        // }

        // if (minAbs == Math.abs(players[i].getPointsPerGame() - targetPoints)) {
        // p1 = players[i];
        // }
        // }

        int half = players.length / 2;
        int minAbs = Math.abs(players[half].getPointsPerGame() - targetPoints);
        p1 = players[half];
        int count = players.length;
        while (count != 0) {
            if (players[half].getPointsPerGame() < targetPoints) {
                half = players.length - half;
            }

            if (minAbs > Math.abs(players[half].getPointsPerGame() - targetPoints)) {
                minAbs = Math.abs(players[half].getPointsPerGame() - targetPoints);
                p1 = players[half];
            }

            if (minAbs == Math.abs(players[half].getPointsPerGame() - targetPoints)) {
                p1 = players[half];
            }

            count /= 2;
        }

        return p1;
    }

    public static Player findPlayerShootingPercentage(final Player[] players, int targetShootingPercentage) {
        // players[0].getShootingPercentage();
        return null;
    }

    public static long find3ManDreamTeam(final Player[] players, final Player[] outPlayers, final Player[] scratch) {
        // players 의 길이는 항상 3보다 같거나 크다고 가정
        // outPlayers 와 scratch 의 크기는 항상 3이라고 가정
        // 총 35가지

        Combination(players, scratch, 3, 0, 0, outPlayers, 3);

        int result = teamwork;
        teamwork = 0;

        return result;
    }

    public static long findDreamTeam(final Player[] players, int k, final Player[] outPlayers, final Player[] scratch) {
        Combination(players, scratch, k, 0, 0, outPlayers, k);

        int result = teamwork;
        teamwork = 0;

        return result;
    }

    public static int findDreamTeamSize(final Player[] players, final Player[] scratch) {

        for (int i = 1; i < players.length; i++) {
            Combination(players, scratch, i, 0, 0, null, i);
        }

        int result = playerCount;
        playerCount = 0;
        teamwork = 0;

        return result;
    }
}