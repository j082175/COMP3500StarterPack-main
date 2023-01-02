package academy.pocu.comp3500.assignment1;

import academy.pocu.comp3500.assignment1.pba.Player;
import academy.pocu.comp3500.assignment1.pba.GameStat;

public final class PocuBasketballAssociation {
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
            outPlayers[playerCount].setAssistsPerGame(assists2/ strCount);
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
        return null;
    }

    public static Player findPlayerShootingPercentage(final Player[] players, int targetShootingPercentage) {
        return null;
    }

    public static long find3ManDreamTeam(final Player[] players, final Player[] outPlayers, final Player[] scratch) {
        return -1;
    }

    public static long findDreamTeam(final Player[] players, int k, final Player[] outPlayers, final Player[] scratch) {
        return -1;
    }

    public static int findDreamTeamSize(final Player[] players, final Player[] scratch) {
        return -1;
    }
}