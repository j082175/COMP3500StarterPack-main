package academy.pocu.comp3500.assignment3;

import academy.pocu.comp3500.assignment3.chess.Move;
import academy.pocu.comp3500.assignment3.chess.PlayerBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Player extends PlayerBase {
    private static final int INFINITY = 100000;
    private static final Map<Character, Integer> mapW = new HashMap<>();

    public Player(boolean isWhite, int maxMoveTimeMilliseconds) {
        super(isWhite, maxMoveTimeMilliseconds);

        mapW.put('p', 1);
        mapW.put('b', 3);
        mapW.put('n', 3);
        mapW.put('r', 5);
        mapW.put('q', 9);
        mapW.put('k', 200);
    }

    @Override
    public Move getNextMove(char[][] board) {
        return getNextMove(board, null);
    }

    @Override
    public Move getNextMove(char[][] board, Move opponentMove) {
        Move bestMove = null;
        int bestScore = -INFINITY;
        int bestScore2 = -INFINITY;

        // 현재 플레이어의 색상
        char color = isWhite() ? 'W' : 'B';

        // 현재 상태에서 가능한 모든 수를 구합니다.
        // ArrayList<Move> possibleMoves = getPossibleMoves(board, color);
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.boardInitializer(board);
        ArrayList<Move> possibleMoves = getPossibleMovesFromPosition(chessBoard, color);

        ArrayList<Move> sameMoves = new ArrayList<>();

        // 가능한 모든 수에 대해 상대방이 다음 수로 이동할 수 있는 모든 가능성에 대한 점수를 구합니다.
        for (Move move : possibleMoves) {
            // char[][] newBoard = applyMove(board, move);
            ChessBoard chess2 = new ChessBoard(chessBoard);

            applyMove(chess2, move);


            int[] minValue = {INFINITY};

            int s = minMax(chess2, getOpponentColor(color), 1, -1, minValue);

            int score = minMax(chess2, getOpponentColor(color), 5, -1, minValue);

            // 가장 높은 점수를 가진 수를 선택합니다.

            int value = score > s ? score : s;
            int value2 = score < s ? score : s;

            if (value != INFINITY && value2 != INFINITY) {
                if (value > bestScore || value2 > bestScore2) {
                    bestScore = value;
                    bestScore2 = value2;
                    bestMove = move;
                    sameMoves.clear();
                }

                if (value == bestScore && value2 == bestScore2) {
                    sameMoves.add(move);
                }
            }

        }


        if (sameMoves.size() != 0) {
            Random random = new Random();
            return sameMoves.get(random.nextInt(sameMoves.size()));
        }

        if (bestMove == null) {
            Random random = new Random();
            return possibleMoves.get(random.nextInt(possibleMoves.size()));
        }

        return bestMove;
    }

    // 현재 위치에서 가능한 모든 수를 반환하는 함수
    private ArrayList<Move> getPossibleMovesFromPosition(ChessBoard chessBoard, char color) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        possibleMoves.ensureCapacity(10);

        int priority = -1;
        ArrayList<Move> bestMoveContainer = new ArrayList<>();
        int bestMove = -1;
        bestMoveContainer.add(null);

        int left;
        int right;
        int chooser; // 소문자를 기준으로 할거임

        if (color == 'W') {
            left = 97;
            right = 122;
            chooser = -32;
        } else {
            left = 65;
            right = 90;
            chooser = 0;
        }

        int lessMin = 0;
        int lessMax = 8;

        int opponentLeft = getOpponentColorToAscii(color);
        int opponentRight = opponentLeft + 25;


        for (int y = lessMin; y < lessMax; y++) {
            for (int x = lessMin; x < lessMax; x++) {
                char piece = checkPiece(chessBoard, y, x);
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

                        if (initialMoveCheck && checkPiece(chessBoard, afterY, x) == 0) {
                            possibleMoves.add(new Move(x, y, x, afterY));

                            if (y == pawnStart && (afterY2 < lessMax) && checkPiece(chessBoard, afterY2, x) == 0) {
                                possibleMoves.add(new Move(x, y, x, afterY2));
                            }
                        }


                        int afterXleft = x - 1;
                        int afterXright = x + 1;

                        boolean initialMoveLeftCheck = afterXleft >= lessMin && afterXleft < lessMax && afterY >= lessMin && afterY < lessMax;

                        char pCh = checkPiece(chessBoard, afterY, afterXleft);

                        if (initialMoveLeftCheck && pCh >= opponentLeft && pCh <= opponentRight) {

                            // 공격적 경우의 수 중
                            if (pCh == 'k' + chooser) {
                                priority = 200;
                                //bestMove.set(0, mapW.get('k'));
                                bestMove = mapW.get('k');
                                bestMoveContainer.set(0, new Move(x, y, afterXleft, afterY));
                            }

                            if (pCh == 'q' + chooser) {
                                if (bestMove < mapW.get('q') && priority < 100) {
                                    priority = 100;
                                    //bestMove.set(0, mapW.get('q'));
                                    bestMove = mapW.get('q');
                                    bestMoveContainer.set(0, new Move(x, y, afterXleft, afterY));
                                }
                            }

                            if (pCh == 'r' + chooser) {
                                if (bestMove < mapW.get('r') && priority < 50) {
                                    priority = 50;
                                    bestMove = mapW.get('r');
                                    bestMoveContainer.set(0, new Move(x, y, afterXleft, afterY));
                                }
                            }

                            if (pCh == 'b' + chooser || pCh == 'n' + chooser) {
                                if (bestMove < mapW.get('b') && priority < 30) {
                                    priority = 30;
                                    bestMove = mapW.get('b');
                                    bestMoveContainer.set(0, new Move(x, y, afterXleft, afterY));
                                }
                            }

                            if (pCh == 'p' + chooser) {
                                if (priority < 10) {
                                    priority = 10;
                                    bestMove = mapW.get('p');
                                    bestMoveContainer.set(0, new Move(x, y, afterXleft, afterY));
                                }
                            }

                            possibleMoves.add(new Move(x, y, afterXleft, afterY));
                        }

                        boolean initialMoveRightCheck = afterXright < lessMax && afterY >= lessMin && afterY < lessMax;

                        char pCh2 = checkPiece(chessBoard, afterY, afterXright);

                        if (initialMoveRightCheck && pCh2 >= opponentLeft && pCh2 <= opponentRight) {

                            // 공격적 경우의 수 중
                            if (pCh2 == 'k' + chooser) {

                                priority = 200;
                                bestMove = mapW.get('k');
                                bestMoveContainer.set(0, new Move(x, y, afterXright, afterY));
                                break;
                            }

                            if (pCh2 == 'q' + chooser) {
                                if (bestMove < mapW.get('q') && priority < 100) {
                                    priority = 100;
                                    bestMove = mapW.get('q');
                                    bestMoveContainer.set(0, new Move(x, y, afterXright, afterY));
                                    break;
                                }
                            }

                            if (pCh2 == 'r' + chooser) {
                                if (bestMove < mapW.get('r') && priority < 50) {
                                    priority = 50;
                                    bestMove = mapW.get('r');
                                    bestMoveContainer.set(0, new Move(x, y, afterXright, afterY));
                                    break;
                                }
                            }

                            if (pCh2 == 'b' + chooser || pCh2 == 'n' + chooser) {
                                if (bestMove < mapW.get('b') && priority < 30) {
                                    priority = 30;
                                    bestMove = mapW.get('b');
                                    bestMoveContainer.set(0, new Move(x, y, afterXright, afterY));
                                    break;
                                }
                            }

                            if (pCh2 == 'p' + chooser) {
                                if (priority < 10) {
                                    priority = 10;
                                    bestMove = mapW.get('p');
                                    bestMoveContainer.set(0, new Move(x, y, afterXright, afterY));
                                    break;
                                }
                            }

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

                            char kCh = checkPiece(chessBoard, afterY, afterX);

                            if (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {
                                if (kCh >= opponentLeft && kCh <= opponentRight) {
                                    // 공격적 경우의 수 중
                                    if (kCh == 'k' + chooser) {
                                        if (priority < 196) {
                                            priority = 196;
                                            bestMove = mapW.get('k');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (kCh == 'q' + chooser) {
                                        if (priority < 96) {
                                            priority = 96;
                                            bestMove = mapW.get('q');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (kCh == 'r' + chooser) {
                                        if (priority < 46) {
                                            priority = 46;
                                            bestMove = mapW.get('r');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (kCh == 'b' + chooser || kCh == 'n' + chooser) {
                                        if (priority < 26) {
                                            priority = 26;
                                            bestMove = mapW.get('b');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (kCh == 'p' + chooser) {
                                        if (priority < 6) {
                                            priority = 6;
                                            bestMove = mapW.get('p');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    possibleMoves.add(new Move(x, y, afterX, afterY));
                                }

                                if (kCh == 0) {
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

                                char qCh = checkPiece(chessBoard, afterY, afterX);

                                if (qCh >= opponentLeft && qCh <= opponentRight) {

                                    // 공격적 경우의 수 중
                                    if (qCh == 'k' + chooser) {
                                        if (priority < 197) {
                                            priority = 197;
                                            bestMove = mapW.get('k');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (qCh == 'q' + chooser) {
                                        if (priority < 97) {
                                            priority = 97;
                                            bestMove = mapW.get('q');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (qCh == 'r' + chooser) {
                                        if (priority < 47) {
                                            priority = 47;
                                            bestMove = mapW.get('r');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (qCh == 'b' + chooser || qCh == 'n' + chooser) {
                                        if (priority < 27) {
                                            priority = 27;
                                            bestMove = mapW.get('b');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (qCh == 'p' + chooser) {
                                        if (priority < 7) {
                                            priority = 7;
                                            bestMove = mapW.get('p');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    possibleMoves.add(new Move(x, y, afterX, afterY));
                                    break;
                                }

                                if (qCh >= left && qCh <= right) {
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

                                    char rCh = checkPiece(chessBoard, afterY, afterX);

                                    if (rCh >= opponentLeft && rCh <= opponentRight) {

                                        // 공격적 경우의 수 중
                                        if (rCh == 'k' + chooser) {
                                            if (priority < 198) {
                                                priority = 198;
                                                bestMove = mapW.get('k');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (rCh == 'q' + chooser) {
                                            if (bestMove < mapW.get('q') && priority < 98) {
                                                priority = 98;
                                                bestMove = mapW.get('q');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }

                                        }

                                        if (rCh == 'r' + chooser) {
                                            if (priority < 48) {
                                                priority = 48;
                                                bestMove = mapW.get('r');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (rCh == 'b' + chooser || rCh == 'n' + chooser) {
                                            if (priority < 28) {
                                                priority = 28;
                                                bestMove = mapW.get('b');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (rCh == 'p' + chooser) {
                                            if (priority < 8) {
                                                priority = 8;
                                                bestMove = mapW.get('p');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        possibleMoves.add(new Move(x, y, afterX, afterY));
                                        break;
                                    }

                                    if (rCh >= left && rCh <= right) {
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

                                    char bCh = checkPiece(chessBoard, afterY, afterX);

                                    if (bCh >= opponentLeft && bCh <= opponentRight) {

                                        // 공격적 경우의 수 중
                                        if (bCh == 'k' + chooser) {
                                            if (priority < 199) {
                                                priority = 199;
                                                bestMove = mapW.get('k');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (bCh == 'q' + chooser) {
                                            if (bestMove < mapW.get('q') && priority < 99) {
                                                priority = 99;
                                                bestMove = mapW.get('q');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (bCh == 'r' + chooser) {
                                            if (bestMove < mapW.get('r') && priority < 49) {
                                                priority = 49;
                                                bestMove = mapW.get('r');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (bCh == 'b' + chooser || bCh == 'n' + chooser) {
                                            if (priority < 29) {
                                                priority = 29;
                                                bestMove = mapW.get('b');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (bCh == 'p' + chooser) {
                                            if (bestMove < mapW.get('p') && priority < 9) {
                                                priority = 9;
                                                bestMove = mapW.get('p');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        possibleMoves.add(new Move(x, y, afterX, afterY));
                                        break;
                                    }

                                    if (bCh >= left && bCh <= right) {
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

                            char nCh = checkPiece(chessBoard, afterY, afterX);

                            if (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {
                                if (nCh >= opponentLeft && nCh <= opponentRight) {

                                    // 공격적 경우의 수 중
                                    if (nCh == 'k' + chooser) {
                                        if (priority < 199) {
                                            priority = 199;
                                            bestMove = mapW.get('k');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (nCh == 'q' + chooser) {
                                        if (bestMove < mapW.get('q') && priority < 99) {
                                            priority = 99;
                                            bestMove = mapW.get('q');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (nCh == 'r' + chooser) {
                                        if (bestMove < mapW.get('r') && priority < 49) {
                                            priority = 49;
                                            bestMove = mapW.get('r');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (nCh == 'b' + chooser || nCh == 'n' + chooser) {
                                        if (priority < 29) {
                                            priority = 29;
                                            bestMove = mapW.get('b');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (nCh == 'p' + chooser) {
                                        if (bestMove < mapW.get('p') && priority < 9) {
                                            priority = 9;
                                            bestMove = mapW.get('p');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    possibleMoves.add(new Move(x, y, afterX, afterY));
                                }

                                if (nCh == 0) {
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

        if (possibleMoves.size() > 10) {
            ArrayList<Move> newArr = new ArrayList<>(10);
            Random r = new Random();
            for (int i = 0; i < 10; i++) {
                int rand = r.nextInt(possibleMoves.size());
                newArr.add(possibleMoves.get(rand));
                possibleMoves.remove(rand);
            }

            return newArr;
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

    private int evaluateBoard2(char[][] board, char color) {
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
                }

            }
        }

        return score;
    }

    // Minimax 알고리즘을 사용하여 최선의 수를 선택합니다.
/*    private int minMax(char[][] board, char color, int depth, int change) {
        int score = evaluateBoard(board, color);

        if (depth == 0 || Math.abs(score) == INFINITY) {
            return score;
        }

        char opponent;
        if (change == -1) {
            opponent = color;
            change = 1;
        } else {
            opponent = getOpponentColor(color);
            change = -1;
        }

        int bestScore = score;
        ArrayList<Move> possibleMoves = getPossibleMovesFromPosition(board, opponent, -1, -1);
        for (Move move : possibleMoves) {
            char[][] newBoard = applyMove(board, move);
            int currentScore = minMax(newBoard, color, depth - 1, change);
            if (change == 1) {
                bestScore = Math.max(bestScore, currentScore);
            } else {
                bestScore = Math.min(bestScore, currentScore);
            }

        }
        return bestScore;

    }*/


/*    private int minMax(char[][] board, char color, int depth, int check, int[] minValue) {
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
            char[][] newBoard = applyMove(board, move); // 현재 수를 적용한 새로운 보드 생성
            int score = -minMax(newBoard, getOpponentColor(color), depth - 1, check * -1, minValue); // 새로운 보드에 대해 미니맥스 재귀호출

            minValue[0] = score;
            if (check == 1) {
                bestScore = Math.max(bestScore, score); // 최적의 점수 갱신
            } else {
                bestScore = Math.min(bestScore, score);
            }

        }

        return bestScore;
    }*/


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
/*    private char[][] applyMove(char[][] board, Move move) {
        char[][] newBoard = new char[board.length][board[0].length];
        //char[][] newBoard = board;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                newBoard[col][row] = board[col][row];
            }
        }

        newBoard[move.toY][move.toX] = newBoard[move.fromY][move.fromX];
        newBoard[move.fromY][move.fromX] = 0;

        return newBoard;
    }*/

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

    private int evaluateBoard(ChessBoard chessBoard, char color) {

        int check = 1;
        if (color == 'B') {
            check *= -1;
        }

        long[] pieces = chessBoard.getPieces();
        long boardStatus = chessBoard.getBoardStatus();
        int count = 0;
        int score = 0;

        for (ChessBoard.ChessPiece c : ChessBoard.ChessPiece.values()) {
            if (pieces[count] != 0) {
                int i;
                long p = pieces[count];
                for (i = 0; p != 0; i++) {
                    p &= (p - 1);
                }

                score += c.getScore() * check * i;
            }

            ++count;
        }

        return score;
    }

    public static void applyMove(ChessBoard chessBoard, Move move) {
        int lessMin = 0;
        int lessMax = 8;
        int boardSize = lessMax * lessMax;

        int fromCount = boardSize - (move.fromY * lessMax + move.fromX);
        int toCount = boardSize - (move.toY * lessMax + move.toX);

        char pieceFrom = checkPiece(chessBoard, move.fromY, move.fromX);
        char pieceTo = checkPiece(chessBoard, move.toY, move.toX);

        long[] p = chessBoard.getPieces();

        int count = 0;
        for (ChessBoard.ChessPiece c : ChessBoard.ChessPiece.values()) {
            if (c.getChar() == pieceFrom) {
                //System.out.println(Long.toBinaryString(p[count]));
                p[count] ^= (long) 1 << fromCount - 1;
                //System.out.println(Long.toBinaryString(p[count]));
                p[count] ^= (long) 1 << toCount - 1;
                //System.out.println(Long.toBinaryString(p[count]));
            }

            if (c.getChar() == pieceTo) {
                p[count] ^= (long) 1 << toCount - 1;
            }

            chessBoard.setPieces(p);
            ++count;
        }

    }

    public static char checkPiece(ChessBoard chessBoard, int y, int x) {
        int lessMin = 0;
        int lessMax = 8;
        int boardSize = lessMax * lessMax;

        int totalCount = boardSize - (y * lessMax + x);

        // System.out.println(Long.toBinaryString(offset));

        long[] pieces = chessBoard.getPieces();
        int count = 0;
        for (ChessBoard.ChessPiece c : ChessBoard.ChessPiece.values()) {
            if ((pieces[count] | (long) 1 << totalCount - 1) == pieces[count]) {
                //outPieces[0] = pieces[count];
                return c.getChar();
            }
            ++count;
        }

        return 0;
    }

    private int minMax(ChessBoard chessBoard, char color, int depth, int check, int[] minValue) {
        if (depth == 0) { // 종료 조건
            return evaluateBoard(chessBoard, getOpponentColor(color));
        }

        ArrayList<Move> possibleMoves = getPossibleMovesFromPosition(chessBoard, color); // 현재 플레이어의 가능한 수 모두 생성
        int bestScore;

        if (check == 1) {
            bestScore = -INFINITY;
        } else {
            bestScore = INFINITY;
        }

        for (Move move : possibleMoves) {
            ChessBoard chessBoard2 = new ChessBoard(chessBoard);
            applyMove(chessBoard2, move); // 현재 수를 적용한 새로운 보드 생성
            int score = -minMax(chessBoard2, getOpponentColor(color), depth - 1, check * -1, minValue); // 새로운 보드에 대해 미니맥스 재귀호출

            minValue[0] = score;
            if (check == 1) {
                bestScore = Math.max(bestScore, score); // 최적의 점수 갱신
            } else {
                bestScore = Math.min(bestScore, score);
            }

        }

        return bestScore;
    }

}