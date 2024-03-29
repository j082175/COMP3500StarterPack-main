package academy.pocu.comp3500.assignment1.app;

import java.util.Scanner;

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

                // Player[] players = new Player[] {
                // new Player("Player 2", 5, 5, 17, 50),
                // new Player("Player 6", 15, 4, 10, 40),
                // new Player("Player 5", 11, 3, 25, 54),
                // new Player("Player 4", 10, 9, 1, 88),
                // new Player("Player 7", 16, 7, 5, 77),
                // new Player("Player 1", 1, 2, 8, 22),
                // new Player("Player 9", 42, 15, 4, 56),
                // new Player("Player 8", 33, 11, 3, 72),
                // };

                // int k = 4;
                // Player[] outPlayers = new Player[4];
                // Player[] scratch = new Player[k];

                // long maxTeamwork = PocuBasketballAssociation.findDreamTeam(players, k,
                // outPlayers, scratch); // maxTeamwork: 171, outPlayers: [ Player 6, Player 5,
                // Player 2, Player 7 ]

                // Player[] players = new Player[] {
                // new Player("Player 2", 5, 12, 14, 50),
                // new Player("Player 6", 15, 2, 5, 40),
                // new Player("Player 5", 11, 1, 11, 54),
                // new Player("Player 4", 10, 3, 51, 88),
                // new Player("Player 7", 16, 8, 5, 77),
                // new Player("Player 1", 1, 15, 2, 22),
                // new Player("Player 3", 7, 5, 8, 66)
                // };

                // Player[] outPlayers = new Player[3];
                // Player[] scratch = new Player[3];

                // long maxTeamwork = PocuBasketballAssociation.find3ManDreamTeam(players,
                // outPlayers, scratch); // maxTeamwork:
                // // 219,
                // // outPlayers: [
                // // Player 4,
                // // Player 2,
                // // Player 3 ]

                Player[] players1 = new Player[] {
                                new Player("Player 9", -1, 5, 88, -1), //
                                new Player("Player 6", -1, 6, 200, -1), //
                                new Player("Player 3", -1, 10, 67, -1),
                                new Player("Player 7", -1, 11, 23, -1),
                                new Player("Player 12", -1, 12, 39, -1),
                                new Player("Player 10", -1, 13, 11, -1),
                                new Player("Player 1", -1, 14, 44, -1),
                                new Player("Player 2", -1, 15, 5, -1),
                                new Player("Player 5", -1, 17, 51, -1),
                                new Player("Player 8", -1, 19, 56, -1), //
                                new Player("Player 11", -1, 20, 25, -1),
                                new Player("Player 4", -1, 22, 2, -1),

                };

                int k = 12;
                Player[] outPlayers1 = new Player[k];
                Player[] scratch1 = new Player[k];

                // long maxTeamwork1 = PocuBasketballAssociation.findDreamTeam(players1, k,
                // outPlayers1, scratch1);
                // long maxTeamwork2 = PocuBasketballAssociation.find3ManDreamTeam(players1,
                // outPlayers1, scratch1);
                int size1 = PocuBasketballAssociation.findDreamTeamSize(players1, scratch1);
                // assert maxTeamwork1 == 400;

                Player[] players = new Player[] {
                                new Player("Player 1", -1, 1, 9, -1),
                                new Player("Player 2", -1, 2, 8, -1),
                                new Player("Player 3", -1, 3, 7, -1),
                                new Player("Player 4", -1, 4, 6, -1),
                                new Player("Player 5", -1, 5, 5, -1),
                                new Player("Player 6", -1, 6, 4, -1),
                                new Player("Player 7", -1, 7, 3, -1),
                                new Player("Player 8", -1, 8, 2, -1),
                                new Player("Player 9", -1, 9, 1, -1)
                };

                int size = 9;
                Player[] outPlayers = new Player[size];
                Player[] scratch = new Player[size];

                long maxTeamwork = PocuBasketballAssociation.findDreamTeam(players, size, outPlayers, scratch); // maxTeamwork:
                                                                                                                // 219,
                                                                                                                // outPlayers:
                                                                                                                // [
                                                                                                                // Player
                                                                                                                // 4,
                                                                                                                // Player
                                                                                                                // 2,
                                                                                                                // Player
                                                                                                                // 3 ]
                int aa = PocuBasketballAssociation.findDreamTeamSize(players, scratch);

                Player[] players3 = new Player[] {
                                new Player("Player 1", 2, 5, 10, 78),
                                new Player("Player 2", 10, 4, 5, 66),
                                new Player("Player 3", 3, 3, 2, 22),
                                new Player("Player 4", 1, 9, 8, 12),
                                new Player("Player 5", 11, 1, 12, 26),
                                new Player("Player 6", 7, 2, 10, 15),
                                new Player("Player 7", 8, 15, 3, 11),
                                new Player("Player 8", 5, 7, 13, 5),
                                new Player("Player 9", 8, 2, 7, 67),
                                new Player("Player 10", 1, 11, 1, 29),
                                new Player("Player 11", 2, 6, 9, 88)
                };

                Player[] scratch3 = new Player[11];
                Player[] outPlayers3 = new Player[11];
                // long t = PocuBasketballAssociation.findDreamTeam(players3, 6, outPlayers3,
                // scratch3);
                int k3 = PocuBasketballAssociation.findDreamTeamSize(players3, scratch3); // k: 6





                Player[] players4 = new Player[] {
                                new Player("Player 1", 2, 5, 10, 78),
                                new Player("Player 2", 10, 4, 5, 66),
                                new Player("Player 3", 3, 3, 2, 22),
                                new Player("Player 4", 1, 9, 8, 12),
                                new Player("Player 5", 11, 1, 12, 26),
                                new Player("Player 6", 7, 2, 10, 15),
                                new Player("Player 7", 8, 15, 3, 11),
                                new Player("Player 8", 5, 7, 13, 5),
                                new Player("Player 9", 8, 2, 7, 67),
                                new Player("Player 10", 1, 11, 1, 29),
                                new Player("Player 11", 2, 6, 9, 88),
                                new Player("Player 12", 2, 22, 12, 78),
                                new Player("Player 13", 10, 45, 33, 66),
                                new Player("Player 14", 3, 31, 44, 22),
                                new Player("Player 15", 1, 12, 22, 12),
                                new Player("Player 16", 11, 16, 55, 26),
                                new Player("Player 17", 7, 25, 32, 15),
                                new Player("Player 18", 8, 19, 42, 11),
                                new Player("Player 19", 5, 30, 45, 5),
                                new Player("Player 20", 8, 41, 14, 67),
                                new Player("Player 21", 1, 13, 24, 29),
                                new Player("Player 22", 2, 18, 21, 88),

                };

                Player[] scratch4 = new Player[players4.length];
                Player[] outPlayers4 = new Player[players4.length];

                int k4 = PocuBasketballAssociation.findDreamTeamSize(players4, scratch4);
        }
}