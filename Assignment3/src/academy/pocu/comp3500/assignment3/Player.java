package academy.pocu.comp3500.assignment3;

import academy.pocu.comp3500.assignment3.chess.Move;
import academy.pocu.comp3500.assignment3.chess.PlayerBase;

public class Player extends PlayerBase{

    public Player(boolean isWhite, int maxMoveTimeMilliseconds) {
        super(isWhite, maxMoveTimeMilliseconds);
    }

    @Override
    public Move getNextMove(char[][] board) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Move getNextMove(char[][] board, Move opponentMove) {
        // TODO Auto-generated method stub
        return null;
    }

    
}