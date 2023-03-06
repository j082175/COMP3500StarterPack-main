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
        int bestScore2 = -INFINITY;

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

            int s = minMax(chessBoardBackup, getOpponentColor(color), 1, -1, minValue, pieces[0], pieces[1], pieces[2], pieces[3], pieces[4], pieces[5], pieces[6], pieces[7], pieces[8], pieces[9], pieces[10], pieces[11], chessBoardBackup.getBoardStatus());

            chessBoardBackup.setPieces(chessBoard.getPieces());
            chessBoardBackup.setBoardStatus(chessBoard.getBoardStatus());
            applyMove(chessBoardBackup, move);
            pieces = chessBoardBackup.getPieces();

            int score = minMax(chessBoardBackup, getOpponentColor(color), 3, -1, minValue, pieces[0], pieces[1], pieces[2], pieces[3], pieces[4], pieces[5], pieces[6], pieces[7], pieces[8], pieces[9], pieces[10], pieces[11], chessBoardBackup.getBoardStatus());

            // 가장 높은 점수를 가진 수를 선택합니다.
            int value = score > s ? score : s;
            int value2 = score < s ? score : s;

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

    private ArrayList<Move> getPossibleMovesFromPosition(ChessBoard chessBoard, char color, int row, int col, int l) {
        ArrayList<Move> possibleMoves = new ArrayList<>(100);

        long result;

        int opponentLeft = getOpponentColorToAscii(color);
        int opponentRight = opponentLeft + 25;

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                char ch = checkPiece(chessBoard, y, x);
                if (ch >= opponentLeft && ch <= opponentRight || ch == 0) {
                    continue;
                }
                int square = ((7 - y) * 8) + (7 - x);
                switch (ch) {
                    case 'p':
                    case 'P':
                        result = getPawnMoves(square, chessBoard.getBoardStatus(), color);
                        calculateDecimalFromPowersOfTwo(result, y, x, possibleMoves);
                        break;
                    case 'n':
                    case 'N':
                        result = getKnightMoves(square, chessBoard.getBoardStatus());
                        calculateDecimalFromPowersOfTwo(result, y, x, possibleMoves);
                        break;
                    case 'b':
                    case 'B':
                        result = getBishopMoves(square, chessBoard.getBoardStatus());
                        calculateDecimalFromPowersOfTwo(result, y, x, possibleMoves);
                        break;
                    case 'r':
                    case 'R':
                        result = getRookMoves(square, chessBoard.getBoardStatus());
                        calculateDecimalFromPowersOfTwo(result, y, x, possibleMoves);
                        break;
                    case 'q':
                    case 'Q':
                        result = getQueenMoves(square, chessBoard.getBoardStatus());
                        calculateDecimalFromPowersOfTwo(result, y, x, possibleMoves);
                        break;
                    case 'k':
                    case 'K':
                        result = getKingMoves(square, chessBoard.getBoardStatus());
                        calculateDecimalFromPowersOfTwo(result, y, x, possibleMoves);
                        break;
                }
            }
        }

        return possibleMoves;
    }


    // 현재 위치에서 가능한 모든 수를 반환하는 함수
    private ArrayList<Move> getPossibleMovesFromPosition(ChessBoard chessBoard, char color, int row, int col) {
        ArrayList<Move> possibleMoves = new ArrayList<>(100);



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
/*                            if (checkPiece(chessBoard, afterY, afterXleft) == 'k' + chooser) {
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
                            }*/

                            possibleMoves.add(new Move(x, y, afterXleft, afterY));
                        }

                        boolean initialMoveRightCheck = afterXright < lessMax && afterY >= lessMin && afterY < lessMax;

                        if (initialMoveRightCheck && checkPiece(chessBoard, afterY, afterXright) >= opponentLeft && checkPiece(chessBoard, afterY, afterXright) <= opponentRight) {

                            // 공격적 경우의 수 중
                            /*if (checkPiece(chessBoard, afterY, afterXright) == 'k' + chooser) {

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
                                if (checkPiece(chessBoard, afterY, afterX) >= opponentLeft && checkPiece(chessBoard, afterY, afterX) <= opponentRight) {
                                    // 공격적 경우의 수 중
                                    /*if (checkPiece(chessBoard, afterY, afterX) == 'k' + chooser) {
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
                                    }*/

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
                                    /*if (checkPiece(chessBoard, afterY, afterX) == 'k' + chooser) {
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
                                    }*/

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
                                        /*if (checkPiece(chessBoard, afterY, afterX) == 'k' + chooser) {
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
                                        }*/

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
                                        /*if (checkPiece(chessBoard, afterY, afterX) == 'k' + chooser) {
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
                                        }*/

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
                                    /*if (checkPiece(chessBoard, afterY, afterX) == 'k' + chooser) {
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
                                    }*/

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


    private int minMax(ChessBoard chessBoard, char color, int depth, int check, int[] minValue, long WP, long WN,
                       long WB, long WR, long WQ, long WK, long BP, long BN, long BB, long BR, long BQ, long BK, long boardStatus) {
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

            long[] p = chessBoard.getPieces();
            int score = -minMax(chessBoard, getOpponentColor(color), depth - 1, check * -1, minValue, p[0], p[1], p[2], p[3], p[4], p[5], p[6], p[7], p[8], p[9], p[10], p[11], chessBoard.getBoardStatus()); // 새로운 보드에 대해 미니맥스 재귀호출

            chessBoard.setPieces(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK, boardStatus);

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

    public static long getQueenMoves(int square, long occupied) {
        long attacks = getBishopMoves(square, occupied) | getRookMoves(square, occupied);
        return attacks;
    }

    public static long getRookMoves(int square, long occupied) {
        long bitboard = 0L;
        int rank = square / 8;
        int file = square % 8;
        int i;

        // Moves in up direction
        for (i = rank + 1; i <= 7; i++) {
            if ((occupied & (1L << (i * 8 + file))) != 0L) break;
            bitboard |= (1L << (i * 8 + file));
        }

        // Moves in down direction
        for (i = rank - 1; i >= 0; i--) {
            if ((occupied & (1L << (i * 8 + file))) != 0L) break;
            bitboard |= (1L << (i * 8 + file));
        }

        // Moves in right direction
        for (i = file + 1; i <= 7; i++) {
            if ((occupied & (1L << (rank * 8 + i))) != 0L) break;
            bitboard |= (1L << (rank * 8 + i));
        }

        // Moves in left direction
        for (i = file - 1; i >= 0; i--) {
            if ((occupied & (1L << (rank * 8 + i))) != 0L) break;
            bitboard |= (1L << (rank * 8 + i));
        }

        return bitboard;
    }

    public static long getKnightMoves(int square, long occupied) {
        long bitboard = 0L;
        int rank = square / 8;
        int file = square % 8;


        int rankOffset = rank + 2;
        int fileOffset = file + 1;

        if (rankOffset <= 7 && fileOffset <= 7) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)){
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            };
        }

        fileOffset = file - 1;

        if (rankOffset <= 7 && file - 1 >= 0) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)){
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            };
        }

        rankOffset = rank + 1;
        fileOffset = file + 2;

        if (rankOffset <= 7 && file + 2 <= 7) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)){
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            };
        }

        fileOffset = file - 2;

        if (rankOffset <= 7 && file - 2 >= 0) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)){
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            };
        }

        rankOffset = rank - 1;
        fileOffset = file + 2;

        if (rankOffset >= 0 && file + 2 <= 7) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)){
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            };
        }

        fileOffset = file - 2;

        if (rankOffset >= 0 && file - 2 >= 0) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)){
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            };
        }

        rankOffset = rank - 2;
        fileOffset = file + 1;

        if (rankOffset >= 0 && fileOffset <= 7) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)){
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            };
        }

        fileOffset = file - 1;

        if (rankOffset >= 0 && file - 1 >= 0) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)){
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            };
        }

        return bitboard;
    }

    public static long getBishopMoves(int square, long occupied) {
        long bitboard = 0L;
        int rank = square / 8;
        int file = square % 8;
        int i, j;

        // Moves in up-right direction
        for (i = rank + 1, j = file + 1; i <= 7 && j <= 7; i++, j++) {
            if ((occupied & (1L << (i * 8 + j))) != 0L) break;
            bitboard |= (1L << (i * 8 + j));
        }

        // Moves in up-left direction
        for (i = rank + 1, j = file - 1; i <= 7 && j >= 0; i++, j--) {
            if ((occupied & (1L << (i * 8 + j))) != 0L) break;
            bitboard |= (1L << (i * 8 + j));
        }

        // Moves in down-right direction
        for (i = rank - 1, j = file + 1; i >= 0 && j <= 7; i--, j++) {
            if ((occupied & (1L << (i * 8 + j))) != 0L) break;
            bitboard |= (1L << (i * 8 + j));
        }

        // Moves in down-left direction
        for (i = rank - 1, j = file - 1; i >= 0 && j >= 0; i--, j--) {
            if ((occupied & (1L << (i * 8 + j))) != 0L) break;
            bitboard |= (1L << (i * 8 + j));
        }

        return bitboard;
    }

    public static long getKingMoves(int square, long occupied) {
        long bitboard = 0L;
        int rank = square / 8;
        int file = square % 8;

        int rankOffset = rank + 1;
        int fileOffset = file;

        if (rank + 1 <= 7) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)){
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            }
        }

        rankOffset = rank - 1;
        fileOffset = file;

        if (rank - 1 >= 0) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)){
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            }
        }

        rankOffset = rank;
        fileOffset = file + 1;

        if (file + 1 <= 7) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)){
                bitboard |= (1L << (rankOffset * 8 + fileOffset));
            }
        }

        rankOffset = rank;
        fileOffset = file - 1;

        if (file - 1 >= 0) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)){
                bitboard |= (1L << (rankOffset * 8 + fileOffset));
            }
        }

        rankOffset = rank + 1;
        fileOffset = file + 1;

        if (rank + 1 <= 7 && file + 1 <= 7) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)){
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            }
        }

        rankOffset = rank - 1;
        fileOffset = file + 1;

        if (rank - 1 >= 0 && file + 1 <= 7) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)){
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            }
        }

        rankOffset = rank + 1;
        fileOffset = file - 1;

        if (rank + 1 <= 7 && file - 1 >= 0) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)){
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            }
        }

        rankOffset = rank - 1;
        fileOffset = file - 1;

        if (rank - 1 >= 0 && file - 1 >= 0) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)){
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            }
        }

        return bitboard;
    }

    public static long getPawnMoves(int square, long occupied, char color) {
        long mask, moves = 0L;
        int rank = square / 8;
        int file = square % 8;

        // White pawn moves
        if (color == 'W') {
            if (rank < 7) {
                mask = 1L << (square + 8);
                if ((occupied & mask) == 0) {
                    moves |= mask;
                    if (rank == 1) {
                        mask = 1L << (square + 16);
                        if ((occupied & mask) == 0) {
                            moves |= mask;
                        }
                    }
                }
                if (file > 0) {
                    mask = 1L << (square + 7);
                    if ((occupied & mask) != 0) {
                        moves |= mask;
                    }
                }
                if (file < 7) {
                    mask = 1L << (square + 9);
                    if ((occupied & mask) != 0) {
                        moves |= mask;
                    }
                }
            }
        }


        // Black pawn moves
        if (color == 'B') {
            if (rank > 0) {
                mask = 1L << (square - 8);
                if ((occupied & mask) == 0) {
                    moves |= mask;
                    if (rank == 6) {
                        mask = 1L << (square - 16);
                        if ((occupied & mask) == 0) {
                            moves |= mask;
                        }
                    }
                }
                if (file > 0) {
                    mask = 1L << (square - 9);
                    if ((occupied & mask) != 0) {
                        moves |= mask;
                    }
                }
                if (file < 7) {
                    mask = 1L << (square - 7);
                    if ((occupied & mask) != 0) {
                        moves |= mask;
                    }
                }
            }
        }


        return moves;
    }

    public static void calculateDecimalFromPowersOfTwo(long decimal, int fromY, int fromX, ArrayList<Move> arrayList) {
        // 십진수를 2진수로 변환
        String binaryString = Long.toBinaryString(decimal);

        // 2진수에서 2의 제곱수들을 찾아서 합을 계산
        int sum = 0;
        int i;
        for (i = 0; i < binaryString.length(); i++) {
            if (binaryString.charAt(binaryString.length() - 1 - i) == '1') {
                sum += Math.pow(2, i);
                int toX = 7 - i % 8;
                int toY = 7 - i / 8;
                arrayList.add(new Move(fromX, fromY, toX, toY));
            }
        }

    }


}
