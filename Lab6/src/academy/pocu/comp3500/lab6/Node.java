package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class Node {
    public Player value;
    public Node left;
    public Node right;
    public Node previous;

    public Node(Player data) {
        value = data;
        left = null;
        right = null;
    }

}
