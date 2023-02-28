package academy.pocu.comp3500.assignment3;

import academy.pocu.comp3500.assignment3.chess.Move;
import academy.pocu.comp3500.assignment3.chess.PlayerBase;

import java.util.ArrayList;
import java.util.Random;

public class Player extends PlayerBase {
    private static final int INFINITY = 100000;

    public Player(boolean isWhite, int maxMoveTimeMilliseconds) {
        super(isWhite, maxMoveTimeMilliseconds);
    }

    @Override
    public Move getNextMove(char[][] board) {
        // TODO Auto-generated method stub
        //return getNextMove(board, new Move(3, 6, 3, 4));

        // return new Move(3, 6, 3, 4);

        return getNextMove(board, null);
    }

    @Override
    public Move getNextMove(char[][] board, Move opponentMove) {
        // TODO Auto-generated method stub

        long startTime = System.currentTimeMillis();

        Move bestMove = null;
        int bestScore = -INFINITY;

        // 현재 플레이어의 색상
        char color = isWhite() ? 'W' : 'B';

        // 현재 상태에서 가능한 모든 수를 구합니다.
        ArrayList<Move> possibleMoves = getPossibleMoves(board, color);

        ArrayList<Move> sameMoves = new ArrayList<>();

        // 가능한 모든 수에 대해 상대방이 다음 수로 이동할 수 있는 모든 가능성에 대한 점수를 구합니다.
        for (Move move : possibleMoves) {
            char[][] newBoard = applyMove(board, move);

            int score = minMax(newBoard, color, getOpponentColor(color), false, 2, Integer.MIN_VALUE, Integer.MAX_VALUE);

            // 가장 높은 점수를 가진 수를 선택합니다.
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }

            if (score == bestScore) {
                sameMoves.add(move);
            }
        }

/*        long elapsedTime = System.currentTimeMillis() - startTime;
        if (elapsedTime >= getMaxMoveTimeMilliseconds()) {
            throw new RuntimeException("Time is up!");
        }*/

        if (sameMoves.size() != 0) {
            if (sameMoves.get(0) == bestMove) {
                Random random = new Random();
                return sameMoves.get(random.nextInt(sameMoves.size()));
            }
        }

