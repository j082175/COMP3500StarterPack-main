package academy.pocu.comp3500.assignment1.pba;

public final class GameStat {
    private String playerName;
    private int game;
    private int points;
    private int goals;
    private int goalAttempts;
    private int assists;
    private int numPasses;

    public GameStat(final String playerName, // 선수 이름
                    final int game, // 경기 번호
                    final int points, // 득점
                    final int goals, // 슛 성공 수
                    final int goalAttempts, // 슛 시도 수
                    final int assists, // 어시스트 수
                    final int numPasses) { // 패스 수
        this.playerName = playerName;
        this.game = game;
        this.points = points;
        this.goals = goals;
        this.goalAttempts = goalAttempts;
        this.assists = assists;
        this.numPasses = numPasses;
    }

    public int getPoints() {
        return this.points;
    }

    public int getAssists() {
        return this.assists;
    }

    public int getNumPasses() {
        return this.numPasses;
    }

    public int getGoalAttempts() {
        return this.goalAttempts;
    }

    public int getGoals() {
        return this.goals;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public int getGame() {
        return this.game;
    }
}
