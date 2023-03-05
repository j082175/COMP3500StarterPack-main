package academy.pocu.comp3500.assignment3;

import academy.pocu.comp3500.assignment3.chess.Move;
import academy.pocu.comp3500.assignment3.chess.PlayerBase;

import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.zip.CheckedOutputStream;

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

        ChessBoard chessBoard = new ChessBoard();
        chessBoard.boardInitializer(board);

        // 현재 상태에서 가능한 모든 수를 구합니다.
        // ArrayList<Move> possibleMoves = getPossibleMoves(board, color);
        ArrayList<Move> possibleMoves = getPossibleMovesFromPosition(chessBoard, color, -1, -1);

        ArrayList<Move> sameMoves = new ArrayList<>();

        ChessBoard chessBoardBackup = new ChessBoard();

        // 가능한 모든 수에 대해 상대방이 다음 수로 이동할 수 있는 모든 가능성에 대한 점수를 구합니다.
        for (Move move : possibleMoves) {
            chessBoardBackup.setPieces(chessBoard.getPieces());
            chessBoardBackup.setBoardStatus(chessBoard.getBoardStatus());

            applyMove(chessBoardBackup, move);

            long[] pieces = chessBoardBackup.getPieces();

            int[] minValue = {INFINITY};
            int score = minMax(chessBoardBackup, getOpponentColor(color), 3, -1, minValue, pieces[0], pieces[1], pieces[2], pieces[3], pieces[4], pieces[5], pieces[6], pieces[7], pieces[8], pieces[9], pieces[10], pieces[11], chessBoardBackup.getBoardStatus());

            // 가장 높은 점수를 가진 수를 선택합니다.
            if (score != INFINITY && score > bestScore && score == minValue[0]) {
                bestScore = score;
                bestMove = move;
                sameMoves.clear();
            }

            if (score == bestScore && minValue[0] == bestScore) {
                sameMoves.add(move);
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

/*    private ArrayList<Move> getPossibleMoves(char[][] board, char color) {
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
    }*/


    // 현재 위치에서 가능한 모든 수를 반환하는 함수
    private ArrayList<Move> getPossibleMovesFromPosition(ChessBoard chessBoard, char color, int row, int col) {


        ArrayList<Move> possibleMoves = new ArrayList<>();
        int priority = -1;
        ArrayList<Move> bestMoveContainer = new ArrayList<>();
        int bestMove = -1;
        bestMoveContainer.add(null);

        int left;
        int right;
        int chooser; // 소문자를 기준으로 할거임

        long pawns; // 흰색 폰의 위치를 저장하는 배열
        long knights;
        long bishops;
        long rooks;
        long queens;
        long king;

        long[] pieces = chessBoard.getPieces();

        if (color == 'W') {
            left = 97;
            right = 122;
            chooser = -32;
            pawns = pieces[ChessBoard.ChessPiece.whitePawns.ordinal()];
            knights = pieces[ChessBoard.ChessPiece.whiteKnights.ordinal()];
            bishops = pieces[ChessBoard.ChessPiece.whiteBishops.ordinal()];
            rooks = pieces[ChessBoard.ChessPiece.whiteRooks.ordinal()];
            queens = pieces[ChessBoard.ChessPiece.whiteQueens.ordinal()];
            king = pieces[ChessBoard.ChessPiece.whiteKing.ordinal()];
        } else {
            left = 65;
            right = 90;
            chooser = 0;
            pawns = pieces[ChessBoard.ChessPiece.blackPawns.ordinal()];
            knights = pieces[ChessBoard.ChessPiece.blackKnights.ordinal()];
            bishops = pieces[ChessBoard.ChessPiece.blackBishops.ordinal()];
            rooks = pieces[ChessBoard.ChessPiece.blackRooks.ordinal()];
            queens = pieces[ChessBoard.ChessPiece.blackQueens.ordinal()];
            king = pieces[ChessBoard.ChessPiece.blackKing.ordinal()];
        }

        int lessMin = 0;
        int lessMax = 8;

        int opponentLeft = getOpponentColorToAscii(color);
        int opponentRight = opponentLeft + 25;




        for (int y = lessMin; y < lessMax; y++) {
            for (int x = lessMin; x < lessMax; x++) {
                long[] pieceBinary = new long[1];
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

                        if (initialMoveLeftCheck && checkPiece(chessBoard, afterY, afterXleft) >= opponentLeft && checkPiece(chessBoard, afterY, afterXleft) <= opponentRight) {

                            // 공격적 경우의 수 중
                            if (checkPiece(chessBoard, afterY, afterXleft) == 'k' + chooser) {
                                priority = 200;
                                //bestMove.set(0, mapW.get('k'));
                                bestMove = mapW.get('k');
                                bestMoveContainer.set(0, new Move(x, y, afterXleft, afterY));
                            }

                            if (checkPiece(chessBoard, afterY, afterXleft) == 'q' + chooser) {
                                if (bestMove < mapW.get('q') && priority < 100) {
                                    priority = 100;
                                    //bestMove.set(0, mapW.get('q'));
                                    bestMove = mapW.get('q');
                                    bestMoveContainer.set(0, new Move(x, y, afterXleft, afterY));
                                }
                            }

                            if (checkPiece(chessBoard, afterY, afterXleft) == 'r' + chooser) {
                                if (bestMove < mapW.get('r') && priority < 50) {
                                    priority = 50;
                                    bestMove = mapW.get('r');
                                    bestMoveContainer.set(0, new Move(x, y, afterXleft, afterY));
                                }
                            }

                            if (checkPiece(chessBoard, afterY, afterXleft) == 'b' + chooser || checkPiece(chessBoard, afterY, afterXleft) == 'n' + chooser) {
                                if (bestMove < mapW.get('b') && priority < 30) {
                                    priority = 30;
                                    bestMove = mapW.get('b');
                                    bestMoveContainer.set(0, new Move(x, y, afterXleft, afterY));
                                }
                            }

                            if (checkPiece(chessBoard, afterY, afterXleft) == 'p' + chooser) {
                                if (priority < 10) {
                                    priority = 10;
                                    bestMove = mapW.get('p');
                                    bestMoveContainer.set(0, new Move(x, y, afterXleft, afterY));
                                }
                            }

                            possibleMoves.add(new Move(x, y, afterXleft, afterY));
                        }

                        boolean initialMoveRightCheck = afterXright < lessMax && afterY >= lessMin && afterY < lessMax;

                        if (initialMoveRightCheck && checkPiece(chessBoard, afterY, afterXright) >= opponentLeft && checkPiece(chessBoard, afterY, afterXright) <= opponentRight) {

                            // 공격적 경우의 수 중
                            if (checkPiece(chessBoard, afterY, afterXright) == 'k' + chooser) {

                                priority = 200;
                                bestMove = mapW.get('k');
                                bestMoveContainer.set(0, new Move(x, y, afterXright, afterY));
                                break;
                            }

                            if (checkPiece(chessBoard, afterY, afterXright) == 'q' + chooser) {
                                if (bestMove < mapW.get('q') && priority < 100) {
                                    priority = 100;
                                    bestMove = mapW.get('q');
                                    bestMoveContainer.set(0, new Move(x, y, afterXright, afterY));
                                    break;
                                }
                            }

                            if (checkPiece(chessBoard, afterY, afterXright) == 'r' + chooser) {
                                if (bestMove < mapW.get('r') && priority < 50) {
                                    priority = 50;
                                    bestMove = mapW.get('r');
                                    bestMoveContainer.set(0, new Move(x, y, afterXright, afterY));
                                    break;
                                }
                            }

                            if (checkPiece(chessBoard, afterY, afterXright) == 'b' + chooser || checkPiece(chessBoard, afterY, afterXright) == 'n' + chooser) {
                                if (bestMove < mapW.get('b') && priority < 30) {
                                    priority = 30;
                                    bestMove = mapW.get('b');
                                    bestMoveContainer.set(0, new Move(x, y, afterXright, afterY));
                                    break;
                                }
                            }

                            if (checkPiece(chessBoard, afterY, afterXright) == 'p' + chooser) {
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

                            if (afterX >= lessMin && afterX < lessMax && afterY >= lessMin && afterY < lessMax) {
                                if (checkPiece(chessBoard, afterY, afterX) >= opponentLeft && checkPiece(chessBoard, afterY, afterX) <= opponentRight) {
                                    // 공격적 경우의 수 중
                                    if (checkPiece(chessBoard, afterY, afterX) == 'k' + chooser) {
                                        if (priority < 196) {
                                            priority = 196;
                                            bestMove = mapW.get('k');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (checkPiece(chessBoard, afterY, afterX) == 'q' + chooser) {
                                        if (priority < 96) {
                                            priority = 96;
                                            bestMove = mapW.get('q');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (checkPiece(chessBoard, afterY, afterX) == 'r' + chooser) {
                                        if (priority < 46) {
                                            priority = 46;
                                            bestMove = mapW.get('r');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (checkPiece(chessBoard, afterY, afterX) == 'b' + chooser || checkPiece(chessBoard, afterY, afterX) == 'n' + chooser) {
                                        if (priority < 26) {
                                            priority = 26;
                                            bestMove = mapW.get('b');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (checkPiece(chessBoard, afterY, afterX) == 'p' + chooser) {
                                        if (priority < 6) {
                                            priority = 6;
                                            bestMove = mapW.get('p');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    possibleMoves.add(new Move(x, y, afterX, afterY));
                                }

                                if (checkPiece(chessBoard, afterY, afterX) == 0) {
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
                                if (checkPiece(chessBoard, afterY, afterX) >= opponentLeft && checkPiece(chessBoard, afterY, afterX) <= opponentRight) {

                                    // 공격적 경우의 수 중
                                    if (checkPiece(chessBoard, afterY, afterX) == 'k' + chooser) {
                                        if (priority < 197) {
                                            priority = 197;
                                            bestMove = mapW.get('k');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (checkPiece(chessBoard, afterY, afterX) == 'q' + chooser) {
                                        if (priority < 97) {
                                            priority = 97;
                                            bestMove = mapW.get('q');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (checkPiece(chessBoard, afterY, afterX) == 'r' + chooser) {
                                        if (priority < 47) {
                                            priority = 47;
                                            bestMove = mapW.get('r');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (checkPiece(chessBoard, afterY, afterX) == 'b' + chooser || checkPiece(chessBoard, afterY, afterX) == 'n' + chooser) {
                                        if (priority < 27) {
                                            priority = 27;
                                            bestMove = mapW.get('b');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (checkPiece(chessBoard, afterY, afterX) == 'p' + chooser) {
                                        if (priority < 7) {
                                            priority = 7;
                                            bestMove = mapW.get('p');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    possibleMoves.add(new Move(x, y, afterX, afterY));
                                    break;
                                }

                                if (checkPiece(chessBoard, afterY, afterX) >= left && checkPiece(chessBoard, afterY, afterX) <= right) {
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

                                    if (checkPiece(chessBoard, afterY, afterX) >= opponentLeft && checkPiece(chessBoard, afterY, afterX) <= opponentRight) {

                                        // 공격적 경우의 수 중
                                        if (checkPiece(chessBoard, afterY, afterX) == 'k' + chooser) {
                                            if (priority < 198) {
                                                priority = 198;
                                                bestMove = mapW.get('k');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (checkPiece(chessBoard, afterY, afterX) == 'q' + chooser) {
                                            if (bestMove < mapW.get('q') && priority < 98) {
                                                priority = 98;
                                                bestMove = mapW.get('q');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }

                                        }

                                        if (checkPiece(chessBoard, afterY, afterX) == 'r' + chooser) {
                                            if (priority < 48) {
                                                priority = 48;
                                                bestMove = mapW.get('r');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (checkPiece(chessBoard, afterY, afterX) == 'b' + chooser || checkPiece(chessBoard, afterY, afterX) == 'n' + chooser) {
                                            if (priority < 28) {
                                                priority = 28;
                                                bestMove = mapW.get('b');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (checkPiece(chessBoard, afterY, afterX) == 'p' + chooser) {
                                            if (priority < 8) {
                                                priority = 8;
                                                bestMove = mapW.get('p');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        possibleMoves.add(new Move(x, y, afterX, afterY));
                                        break;
                                    }

                                    if (checkPiece(chessBoard, afterY, afterX) >= left && checkPiece(chessBoard, afterY, afterX) <= right) {
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

                                    if (checkPiece(chessBoard, afterY, afterX) >= opponentLeft && checkPiece(chessBoard, afterY, afterX) <= opponentRight) {

                                        // 공격적 경우의 수 중
                                        if (checkPiece(chessBoard, afterY, afterX) == 'k' + chooser) {
                                            if (priority < 199) {
                                                priority = 199;
                                                bestMove = mapW.get('k');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (checkPiece(chessBoard, afterY, afterX) == 'q' + chooser) {
                                            if (bestMove < mapW.get('q') && priority < 99) {
                                                priority = 99;
                                                bestMove = mapW.get('q');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (checkPiece(chessBoard, afterY, afterX) == 'r' + chooser) {
                                            if (bestMove < mapW.get('r') && priority < 49) {
                                                priority = 49;
                                                bestMove = mapW.get('r');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (checkPiece(chessBoard, afterY, afterX) == 'b' + chooser || checkPiece(chessBoard, afterY, afterX) == 'n' + chooser) {
                                            if (priority < 29) {
                                                priority = 29;
                                                bestMove = mapW.get('b');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        if (checkPiece(chessBoard, afterY, afterX) == 'p' + chooser) {
                                            if (bestMove < mapW.get('p') && priority < 9) {
                                                priority = 9;
                                                bestMove = mapW.get('p');
                                                bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                            }
                                        }

                                        possibleMoves.add(new Move(x, y, afterX, afterY));
                                        break;
                                    }

                                    if (checkPiece(chessBoard, afterY, afterX) >= left && checkPiece(chessBoard, afterY, afterX) <= right) {
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
                                if ((checkPiece(chessBoard, afterY, afterX) >= opponentLeft && checkPiece(chessBoard, afterY, afterX) <= opponentRight)) {

                                    // 공격적 경우의 수 중
                                    if (checkPiece(chessBoard, afterY, afterX) == 'k' + chooser) {
                                        if (priority < 199) {
                                            priority = 199;
                                            bestMove = mapW.get('k');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (checkPiece(chessBoard, afterY, afterX) == 'q' + chooser) {
                                        if (bestMove < mapW.get('q') && priority < 99) {
                                            priority = 99;
                                            bestMove = mapW.get('q');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (checkPiece(chessBoard, afterY, afterX) == 'r' + chooser) {
                                        if (bestMove < mapW.get('r') && priority < 49) {
                                            priority = 49;
                                            bestMove = mapW.get('r');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (checkPiece(chessBoard, afterY, afterX) == 'b' + chooser || checkPiece(chessBoard, afterY, afterX) == 'n' + chooser) {
                                        if (priority < 29) {
                                            priority = 29;
                                            bestMove = mapW.get('b');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    if (checkPiece(chessBoard, afterY, afterX) == 'p' + chooser) {
                                        if (bestMove < mapW.get('p') && priority < 9) {
                                            priority = 9;
                                            bestMove = mapW.get('p');
                                            bestMoveContainer.set(0, new Move(x, y, afterX, afterY));
                                        }
                                    }

                                    possibleMoves.add(new Move(x, y, afterX, afterY));
                                }

                                if ((checkPiece(chessBoard, afterY, afterX) == 0)) {
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
            if (pieces[count] != 0 && (pieces[count] & boardStatus) == pieces[count]) {
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


    private int minMax(ChessBoard chessBoard, char color, int depth, int check, int[] minValue, long WP, long WN, long WB, long WR, long WQ, long WK, long BP, long BN, long BB, long BR, long BQ, long BK, long boardStatus) {
        chessBoard.setPieces(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK, boardStatus);

        if (depth == 0) { // 종료 조건
            return evaluateBoard(chessBoard, getOpponentColor(color));
        }

        ArrayList<Move> possibleMoves = getPossibleMovesFromPosition(chessBoard, color, -1, -1); // 현재 플레이어의 가능한 수 모두 생성
        int bestScore;

        if (check == 1) {
            bestScore = -INFINITY;
        } else {
            bestScore = INFINITY;
        }

        for (Move move : possibleMoves) {
            applyMove(chessBoard, move); // 현재 수를 적용한 새로운 보드 생성

            int score = -minMax(chessBoard, getOpponentColor(color), depth - 1, check * -1, minValue, WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK, boardStatus); // 새로운 보드에 대해 미니맥스 재귀호출

            minValue[0] = score;
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
}