package academy.pocu.comp3500.lab6.app;

import academy.pocu.comp3500.lab6.League;
import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class Program {

    public static void main(String[] args) {
        Player player1 = new Player(1, "player1", 9);
        Player player2 = new Player(2, "player2", 12);
        Player player3 = new Player(3, "player3", 17);
        Player player4 = new Player(4, "player4", 14);
        
        League league = new League(new Player[]{player1, player2, player3, player4});
        
        Player player3Match = league.findMatchOrNull(player2); // player4
        Player player4Match = league.findMatchOrNull(player4); // player2
    }
}
