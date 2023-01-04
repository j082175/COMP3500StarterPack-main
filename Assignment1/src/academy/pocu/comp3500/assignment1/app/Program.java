package academy.pocu.comp3500.assignment1.app;

import academy.pocu.comp3500.assignment1.PocuBasketballAssociation;
import academy.pocu.comp3500.assignment1.pba.GameStat;
import academy.pocu.comp3500.assignment1.pba.Player;

public class Program {

    public static void main(String[] args) {
        // Player[] players = new Player[] {
        // new Player("Player 1", 1, 5, 1, 60),
        // new Player("Player 2", 5, 2, 11, 31),
        // new Player("Player 3", 7, 4, 7, 44),
        // new Player("Player 4", 10, 10, 15, 25),
        // new Player("Player 5", 11, 12, 6, 77),
        // new Player("Player 6", 15, 0, 12, 61),
        // new Player("Player 7", 16, 8, 2, 70)
        // };

        // Player player = PocuBasketballAssociation.findPlayerPointsPerGame(players,
        // 12); // player: Player 5

        // player = PocuBasketballAssociation.findPlayerPointsPerGame(players, 5); //
        // player: Player 2
        // player = PocuBasketballAssociation.findPlayerPointsPerGame(players, 13); //
        // player: Player 6

        Player[] players = new Player[] {
            new Player("Player 2", 5, 12, 14, 50),
            new Player("Player 6", 15, 2, 5, 40),
            new Player("Player 5", 11, 1, 11, 54),
            new Player("Player 4", 10, 3, 51, 88),
            new Player("Player 7", 16, 8, 5, 77),
            new Player("Player 1", 1, 15, 2, 22),
            new Player("Player 3", 7, 5, 8, 66)
    };
    
    Player[] outPlayers = new Player[3];
    Player[] scratch = new Player[3];
    
    long maxTeamwork = PocuBasketballAssociation.find3ManDreamTeam(players, outPlayers, scratch); // maxTeamwork: 219, outPlayers: [ Player 4, Player 2, Player 3 ]

        int a = 1;
    }
}