        return bestMove;
    }

    private ArrayList<Move> getPossibleMoves(char[][] board, char color) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

        int left;
        int right;
        if (color == 'W') {
            left = 97;
            right = 122;
        } else {
            left = 65;
            right = 90;
        }

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
/*                if (board[row][col] == color) {
                    ArrayList<Move> movesFromCurrentPosition = getPossibleMovesFromPosition(board, color, row, col);
                    possibleMoves.addAll(movesFromCurrentPosition);
                }*/
                if (board[col][row] >= left && board[col][row] <= right) {
                    ArrayList<Move> movesFromCurrentPosition = getPossibleMovesFromPosition(board, color, row, col);
                    possibleMoves.addAll(movesFromCurrentPosition);
                }
            }
        }

        return possibleMoves;
    }

    // 현재 위치에서 가능한 모든 수를 반환하는 함수
    private ArrayList<Move> getPossibleMovesFromPosition(char[][] board, char color, int row, int col) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

        int left;
        int right;
        if (color == 'W') {
            left = 97;
            right = 122;
        } else {
            left = 65;
            right = 90;
        }

        int lessMin = 0;
        int lessMax = 8;

        int opponentLeft = getOpponentColorToAscii(color);
        int opponentRight = opponentLeft + 25;


        int x = row;
        int y = col;

        /*for (int x = lessMin; x < lessMax; x++) {
            for (int y = lessMin; y < lessMax; y++) {*/

        char piece = board[y][x];
        if (piece >= opponentLeft && piece <= opponentRight) {
            return possibleMoves;
        }

        switch (piece) {
            case 'P':
            case 'p': {
                int afterY;
                if (color == 'B') {
                    afterY = y + 1;
                } else {
                    afterY = y - 1;
                }

                int pawnStart;
                if (color == 'W') {
                    pawnStart = 6;
                } else {
                    pawnStart = 1;
                }

                if ((afterY >= lessMin && afterY < lessMax) && board[afterY][x] == 0) {
                    possibleMoves.add(new Move(x, y, x, afterY));

                    if (y == pawnStart && (afterY + 1 < lessMax) && board[afterY + 1][x] == 0) {
                        possibleMoves.add(new Move(x, y, x, afterY + 1));
                    }
                }


                int afterXleft = x - 1;
                int afterXright = x + 1;

                if (afterXleft >= lessMin && afterXleft < lessMax && (afterY >= lessMin && afterY < lessMax) && board[afterY][afterXleft] >= opponentLeft && board[afterY][afterXleft] <= opponentRight) {
                    possibleMoves.add(new Move(x, y, afterXleft, afterY));
                }

                if (afterXright < lessMax && (afterY >= lessMin && afterY < lessMax) && board[afterY][afterXright] >= opponentLeft && board[afterY][afterXright] <= opponentRight) {
                    possibleMoves.add(new Move(x, y, afterXright, afterY));
                }


                break;
            }


            case 'K':
            case 'k': {
                int[] xCase = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
                int[] yCase = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};

                for (int i = 0; i < 8; i++) {
                    int afterY = y + yCase[i];
                    int afterX = x + xCase[i];

                    if (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {
                        if ((board[afterY][afterX] >= opponentLeft && board[afterY][afterX] <= opponentRight) || (board[afterY][afterX] == 0)) {
                            possibleMoves.add(new Move(x, y, afterX, afterY));
                        }
                    }
                }

                break;
            }

            case 'Q':
            case 'q': {
                int[] xCase = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
                int[] yCase = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};

                for (int i = 0; i < 8; i++) {
                    int afterX = x + xCase[i];
                    int afterY = y + yCase[i];

                    if (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {

                        while (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {

                            if (board[afterY][afterX] >= opponentLeft && board[afterY][afterX] <= opponentRight) {
                                possibleMoves.add(new Move(x, y, afterX, afterY));
                                break;
                            }

                            if (board[afterY][afterX] >= left && board[afterY][afterX] <= right) {
                                break;
                            }

                            possibleMoves.add(new Move(x, y, afterX, afterY));

                            afterX += xCase[i];
                            afterY += yCase[i];

                        }


                    }
                }

                break;
            }

            case 'R':
            case 'r': {
                int[] xCase = new int[]{0, -1, 1, 0};
                int[] yCase = new int[]{-1, 0, 0, 1};

                for (int i = 0; i < 4; i++) {
                    int afterX = x + xCase[i];
                    int afterY = y + yCase[i];

                    if (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {

                        while (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {

                            if (board[afterY][afterX] >= opponentLeft && board[afterY][afterX] <= opponentRight) {
                                possibleMoves.add(new Move(x, y, afterX, afterY));
                                break;
                            }

                            if (board[afterY][afterX] >= left && board[afterY][afterX] <= right) {
                                break;
                            }

                            possibleMoves.add(new Move(x, y, afterX, afterY));

                            afterX += xCase[i];
                            afterY += yCase[i];
                        }


                    }
                }

                break;
            }

            case 'B':
            case 'b': {
                int[] xCase = new int[]{-1, -1, 1, 1};
                int[] yCase = new int[]{-1, 1, -1, 1};

                for (int i = 0; i < 4; i++) {
                    int afterX = x + xCase[i];
                    int afterY = y + yCase[i];

                    if (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {

                        while (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {

                            if (board[afterY][afterX] >= opponentLeft && board[afterY][afterX] <= opponentRight) {
                                possibleMoves.add(new Move(x, y, afterX, afterY));
                                break;
                            }

                            if (board[afterY][afterX] >= left && board[afterY][afterX] <= right) {
                                break;
                            }

                            possibleMoves.add(new Move(x, y, afterX, afterY));

                            afterX += xCase[i];
                            afterY += yCase[i];
                        }


                    }
                }

                break;
            }

            case 'N':
            case 'n': {
                int[] xCase = new int[]{2, 2, -2, -2, 1, 1, -1, -1};
                int[] yCase = new int[]{-1, 1, -1, 1, 2, -2, 2, -2};

                for (int i = 0; i < 8; i++) {
                    int afterX = x + xCase[i];
                    int afterY = y + yCase[i];

                    if (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {
                        if ((board[afterY][afterX] >= opponentLeft && board[afterY][afterX] <= opponentRight) || (board[afterY][afterX] == 0)) {
                            possibleMoves.add(new Move(x, y, afterX, afterY));
                        }
                    }
                }

                break;
            }

        }

        return possibleMoves;
    }

    // 현재 상태의 점수를 반환하는 함수
    private int evaluateBoard(char[][] board, char color) {
        int score = 0;

        char[] pieces = new char[]{'p', 'n', 'b', 'r', 'q', 'k'};

        char ascii;
        int ascii2;
        if (color == 'W') {
            ascii = 97;
            ascii2 = -32;
        } else {
            ascii = 65;
            ascii2 = 32;
        }

        char[] result = changeUpDown(pieces, color);

        int[] scores = new int[]{1, 3, 3, 5, 9, 200};

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] >= ascii && board[row][col] <= ascii + 25) {

                    for (int i = 0; i < 6; i++) {
                        if (board[row][col] == result[i]) {
                            score += scores[i];
                            break;
                        }
                    }
                } else if (board[row][col] >= ascii + ascii2 && board[row][col] <= ascii + 25 + ascii2) {
                    for (int i = 0; i < 6; i++) {
                        if (board[row][col] == result[i] + ascii2) {
                            score -= scores[i];
                            break;
                        }
                    }
                }
                    /*switch (board[row][col]) {
                        case 'p':
                            score += 1;
                            break;
                        case 'n':
                            score += 3;
                            break;
                        case 'b':
                            score += 3;
                            break;
                        case 'r':
                            score += 5;
                            break;
                        case 'q':
                            score += 9;
                            break;
                        case 'k':
                            score += 200;
                            break;
                    }*/
                // score += 1;


                    /*switch (board[row][col]) {
                        case 'P':
                            score -= 1;
                            break;
                        case 'N':
                            score -= 3;
                            break;
                        case 'B':
                            score -= 3;
                            break;
                        case 'R':
                            score -= 5;
                            break;
                        case 'Q':
                            score -= 9;
                            break;
                        case 'K':
                            score -= 200;
                            break;
                    }*/
                // score -= 1;

            }
        }

        return score;
    }

    // Minimax 알고리즘을 사용하여 최선의 수를 선택합니다.
    private int minMax(char[][] board, char currentPlayerColor, char maximizingPlayerColor, boolean isMaximizingPlayer, int depth, int alpha, int beta) {
        int score = evaluateBoard(board, currentPlayerColor);

        if (depth == 0 || Math.abs(score) == INFINITY) {
            return score;
        }

        if (isMaximizingPlayer) {
            // int bestScore = -INFINITY;
            int bestScore = score;
            ArrayList<Move> possibleMoves = getPossibleMoves(board, maximizingPlayerColor);
            for (Move move : possibleMoves) {
                char[][] newBoard = applyMove(board, move);
                int currentScore = minMax(newBoard, currentPlayerColor, maximizingPlayerColor, false, depth - 1, alpha, beta);
                bestScore = Math.max(bestScore, currentScore);
                alpha = Math.max(alpha, bestScore);
                if (beta <= alpha) {
                    break;
                }
            }
            return bestScore;
        } else {
            // int bestScore = INFINITY;
            int bestScore = score;
            ArrayList<Move> possibleMoves = getPossibleMoves(board, getOpponentColor(maximizingPlayerColor));
            for (Move move : possibleMoves) {
                char[][] newBoard = applyMove(board, move);
                int currentScore = minMax(newBoard, currentPlayerColor, maximizingPlayerColor, true, depth - 1, alpha, beta);
                bestScore = Math.min(bestScore, currentScore);
                beta = Math.min(beta, bestScore);
                if (beta <= alpha) {
                    break;
                }
            }
            return bestScore;
        }
    }

    private char getOpponentColorToAscii(char color) {
        if (color == 'W') {
            return 65;
        } else {
            return 97;
        }
    }

    private char getOpponentColor(char color) {
        return color == 'W' ? 'B' : 'W';
    }

    // 특정 수를 적용한 새로운 보드를 반환합니다.
    private char[][] applyMove(char[][] board, Move move) {
        char[][] newBoard = new char[board.length][board[0].length];
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                newBoard[col][row] = board[col][row];
            }
        }

        newBoard[move.toY][move.toX] = newBoard[move.fromY][move.fromX];
        newBoard[move.fromY][move.fromX] = 0;

        return newBoard;
    }

    private char[] changeUpDown(char[] arr, char color) {

        if (color == 'B') {
            char[] arr2 = new char[arr.length];
            for (int i = 0; i < arr.length; i++) {
                arr2[i] = (char) (arr[i] - 32);
            }
            return arr2;
        } else {
            return arr;
        }
    }

}




