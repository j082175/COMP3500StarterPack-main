package academy.pocu.comp3500.lab6.app;

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

        Player[] bottomPlayers = league.getBottom(3); // player5, player3, player1
    }
}
