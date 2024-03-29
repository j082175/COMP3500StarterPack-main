package academy.pocu.comp3500.assignment3;

import academy.pocu.comp3500.assignment3.chess.Move;
import academy.pocu.comp3500.assignment3.chess.PlayerBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player extends PlayerBase {
    private static final int INFINITY = 100000;
    private static final Map<Character, Integer> MAP_W = new HashMap<>();

    public Player(boolean isWhite, int maxMoveTimeMilliseconds) {
        super(isWhite, maxMoveTimeMilliseconds);

        MAP_W.put('p', 1);
        MAP_W.put('b', 3);
        MAP_W.put('n', 3);
        MAP_W.put('r', 5);
        MAP_W.put('q', 9);
        MAP_W.put('k', 200);
    }

    @Override
    public Move getNextMove(char[][] board) {
        return getNextMove(board, null);
    }

    @Override
    public Move getNextMove(char[][] board, Move opponentMove) {
        Move bestMove = null;
        int bestScore = -INFINITY;
        // 현재 플레이어의 색상
        char color = isWhite() ? 'W' : 'B';

        // 현재 상태에서 가능한 모든 수를 구합니다.
        // ArrayList<Move> possibleMoves = getPossibleMoves(board, color);
        ArrayList<Move> possibleMoves = getPossibleMovesFromPosition(board, color);

        ArrayList<Move> sameMoves = new ArrayList<>();

        // 가능한 모든 수에 대해 상대방이 다음 수로 이동할 수 있는 모든 가능성에 대한 점수를 구합니다.

        for (Move move : possibleMoves) {
            char[] hCh = {0};
            applyMove1(board, move, hCh);
            int score = minMax(board, getOpponentColor(color), 1, -1);
            undoMove(board, move, hCh);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        for (Move move : possibleMoves) {
            char[] hCh = {0};
            applyMove1(board, move, hCh);
            int score = minMax(board, getOpponentColor(color), 3, -1);
            undoMove(board, move, hCh);

            // 가장 높은 점수를 가진 수를 선택합니다.

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

/*        if (sameMoves.size() != 0) {
            Random random = new Random();
            return sameMoves.get(random.nextInt(sameMoves.size()));
        }

        if (bestMove == null) {
            Random random = new Random();
            return possibleMoves.get(random.nextInt(possibleMoves.size()));
        }*/

        return bestMove;
    }

    // 현재 위치에서 가능한 모든 수를 반환하는 함수
    private ArrayList<Move> getPossibleMovesFromPosition(char[][] board, char color) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        possibleMoves.ensureCapacity(10);

        int priority = -1;
        ArrayList<Move> bestMoveContainer = new ArrayList<>();
        int bestMove = -1;
        bestMoveContainer.add(null);

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
        int lessMax = board.length;

        int opponentLeft = getOpponentColorToAscii(color);
        int opponentRight = opponentLeft + 25;

        for (int y = lessMin; y < lessMax; y++) {
            for (int x = lessMin; x < lessMax; x++) {
                char piece = board[y][x];
                if (piece >= opponentLeft && piece <= opponentRight) {
                    continue;
                }

                switch (piece) {
                    case 'P':
                    case 'p': {
                        int afterY;
                        int afterY2;
                        if (color == 'B') {
                            afterY = y + 1;
                            afterY2 = y + 2;
                        } else {
                            afterY = y - 1;
                            afterY2 = y - 2;
                        }

                        int pawnStart;
                        if (color == 'W') {
                            pawnStart = 6;
                        } else {
                            pawnStart = 1;
                        }

                        boolean initialMoveCheck = afterY >= lessMin && afterY < lessMax;

                        if (initialMoveCheck && board[afterY][x] == 0) {
                            possibleMoves.add(new Move(x, y, x, afterY));

                            if (y == pawnStart && (afterY2 < lessMax) && board[afterY2][x] == 0) {
                                possibleMoves.add(new Move(x, y, x, afterY2));
                            }
                        }


                        int afterXleft = x - 1;
                        int afterXright = x + 1;

                        boolean initialMoveLeftCheck = afterXleft >= lessMin && afterXleft < lessMax && afterY >= lessMin && afterY < lessMax;

                        if (initialMoveLeftCheck && board[afterY][afterXleft] >= opponentLeft && board[afterY][afterXleft] <= opponentRight) {

                            // 공격적 경우의 수 중
                            /*if (board[afterY][afterXleft] == 'k' + chooser) {
                                priority = 200;
                                //bestMove.set(0, mapW.get('k'));
                                bestMove = MAP_W.get('k');
                                bestMoveContainer.set(0, new Move(x, y, afterXleft, afterY));
                            }

                            if (board[afterY][afterXleft] == 'q' + chooser) {
                                if (bestMove < MAP_W.get('q') && priority < 100) {
                                    priority = 100;
                                    //bestMove.set(0, mapW.get('q'));
                                    bestMove = MAP_W.get('q');
                                    bestMoveContainer.set(0, new Move(x, y, afterXleft, afterY));
                                }
                            }

                            if (board[afterY][afterXleft] == 'r' + chooser) {
                                if (bestMove < MAP_W.get('r') && priority < 50) {
                                    priority = 50;
                                    bestMove = MAP_W.get('r');
                                    bestMoveContainer.set(0, new Move(x, y, afterXleft, afterY));
                                }
                            }

                            if (board[afterY][afterXleft] == 'b' + chooser || board[afterY][afterXleft] == 'n' + chooser) {
                                if (bestMove < MAP_W.get('b') && priority < 30) {
                                    priority = 30;
                                    bestMove = MAP_W.get('b');
                                    bestMoveContainer.set(0, new Move(x, y, afterXleft, afterY));
                                }
                            }

                            if (board[afterY][afterXleft] == 'p' + chooser) {
                                if (priority < 10) {
                                    priority = 10;
                                    bestMove = MAP_W.get('p');
                                    bestMoveContainer.set(0, new Move(x, y, afterXleft, afterY));
                                }
                            }*/

                            possibleMoves.add(new Move(x, y, afterXleft, afterY));
                        }

                        boolean initialMoveRightCheck = afterXright < lessMax && afterY >= lessMin && afterY < lessMax;

                        if (initialMoveRightCheck && board[afterY][afterXright] >= opponentLeft && board[afterY][afterXright] <= opponentRight) {

                            // 공격적 경우의 수 중
                            /*if (board[afterY][afterXright] == 'k' + chooser) {

                                priority = 200;
                                bestMove = MAP_W.get('k');
                                bestMoveContainer.set(0, new Move(x, y, afterXright, afterY));
                                break;
                            }

                            if (board[afterY][afterXright] == 'q' + chooser) {
                                if (bestMove < MAP_W.get('q') && priority < 100) {
                                    priority = 100;
                                    bestMove = MAP_W.get('q');
                                    bestMoveContainer.set(0, new Move(x, y, afterXright, afterY));
                                    break;
                                }
                            }

                            if (board[afterY][afterXright] == 'r' + chooser) {
                                if (bestMove < MAP_W.get('r') && priority < 50) {
                                    priority = 50;
                                    bestMove = MAP_W.get('r');
                                    bestMoveContainer.set(0, new Move(x, y, afterXright, afterY));
                                    break;
                                }
                            }

                            if (board[afterY][afterXright] == 'b' + chooser || board[afterY][afterXright] == 'n' + chooser) {
                                if (bestMove < MAP_W.get('b') && priority < 30) {
                                    priority = 30;
                                    bestMove = MAP_W.get('b');
                                    bestMoveContainer.set(0, new Move(x, y, afterXright, afterY));
                                    break;
                                }
                            }

                            if (board[afterY][afterXright] == 'p' + chooser) {
                                if (priority < 10) {
                                    priority = 10;
                                    bestMove = MAP_W.get('p');
                                    bestMoveContainer.set(0, new Move(x, y, afterXright, afterY));
                                    break;
                                }
                            }*/

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
                                if (board[afterY][afterX] >= opponentLeft && board[afterY][afterX] <= opponentRight) {
                                    // 공격적 경우의 수 중
                                    /*if (board[afterY][afterX] == 'k' + chooser) {
                                        if (priority < 196) {
                                            priority = 196;
                                            bestMove = MAP_W.get('k');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (board[afterY][afterX] == 'q' + chooser) {
                                        if (priority < 96) {
                                            priority = 96;
                                            bestMove = MAP_W.get('q');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (board[afterY][afterX] == 'r' + chooser) {
                                        if (priority < 46) {
                                            priority = 46;
                                            bestMove = MAP_W.get('r');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (board[afterY][afterX] == 'b' + chooser || board[afterY][afterX] == 'n' + chooser) {
                                        if (priority < 26) {
                                            priority = 26;
                                            bestMove = MAP_W.get('b');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (board[afterY][afterX] == 'p' + chooser) {
                                        if (priority < 6) {
                                            priority = 6;
                                            bestMove = MAP_W.get('p');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }*/

                                    possibleMoves.add(new Move(x, y, afterX, afterY));
                                }

                                if (board[afterY][afterX] == 0) {
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


                            while (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {
                                if (board[afterY][afterX] >= opponentLeft && board[afterY][afterX] <= opponentRight) {

                                    // 공격적 경우의 수 중
                                    /*if (board[afterY][afterX] == 'k' + chooser) {
                                        if (priority < 197) {
                                            priority = 197;
                                            bestMove = MAP_W.get('k');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (board[afterY][afterX] == 'q' + chooser) {
                                        if (priority < 97) {
                                            priority = 97;
                                            bestMove = MAP_W.get('q');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (board[afterY][afterX] == 'r' + chooser) {
                                        if (priority < 47) {
                                            priority = 47;
                                            bestMove = MAP_W.get('r');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (board[afterY][afterX] == 'b' + chooser || board[afterY][afterX] == 'n' + chooser) {
                                        if (priority < 27) {
                                            priority = 27;
                                            bestMove = MAP_W.get('b');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (board[afterY][afterX] == 'p' + chooser) {
                                        if (priority < 7) {
                                            priority = 7;
                                            bestMove = MAP_W.get('p');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }*/

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
                        break;
                    }

                    case 'R':
                    case 'r': {
                        int[] xCase = new int[]{0, -1, 1, 0};
                        int[] yCase = new int[]{-1, 0, 0, 1};

                        for (int i = 0; i < xCase.length; i++) {
                            int afterX = x + xCase[i];
                            int afterY = y + yCase[i];

                            if (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {

                                while (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {

                                    if (board[afterY][afterX] >= opponentLeft && board[afterY][afterX] <= opponentRight) {

                                        // 공격적 경우의 수 중
                                        /*if (board[afterY][afterX] == 'k' + chooser) {
                                            if (priority < 198) {
                                                priority = 198;
                                                bestMove = MAP_W.get('k');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (board[afterY][afterX] == 'q' + chooser) {
                                            if (bestMove < MAP_W.get('q') && priority < 98) {
                                                priority = 98;
                                                bestMove = MAP_W.get('q');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }

                                        }

                                        if (board[afterY][afterX] == 'r' + chooser) {
                                            if (priority < 48) {
                                                priority = 48;
                                                bestMove = MAP_W.get('r');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (board[afterY][afterX] == 'b' + chooser || board[afterY][afterX] == 'n' + chooser) {
                                            if (priority < 28) {
                                                priority = 28;
                                                bestMove = MAP_W.get('b');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (board[afterY][afterX] == 'p' + chooser) {
                                            if (priority < 8) {
                                                priority = 8;
                                                bestMove = MAP_W.get('p');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }*/

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

                        HashMap<MoveTo, Integer> checkHashMap = new HashMap<>();

                        for (int i = 0; i < 4; i++) {
                            int afterX = x + xCase[i];
                            int afterY = y + yCase[i];

                            if (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {

                                while (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {

                                    if (board[afterY][afterX] >= opponentLeft && board[afterY][afterX] <= opponentRight) {

                                        // 공격적 경우의 수 중
                                        /*if (board[afterY][afterX] == 'k' + chooser) {
                                            if (priority < 199) {
                                                priority = 199;
                                                bestMove = MAP_W.get('k');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (board[afterY][afterX] == 'q' + chooser) {
                                            if (bestMove < MAP_W.get('q') && priority < 99) {
                                                priority = 99;
                                                bestMove = MAP_W.get('q');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (board[afterY][afterX] == 'r' + chooser) {
                                            if (bestMove < MAP_W.get('r') && priority < 49) {
                                                priority = 49;
                                                bestMove = MAP_W.get('r');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (board[afterY][afterX] == 'b' + chooser || board[afterY][afterX] == 'n' + chooser) {
                                            if (priority < 29) {
                                                priority = 29;
                                                bestMove = MAP_W.get('b');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (board[afterY][afterX] == 'p' + chooser) {
                                            if (bestMove < MAP_W.get('p') && priority < 9) {
                                                priority = 9;
                                                bestMove = MAP_W.get('p');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }*/

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
                                if ((board[afterY][afterX] >= opponentLeft && board[afterY][afterX] <= opponentRight)) {

                                    // 공격적 경우의 수 중
                                    /*if (board[afterY][afterX] == 'k' + chooser) {
                                        if (priority < 199) {
                                            priority = 199;
                                            bestMove = MAP_W.get('k');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (board[afterY][afterX] == 'q' + chooser) {
                                        if (bestMove < MAP_W.get('q') && priority < 99) {
                                            priority = 99;
                                            bestMove = MAP_W.get('q');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (board[afterY][afterX] == 'r' + chooser) {
                                        if (bestMove < MAP_W.get('r') && priority < 49) {
                                            priority = 49;
                                            bestMove = MAP_W.get('r');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (board[afterY][afterX] == 'b' + chooser || board[afterY][afterX] == 'n' + chooser) {
                                        if (priority < 29) {
                                            priority = 29;
                                            bestMove = MAP_W.get('b');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (board[afterY][afterX] == 'p' + chooser) {
                                        if (bestMove < MAP_W.get('p') && priority < 9) {
                                            priority = 9;
                                            bestMove = MAP_W.get('p');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }*/

                                    possibleMoves.add(new Move(x, y, afterX, afterY));
                                }

                                if ((board[afterY][afterX] == 0)) {
                                    possibleMoves.add(new Move(x, y, afterX, afterY));
                                }
                            }
                        }

                        break;
                    }

                }
            }
        }

        if (bestMove != -1) {
            return bestMoveContainer;
        }

        return possibleMoves;
    }

    // 현재 상태의 점수를 반환하는 함수
    private int evaluateBoard(char[][] board, char color) {
        int score = 0;

        char[] pieces = {'p', 'n', 'b', 'r', 'q', 'k'};

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

        int[] scores = {1, 3, 3, 5, 9, 200};

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

            }
        }

        return score;
    }

    private int minMax(char[][] board, char color, int depth, int check) {
        if (depth == 0) { // 종료 조건
            return evaluateBoard(board, getOpponentColor(color));
        }

        ArrayList<Move> possibleMoves = getPossibleMovesFromPosition(board, color); // 현재 플레이어의 가능한 수 모두 생성
        int bestScore;

        if (check == 1) {
            bestScore = -INFINITY;
        } else {
            bestScore = INFINITY;
        }

        for (Move move : possibleMoves) {
            char[] hCh = {0};
            applyMove1(board, move, hCh);
            int score = -minMax(board, getOpponentColor(color), depth - 1, check * -1); // 새로운 보드에 대해 미니맥스 재귀호출
            undoMove(board, move, hCh);

            if (check == 1) {
                bestScore = Math.max(bestScore, score); // 최적의 점수 갱신
            } else {
                bestScore = Math.min(bestScore, score);
            }

        }

        return bestScore;
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

    private void applyMove1(char[][] board, Move move, char[] hCh) {
        hCh[0] = board[move.toY][move.toX];
        board[move.toY][move.toX] = board[move.fromY][move.fromX];
        board[move.fromY][move.fromX] = ' ';
    }

    private void undoMove(char[][] board, Move move, char[] hCh) {
        board[move.fromY][move.fromX] = board[move.toY][move.toX];
        board[move.toY][move.toX] = hCh[0];
    }
}