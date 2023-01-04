package academy.pocu.comp3500.assignment1;

import academy.pocu.comp3500.assignment1.pba.Player;
import academy.pocu.comp3500.assignment1.pba.GameStat;

public final class PocuBasketballAssociation {
    // private static int teamwork = 0;
    // private static int playerCount = 0;

    private static Player findPlayerPointsPerGameRecursive(final Player[] players, int targetPoints, int start, int end, int min, int index) {
        int s = (start + end) / 2; // 중간 값 (middle)

        if (s >= players.length) {
            return players[s - 1];
        }

        if (players[s].getPointsPerGame() == targetPoints) {
            return players[s];
        }

        if (min >= Math.abs(players[s].getPointsPerGame() - targetPoints)) {
            if (players[s].getPointsPerGame() - targetPoints > 0) {
                min = Math.abs(players[s].getPointsPerGame() - targetPoints);
                index = s;
            }
        } // 같으면 해당 인덱스 리턴

        if (start >= end) // 마지막 하나로 압축됐는데 위 1번 탈출 조건을
            return players[index]; // 충족시키지 못해 여기로 왔으면 못찾음(-1) 리턴

        else if (targetPoints < players[s].getPointsPerGame()) {
            return findPlayerPointsPerGameRecursive(players, targetPoints, 0, s - 1, min, index);
        } else if (targetPoints > players[s].getPointsPerGame()) {
            return findPlayerPointsPerGameRecursive(players, targetPoints, s + 1, end, min, index);
        }

        return null;

    }

    private static void combination_DFS(Player[] players, Player[] scratch, int index, int count, boolean[] visit, Player[] outPlayers, int length, int[] teamwork, int[] playerCount) { // count개의 수를 이용해 조합을 만듬
        int min = players[0].getAssistsPerGame();
        int sum = 0;

        if (count == 3) {
            for (int i = 1; i <= length; i++) {
                if (visit[i] == true) {
                    if (scratch[i] == null) {
                        break;
                    }

                    // System.out.print(scratch[i].getName());
                    // System.out.print(" ");
                    if (min > players[i].getAssistsPerGame()) {
                        min = players[i].getAssistsPerGame();
                    }
                    sum += players[i].getPassesPerGame();
                } // 조합과 순열의 차이 (조합은 중복 제거)
            }

            if (teamwork != null) {
                if (teamwork[0] < sum * min) {
                    teamwork[0] = (sum * min);

                    if (playerCount != null) {
                        playerCount[0] = length;
                    }

                    if (outPlayers != null) {
                        for (int i = 0; i < length; i++) {
                            outPlayers[i] = players[i];
                        }
                    }
                }
            }

            return;
        }

        for (int i = index; i <= length; i++) {
            // 이미 방문한 곳이라면 건너뛴다.
            if (visit[i] == true)
                continue;

            visit[i] = true; // 방문 표시를 남긴다.
            combination_DFS(players, scratch, i, count + 1, visit, outPlayers, length, teamwork, playerCount);
            visit[i] = false; // 체크 취소 (다른 자식 노드를 체크하기 위해)
        }
    }

    private static void combination2(Player[] players, Player[] scratch, int index, int depth, Player[] outPlayers, int length, int[] teamwork, int[] playerCount) {
        if (depth == scratch.length) {
            int min = scratch[0].getAssistsPerGame();
            int sum = 0;
            for (int i = 0; i < scratch.length; i++) {
                if (scratch[i] == null) {
                    break;
                }

                // System.out.print(scratch[i].getName());
                // System.out.print(" ");
                if (min > scratch[i].getAssistsPerGame()) {
                    min = scratch[i].getAssistsPerGame();
                }
                sum += scratch[i].getPassesPerGame();
            }

            if (teamwork != null) {
                if (teamwork[0] < sum * min) {
                    teamwork[0] = (sum * min);

                    if (playerCount != null) {
                        playerCount[0] = length;
                    }

                    if (outPlayers != null) {
                        for (int i = 0; i < length; i++) {
                            outPlayers[i] = scratch[i];
                        }
                    }
                }
            }

            return;
        } else {
            for (int i = index; i < players.length; i++) {
                scratch[depth] = players[i];
                combination2(players, scratch, i + 1, depth + 1, outPlayers, length, teamwork, playerCount);
            }
        }
    }

    private static void combination(Player[] players, Player[] scratch, int r, int index, int depth, Player[] outPlayers, int length, int[] teamwork, int[] playerCount) {

        if (r == 0) {
            int min = scratch[0].getAssistsPerGame();
            int sum = 0;
            for (int i = 0; i < scratch.length; i++) {

                if (scratch[i] == null) {
                    break;
                }

                // System.out.print(scratch[i].getName());
                // System.out.print(" ");
                if (min > scratch[i].getAssistsPerGame()) {
                    min = scratch[i].getAssistsPerGame();
                }
                sum += scratch[i].getPassesPerGame();
            }

            if (teamwork != null) {
                if (teamwork[0] < sum * min) {
                    teamwork[0] = (sum * min);

                    if (playerCount != null) {
                        playerCount[0] = length;
                    }

                    if (outPlayers != null) {
                        for (int i = 0; i < length; i++) {
                            outPlayers[i] = scratch[i];
                        }
                    }
                }
            }

            // System.out.println();
        } else if (depth == players.length) // depth == n // 계속 안뽑다가 r 개를 채우지 못한 경우는 이 곳에 걸려야 한다.
        {
            return;
        } else {
            // arr[depth] 를 뽑은 경우
            scratch[index] = players[depth];
            combination(players, scratch, r - 1, index + 1, depth + 1, outPlayers, length, teamwork, playerCount);

            // arr[depth] 를 뽑지 않은 경우
            combination(players, scratch, r, index, depth + 1, outPlayers, length, teamwork, playerCount);
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

        p1 = findPlayerPointsPerGameRecursive(players, targetPoints, 0, players.length,
                Math.abs(players[players.length - 1].getPointsPerGame() - targetPoints), players.length - 1);

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

        int[] teamwork = {};

        // combination(players, scratch, 3, 0, 0, outPlayers, 3, teamwork, null);
        // combination2(players, scratch, 0, 0, outPlayers, 3, teamwork, null);

        boolean[] visit = {};
        combination_DFS(players, scratch, 1, 0, visit, outPlayers, 3, teamwork, null);

        return teamwork[0];
    }

    public static long findDreamTeam(final Player[] players, int k, final Player[] outPlayers, final Player[] scratch) {

        int[] teamwork = {};

        // combination(players, scratch, k, 0, 0, outPlayers, k, teamwork, null);
        combination2(players, scratch, 0, 0, outPlayers, k, teamwork, null);

        return teamwork[0];
    }

    public static int findDreamTeamSize(final Player[] players, final Player[] scratch) {

        int[] teamwork = {};
        int[] playerCount = {};

        for (int i = 1; i < players.length; i++) {
            // combination(players, scratch, i, 0, 0, null, i, teamwork, playerCount);
            combination2(players, scratch, 0, 0, null, i, teamwork, playerCount);
        }

        return playerCount[0];
    }

    private static double baseLog(int x, int base) {
        return Math.log10(x) / Math.log10(base);
    }
}