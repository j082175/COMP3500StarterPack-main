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

        // 현재 플레이어의 색상
        char color = isWhite() ? 'W' : 'B';

        // 현재 상태에서 가능한 모든 수를 구합니다.
        // ArrayList<Move> possibleMoves = getPossibleMoves(board, color);
        ArrayList<Move> possibleMoves = getPossibleMovesFromPosition(board, color, -1, -1);

        ArrayList<Move> sameMoves = new ArrayList<>();

        // 가능한 모든 수에 대해 상대방이 다음 수로 이동할 수 있는 모든 가능성에 대한 점수를 구합니다.
        for (Move move : possibleMoves) {
            char[][] newBoard = applyMove(board, move);

            int score = minMax(newBoard, getOpponentColor(color), 2, -1);

            // 가장 높은 점수를 가진 수를 선택합니다.
            if (score != INFINITY && score > bestScore) {
                bestScore = score;
                bestMove = move;
            }

            if (score == bestScore) {
                sameMoves.add(move);
            }
        }


        if (sameMoves.size() != 0) {
            if (sameMoves.get(0) == bestMove) {
                Random random = new Random();
                return sameMoves.get(random.nextInt(sameMoves.size()));
            }
        }

        if (bestMove == null) {
            Random random = new Random();
            return possibleMoves.get(random.nextInt(possibleMoves.size()));
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
                if (board[row][col] == color) {
                    ArrayList<Move> movesFromCurrentPosition = getPossibleMovesFromPosition(board, color, row, col);
                    possibleMoves.addAll(movesFromCurrentPosition);
                }

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
        ArrayList<Move> possibleMovesForKing = new ArrayList<>();
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
        int lessMax = board.length;

        int opponentLeft = getOpponentColorToAscii(color);
        int opponentRight = opponentLeft + 25;


        for (int y = lessMin; y < lessMax; y++) {
            for (int x = lessMin; x < lessMax; x++) {
                char piece = board[y][x];
                if (piece >= opponentLeft && piece <= opponentRight) {
                    // return possibleMoves;
                    continue;
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

                        boolean initialMoveCheck = afterY >= lessMin && afterY < lessMax;

                        if (initialMoveCheck && board[afterY][x] == 0) {
                            possibleMoves.add(new Move(x, y, x, afterY));

                            if (y == pawnStart && (afterY + 1 < lessMax) && board[afterY + 1][x] == 0) {
                                possibleMoves.add(new Move(x, y, x, afterY + 1));
                            }
                        }


                        int afterXleft = x - 1;
                        int afterXright = x + 1;

                        boolean initialMoveLeftCheck = afterXleft >= lessMin && afterXleft < lessMax && afterY >= lessMin && afterY < lessMax;

                        if (initialMoveLeftCheck && board[afterY][afterXleft] >= opponentLeft && board[afterY][afterXleft] <= opponentRight) {

                            // 공격적 경우의 수 중
                            if (board[afterY][afterXleft] == 'k' + chooser) {
                                priority = 200;
                                //bestMove.set(0, mapW.get('k'));
                                bestMove = mapW.get('k');
                                bestMoveContainer.set(0, new Move(x, y, afterXleft, afterY));
                            }

                            if (board[afterY][afterXleft] == 'q' + chooser) {
                                if (bestMove < mapW.get('q') && priority < 100) {
                                    priority = 100;
                                    //bestMove.set(0, mapW.get('q'));
                                    bestMove = mapW.get('q');
                                    bestMoveContainer.set(0, new Move(x, y, afterXleft, afterY));
                                }
                            }

                            if (board[afterY][afterXleft] == 'r' + chooser) {
                                if (bestMove < mapW.get('r') && priority < 50) {
                                    priority = 50;
                                    bestMove = mapW.get('r');
                                    bestMoveContainer.set(0, new Move(x, y, afterXleft, afterY));
                                }
                            }

                            if (board[afterY][afterXleft] == 'b' + chooser || board[afterY][afterXleft] == 'n' + chooser) {
                                if (bestMove < mapW.get('b') && priority < 30) {
                                    priority = 30;
                                    bestMove = mapW.get('b');
                                    bestMoveContainer.set(0, new Move(x, y, afterXleft, afterY));
                                }
                            }

                            if (board[afterY][afterXleft] == 'p' + chooser) {
                                if (priority < 10) {
                                    priority = 10;
                                    bestMove = mapW.get('p');
                                    bestMoveContainer.set(0, new Move(x, y, afterXleft, afterY));
                                }
                            }

                            possibleMoves.add(new Move(x, y, afterXleft, afterY));
                            // return possibleMoves;
                        }

                        boolean initialMoveRightCheck = afterXright < lessMax && afterY >= lessMin && afterY < lessMax;

                        if (initialMoveRightCheck && board[afterY][afterXright] >= opponentLeft && board[afterY][afterXright] <= opponentRight) {

                            // 공격적 경우의 수 중
                            if (board[afterY][afterXright] == 'k' + chooser) {

                                priority = 200;
                                bestMove = mapW.get('k');
                                bestMoveContainer.set(0, new Move(x, y, afterXright, afterY));
                                break;
                            }

                            if (board[afterY][afterXright] == 'q' + chooser) {
                                if (bestMove < mapW.get('q') && priority < 100) {
                                    priority = 100;
                                    bestMove = mapW.get('q');
                                    bestMoveContainer.set(0, new Move(x, y, afterXright, afterY));
                                    break;
                                }
                            }

                            if (board[afterY][afterXright] == 'r' + chooser) {
                                if (bestMove < mapW.get('r') && priority < 50) {
                                    priority = 50;
                                    bestMove = mapW.get('r');
                                    bestMoveContainer.set(0, new Move(x, y, afterXright, afterY));
                                    break;
                                }
                            }

                            if (board[afterY][afterXright] == 'b' + chooser || board[afterY][afterXright] == 'n' + chooser) {
                                if (bestMove < mapW.get('b') && priority < 30) {
                                    priority = 30;
                                    bestMove = mapW.get('b');
                                    bestMoveContainer.set(0, new Move(x, y, afterXright, afterY));
                                    break;
                                }
                            }

                            if (board[afterY][afterXright] == 'p' + chooser) {
                                if (priority < 10) {
                                    priority = 10;
                                    bestMove = mapW.get('p');
                                    bestMoveContainer.set(0, new Move(x, y, afterXright, afterY));
                                    break;
                                }
                            }

                            possibleMoves.add(new Move(x, y, afterXright, afterY));
                            // return possibleMoves;
                        }


                        break;
                    }


                    case 'K':
                    case 'k': {


                        /*ArrayList<Move> checkmateCheck = new ArrayList<>();
                        HashSet<MoveTo> checkmateCheck = new HashSet<>();

                        {
                            int[] xCase2 = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
                            int[] yCase2 = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};

                            for (int j = 0; j < xCase2.length; j++) {
                                int afterY2 = y + yCase2[j];
                                int afterX2 = x + xCase2[j];
                                boolean isCheckmateFound = false;

                                if (j == 1 && checkmateCheck.size() == 0) {
                                    break;
                                }


                                // Rook case
                                {
                                    int[] xCase = new int[]{-1, 0, 1, 0};
                                    int[] yCase = new int[]{0, 1, 0, -1};


                                    for (int i = 0; i < 4; i++) {
                                        int afterY = afterY2 + yCase[i];
                                        int afterX = afterX2 + xCase[i];

                                        if (isCheckmateFound) {
                                            break;
                                        }

                                        while (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {
                                            if (board[afterY][afterX] >= opponentLeft && board[afterY][afterX] <= opponentRight) {

                                                // 체크메이트부터 확인

                                                if (board[afterY][afterX] == 'r' + chooser) {
                                                    // checkmateCheck.add(new Move(x, y, afterX2, afterY2));

                                                    checkmateCheck.add(new MoveTo(afterX2, afterY2));
                                                    isCheckmateFound = true;
                                                    break;
                                                }

                                                if (board[afterY][afterX] == 'q' + chooser) {
                                                    //checkmateCheck.add(new Move(x, y, afterX2, afterY2));

                                                    checkmateCheck.add(new MoveTo(afterX2, afterY2));
                                                    isCheckmateFound = true;
                                                    break;
                                                }

                                            }

                                            if (board[afterY][afterX] >= left && board[afterY][afterX] <= right) {
                                                break;
                                            }

                                            afterX += xCase[i];
                                            afterY += yCase[i];
                                        }


                                    }
                                }

                                // Bishop case
                                {
                                    int[] xCase = new int[]{-1, 1, 1, -1};
                                    int[] yCase = new int[]{-1, 1, -1, 1};

                                    for (int i = 0; i < 4; i++) {
                                        int afterY = afterY2 + yCase[i];
                                        int afterX = afterX2 + xCase[i];

                                        if (isCheckmateFound) {
                                            break;
                                        }

                                        while (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {
                                            if (board[afterY][afterX] >= opponentLeft && board[afterY][afterX] <= opponentRight) {

                                                // 체크메이트부터 확인

                                                if (board[afterY][afterX] == 'b' + chooser) {
                                                    //checkmateCheck.add(new Move(x, y, afterX2, afterY2));

                                                    checkmateCheck.add(new MoveTo(afterX2, afterY2));
                                                    isCheckmateFound = true;
                                                    break;
                                                }

                                                if (board[afterY][afterX] == 'q' + chooser) {
                                                    //checkmateCheck.add(new Move(x, y, afterX2, afterY2));

                                                    checkmateCheck.add(new MoveTo(afterX2, afterY2));
                                                    isCheckmateFound = true;
                                                    break;
                                                }

                                            }

                                            if (board[afterY][afterX] >= left && board[afterY][afterX] <= right) {
                                                break;
                                            }

                                            afterX += xCase[i];
                                            afterY += yCase[i];
                                        }

                                    }
                                }

                                // Kinght case
                                {
                                    if (isCheckmateFound) {
                                        continue;
                                    }

                                    int[] xCase = new int[]{2, 2, -2, -2, 1, 1, -1, -1};
                                    int[] yCase = new int[]{-1, 1, -1, 1, 2, -2, 2, -2};

                                    for (int i = 0; i < 8; i++) {
                                        int afterY = afterY2 + yCase[i];
                                        int afterX = afterX2 + xCase[i];

                                        if (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {
                                            if (board[afterY][afterX] >= opponentLeft && board[afterY][afterX] <= opponentRight) {

                                                if (board[afterY][afterX] == 'n' + chooser) {
                                                    //checkmateCheck.add(new Move(x, y, afterX2, afterY2));
                                                    checkmateCheck.add(new MoveTo(afterX2, afterY2));
                                                    break;
                                                }
                                            }
                                        }

                                    }
                                }

                            }
                        }*/


                        // ArrayList<MoveTo> allPossibilities = new ArrayList<>();
                        int[] xCase = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
                        int[] yCase = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};

                        HashMap<MoveTo, Integer> checkHashMap = new HashMap<>();

                        for (int i = 0; i < 8; i++) {
                            int afterY = y + yCase[i];
                            int afterX = x + xCase[i];


                            if (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {
                                if (board[afterY][afterX] >= opponentLeft && board[afterY][afterX] <= opponentRight || (board[afterY][afterX] == 0)) {
                                    // 공격적 경우의 수 중
                                    if (board[afterY][afterX] == 'k' + chooser) {
                                        if (priority < 196) {
                                            priority = 196;
                                            bestMove = mapW.get('k');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            return bestMoveContainer;
                                        }

                                    }

                                    if (board[afterY][afterX] == 'q' + chooser) {
                                        if (priority < 195) {
                                            priority = 195;
                                            bestMove = mapW.get('q');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            return bestMoveContainer;

                                        }

                                    }

                                    if (board[afterY][afterX] == 'r' + chooser) {
                                        if (priority < 194) {
                                            priority = 194;
                                            bestMove = mapW.get('r');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            return bestMoveContainer;

                                        }

                                    }

                                    if (board[afterY][afterX] == 'b' + chooser) {
                                        if (priority < 193) {
                                            priority = 193;
                                            bestMove = mapW.get('b');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            return bestMoveContainer;

                                        }

                                    }

                                    // allPossibilities.add(new MoveTo(afterX, afterY));
                                    //possibleMoves.add(new Move(x, y, afterX, afterY));
                                    possibleMovesForKing.add(new Move(x, y, afterX, afterY));

                                    ////////////////////////////////////////////////

                                    {
                                        for (int j = 0; j < 8; j++) {
                                            int afterX2 = afterX + xCase[j];
                                            int afterY2 = afterY + yCase[j];

                                            while (afterX2 >= lessMin && afterX2 < lessMax && afterY2 >= lessMin && afterY2 < lessMax) {
                                                if (board[afterY2][afterX2] >= opponentLeft && board[afterY2][afterX2] <= opponentRight) {

                                                    if (!(board[afterY2][afterX2] == 'q' + chooser || board[afterY2][afterX2] == 'r' + chooser || board[afterY2][afterX2] == 'b' + chooser)) {
                                                        MoveTo m = new MoveTo(afterX, afterY);
                                                        if (!checkHashMap.containsKey(new MoveTo(afterX, afterY))) {
                                                            checkHashMap.put(m, 0);
                                                            // possibleMoves.add(new Move(x, y, afterX, afterY));

                                                            if (bestMove != -1 && bestMove != mapW.get('k')) {
                                                                bestMoveContainer.add(new Move(x, y, afterX, afterY));
                                                            } else {
                                                                possibleMoves.add(new Move(x, y, afterX, afterY));
                                                            }
                                                        }
                                                    }

/*                                                    int offset = evaluateBoard2(board, color);
                                                    if (offset == 0) {
                                                        if (bestMove == -1) {
                                                            bestMove = mapW.get('k');
                                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                                        }
                                                    }*/

                                                    break;
                                                }

                                                if (board[afterY2][afterX2] >= left && board[afterY2][afterX2] <= right) {
                                                    break;
                                                }

                                                // possibleMoves.add(new Move(x, y, afterX, afterY));

                                                afterX2 += xCase[j];
                                                afterY2 += yCase[j];
                                            }
                                        }
                                    }

                                    ///////////////////////////////////////////////////////


                                    // bestMoveContainer.add(new Move(x, y, afterX, afterY));
                                }

                            }
                        }



/*for (int i = 0; i < allPossibilities.size(); i++) {
                            if (checkmateCheck.size() != 0) {
                                if (!checkmateCheck.contains(allPossibilities.get(i))) {
                                    bestMoveContainer.set(0, new Move(x, y, allPossibilities.get(i).toX, allPossibilities.get(i).toY));
                                    return bestMoveContainer;
                                }
                            }

                        }*/

                        break;
                    }


                    case 'Q':
                    case 'q': {
                        int[] xCase = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
                        int[] yCase = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};

                        HashMap<MoveTo, Integer> checkHashMap = new HashMap<>();
                        for (int i = 0; i < 8; i++) {
                            int afterX = x + xCase[i];
                            int afterY = y + yCase[i];


                            while (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {
                                if (board[afterY][afterX] >= opponentLeft && board[afterY][afterX] <= opponentRight) {

                                    // 공격적 경우의 수 중
                                    if (board[afterY][afterX] == 'k' + chooser) {
                                        if (priority < 197) {
                                            priority = 197;
                                            bestMove = mapW.get('k');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (board[afterY][afterX] == 'q' + chooser) {
                                        if (priority < 97) {
                                            priority = 97;
                                            bestMove = mapW.get('q');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    possibleMoves.add(new Move(x, y, afterX, afterY));
                                    break;
                                }

                                if (board[afterY][afterX] >= left && board[afterY][afterX] <= right) {
                                    break;
                                }

                                // possibleMoves.add(new Move(x, y, afterX, afterY));
                                ////////////////////////////////////////////////

                                {
                                    for (int j = 0; j < 8; j++) {
                                        int afterX2 = afterX + xCase[j];
                                        int afterY2 = afterY + yCase[j];

                                        while (afterX2 >= lessMin && afterX2 < lessMax && afterY2 >= lessMin && afterY2 < lessMax) {
                                            if (board[afterY2][afterX2] >= opponentLeft && board[afterY2][afterX2] <= opponentRight) {

                                                MoveTo m = new MoveTo(afterX, afterY);
                                                if (!checkHashMap.containsKey(new MoveTo(afterX, afterY))) {
                                                    checkHashMap.put(m, 0);
                                                    possibleMoves.add(new Move(x, y, afterX, afterY));
                                                }

                                                break;
                                            }

                                            if (board[afterY2][afterX2] >= left && board[afterY2][afterX2] <= right) {
                                                break;
                                            }

                                            // possibleMoves.add(new Move(x, y, afterX, afterY));

                                            afterX2 += xCase[j];
                                            afterY2 += yCase[j];
                                        }
                                    }
                                }

                                ///////////////////////////////////////////////////////
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

                        HashMap<MoveTo, Integer> checkHashMap = new HashMap<>();

                        for (int i = 0; i < xCase.length; i++) {
                            int afterX = x + xCase[i];
                            int afterY = y + yCase[i];

                            if (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {

                                while (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {

                                    if (board[afterY][afterX] >= opponentLeft && board[afterY][afterX] <= opponentRight) {

                                        // 공격적 경우의 수 중
                                        if (board[afterY][afterX] == 'k' + chooser) {
                                            if (priority < 198) {
                                                priority = 198;
                                                bestMove = mapW.get('k');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (board[afterY][afterX] == 'q' + chooser) {
                                            if (bestMove < mapW.get('q') && priority < 98) {
                                                priority = 98;
                                                bestMove = mapW.get('q');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }

                                        }

                                        if (board[afterY][afterX] == 'r' + chooser) {
                                            if (priority < 48) {
                                                priority = 48;
                                                bestMove = mapW.get('r');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        possibleMoves.add(new Move(x, y, afterX, afterY));
                                        break;
                                    }

                                    if (board[afterY][afterX] >= left && board[afterY][afterX] <= right) {
                                        break;
                                    }

                                    // possibleMoves.add(new Move(x, y, afterX, afterY));

                                    ////////////////////////////////////////////////

                                    {
                                        for (int j = 0; j < xCase.length; j++) {
                                            int afterX2 = afterX + xCase[j];
                                            int afterY2 = afterY + yCase[j];

                                            while (afterX2 >= lessMin && afterX2 < lessMax && afterY2 >= lessMin && afterY2 < lessMax) {
                                                if (board[afterY2][afterX2] >= opponentLeft && board[afterY2][afterX2] <= opponentRight) {

                                                    MoveTo m = new MoveTo(afterX, afterY);
                                                    if (!checkHashMap.containsKey(new MoveTo(afterX, afterY))) {
                                                        checkHashMap.put(m, 0);
                                                        possibleMoves.add(new Move(x, y, afterX, afterY));
                                                    }

                                                    break;
                                                }

                                                if (board[afterY2][afterX2] >= left && board[afterY2][afterX2] <= right) {
                                                    break;
                                                }

                                                // possibleMoves.add(new Move(x, y, afterX, afterY));

                                                afterX2 += xCase[j];
                                                afterY2 += yCase[j];
                                            }
                                        }
                                    }

                                    ///////////////////////////////////////////////////////

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
                                        if (board[afterY][afterX] == 'k' + chooser) {
                                            if (priority < 199) {
                                                priority = 199;
                                                bestMove = mapW.get('k');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (board[afterY][afterX] == 'q' + chooser) {
                                            if (bestMove < mapW.get('q') && priority < 99) {
                                                priority = 99;
                                                bestMove = mapW.get('q');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (board[afterY][afterX] == 'r' + chooser) {
                                            if (bestMove < mapW.get('r') && priority < 49) {
                                                priority = 49;
                                                bestMove = mapW.get('r');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (board[afterY][afterX] == 'b' + chooser || board[afterY][afterX] == 'n' + chooser) {
                                            if (priority < 29) {
                                                priority = 29;
                                                bestMove = mapW.get('b');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        possibleMoves.add(new Move(x, y, afterX, afterY));
                                        break;
                                    }

                                    if (board[afterY][afterX] >= left && board[afterY][afterX] <= right) {
                                        break;
                                    }

                                    // possibleMoves.add(new Move(x, y, afterX, afterY));

                                    ////////////////////////////////////////////////

                                    {
                                        for (int j = 0; j < xCase.length; j++) {
                                            int afterX2 = afterX + xCase[j];
                                            int afterY2 = afterY + yCase[j];

                                            while (afterX2 >= lessMin && afterX2 < lessMax && afterY2 >= lessMin && afterY2 < lessMax) {
                                                if (board[afterY2][afterX2] >= opponentLeft && board[afterY2][afterX2] <= opponentRight) {

                                                    MoveTo m = new MoveTo(afterX, afterY);
                                                    if (!checkHashMap.containsKey(new MoveTo(afterX, afterY))) {
                                                        checkHashMap.put(m, 0);
                                                        possibleMoves.add(new Move(x, y, afterX, afterY));
                                                    }

                                                    break;
                                                }

                                                if (board[afterY2][afterX2] >= left && board[afterY2][afterX2] <= right) {
                                                    break;
                                                }

                                                // possibleMoves.add(new Move(x, y, afterX, afterY));

                                                afterX2 += xCase[j];
                                                afterY2 += yCase[j];
                                            }
                                        }
                                    }

                                    ///////////////////////////////////////////////////////

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

                                    // 공격적 경우의 수 중
                                    if (board[afterY][afterX] == 'k' + chooser) {
                                        if (priority < 199) {
                                            priority = 199;
                                            bestMove = mapW.get('k');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (board[afterY][afterX] == 'q' + chooser) {
                                        if (bestMove < mapW.get('q') && priority < 99) {
                                            priority = 99;
                                            bestMove = mapW.get('q');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (board[afterY][afterX] == 'r' + chooser) {
                                        if (bestMove < mapW.get('r') && priority < 49) {
                                            priority = 49;
                                            bestMove = mapW.get('r');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (board[afterY][afterX] == 'b' + chooser || board[afterY][afterX] == 'n' + chooser) {
                                        if (priority < 29) {
                                            priority = 29;
                                            bestMove = mapW.get('b');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

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

        if (possibleMoves.size() == 0) {
            return possibleMovesForKing;
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


    private int minMax(char[][] board, char color, int depth, int check) {
        if (depth == 0) { // 종료 조건
            return evaluateBoard(board, getOpponentColor(color));
        }

        ArrayList<Move> possibleMoves = getPossibleMovesFromPosition(board, color, -1, -1); // 현재 플레이어의 가능한 수 모두 생성
        int bestScore;

        if (check == 1) {
            bestScore = -INFINITY;
        } else {
            bestScore = INFINITY;
        }


        for (Move move : possibleMoves) {
            char[][] newBoard = applyMove(board, move); // 현재 수를 적용한 새로운 보드 생성
            int score = -minMax(newBoard, getOpponentColor(color), depth - 1, check * -1); // 새로운 보드에 대해 미니맥스 재귀호출
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

    // 특정 수를 적용한 새로운 보드를 반환합니다.
    private char[][] applyMove(char[][] board, Move move) {
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


//////////////////////////////////////////////////////////////////////////////////////////////////////

/*
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
        //return getNextMove(board, new Move(3, 6, 3, 4));

        // return new Move(3, 6, 3, 4);

        return getNextMove(board, null);
    }

    @Override
    public Move getNextMove(char[][] board, Move opponentMove) {

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

            int score = minMax(newBoard, color, 1);

            // 가장 높은 점수를 가진 수를 선택합니다.
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }

            if (score == bestScore) {
                sameMoves.add(move);
            }
        }

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
                if (board[row][col] == color) {
                    ArrayList<Move> movesFromCurrentPosition = getPossibleMovesFromPosition(board, color, row, col);
                    possibleMoves.addAll(movesFromCurrentPosition);
                }
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
            }
        }

        return score;
    }

    // Minimax 알고리즘을 사용하여 최선의 수를 선택합니다.
    private int minMax(char[][] board, char color, int depth) {
        if (depth == 0) { // 종료 조건
            return evaluateBoard(board, color);
        }

        ArrayList<Move> possibleMoves = getPossibleMoves(board, color); // 현재 플레이어의 가능한 수 모두 생성
        int bestScore = Integer.MIN_VALUE;

        for (Move move : possibleMoves) {
            char[][] newBoard = applyMove(board, move); // 현재 수를 적용한 새로운 보드 생성
            int score = -minMax(newBoard, getOpponentColor(color), depth - 1); // 새로운 보드에 대해 미니맥스 재귀호출
            bestScore = Math.max(bestScore, score); // 최적의 점수 갱신
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

}*/
