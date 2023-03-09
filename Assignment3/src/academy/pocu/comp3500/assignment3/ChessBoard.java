package academy.pocu.comp3500.assignment3;

import academy.pocu.comp3500.assignment3.chess.Move;

import java.util.ArrayList;

public class ChessBoard {

    private static final long H_FILE = 0x8080808080808080L;
    private static final long A_FILE = 0x0101010101010101L;
    private static final long B_FILE = 0x0202020202020202L;


    private long boardStatus;
    public long whiteStatus;
    public long blackStatus;
    private long[] pieces = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public enum ChessPiece {
        WHITE_PAWNS('p', 1),
        WHITE_KNIGHTS('n', 3),
        WHITE_BISHOPS('b', 3),
        WHITE_ROOKS('r', 5),
        WHITE_QUEENS('q', 9),
        WHITE_KING('k', 200),
        BLACK_PAWNS('P', -1),
        BLACK_KNIGHTS('N', -3),
        BLACK_BISHOPS('B', -3),
        BLACK_ROOKS('R', -5),
        BLACK_QUEENS('Q', -9),
        BLACK_KING('K', -200);

        private char character;
        private int score;

        ChessPiece(char character, int score) {
            this.character = character;
            this.score = score;
        }

        public char getChar() {
            return this.character;
        }

        public int getScore() {
            return this.score;
        }
    }


/*    private long whitePawns; // 흰색 폰의 위치를 저장하는 배열
    private long whiteKnights;
    private long whiteBishops;
    private long whiteRooks;
    private long whiteQueens;
    private long whiteKing;

    private long blackPawns;
    private long blackKnights;
    private long blackBishops;
    private long blackRooks;
    private long blackQueens;
    private long blackKing;*/

    private long whitePawnMoves; // 흰색 폰의 이동 가능한 위치를 저장하는 배열

    public ChessBoard() {

/*        boardStatus = 0xffff00000000ffffL;

        whitePawns =   0b0000000000000000000000000000000000000000000000001111111100000000L; // a2, b2, ..., h2
        whiteKnights = 0b0000000000000000000000000000000000000000000000000000000001000010L;
        whiteBishops = 0b0000000000000000000000000000000000000000000000000000000000100100L;
        whiteRooks =   0b0000000000000000000000000000000000000000000000000000000010000001L;
        whiteQueens =  0b0000000000000000000000000000000000000000000000000000000000001000L;
        whiteKing =    0b0000000000000000000000000000000000000000000000000000000000010000L;

        blackPawns = 0b0000000000000000000000000000000000000000000000000000000000000000L;*/
    }

    public ChessBoard(ChessBoard other) {
        setPieces(other.getPieces());
        setBoardStatus(other.getBoardStatus());
        this.whiteStatus = other.whiteStatus;
        this.blackStatus = other.blackStatus;
    }

    public void boardInitializer(char[][] board) {
        long offset = 0b0000000000000000000000000000000000000000000000000000000000000001L;
        boolean isChecked = false;

        for (int y = board.length - 1; y >= 0; y--) {
            for (int x = board.length - 1; x >= 0; x--) {

                switch (board[y][x]) {
                    case 'p':
                        isChecked = true;
                        pieces[ChessPiece.WHITE_PAWNS.ordinal()] |= offset;
                        whiteStatus |= offset;
                        break;
                    case 'n':
                        isChecked = true;
                        pieces[ChessPiece.WHITE_KNIGHTS.ordinal()] |= offset;
                        whiteStatus |= offset;
                        break;
                    case 'b':
                        isChecked = true;
                        pieces[ChessPiece.WHITE_BISHOPS.ordinal()] |= offset;
                        whiteStatus |= offset;
                        break;
                    case 'r':
                        isChecked = true;
                        pieces[ChessPiece.WHITE_ROOKS.ordinal()] |= offset;
                        whiteStatus |= offset;
                        break;
                    case 'q':
                        isChecked = true;
                        pieces[ChessPiece.WHITE_QUEENS.ordinal()] |= offset;
                        whiteStatus |= offset;
                        break;
                    case 'k':
                        isChecked = true;
                        pieces[ChessPiece.WHITE_KING.ordinal()] |= offset;
                        whiteStatus |= offset;
                        break;
                    case 'P':
                        isChecked = true;
                        pieces[ChessPiece.BLACK_PAWNS.ordinal()] |= offset;
                        blackStatus |= offset;
                        break;
                    case 'N':
                        isChecked = true;
                        pieces[ChessPiece.BLACK_KNIGHTS.ordinal()] |= offset;
                        blackStatus |= offset;
                        break;
                    case 'B':
                        isChecked = true;
                        pieces[ChessPiece.BLACK_BISHOPS.ordinal()] |= offset;
                        blackStatus |= offset;
                        break;
                    case 'R':
                        isChecked = true;
                        pieces[ChessPiece.BLACK_ROOKS.ordinal()] |= offset;
                        blackStatus |= offset;
                        break;
                    case 'Q':
                        isChecked = true;
                        pieces[ChessPiece.BLACK_QUEENS.ordinal()] |= offset;
                        blackStatus |= offset;
                        break;
                    case 'K':
                        isChecked = true;
                        pieces[ChessPiece.BLACK_KING.ordinal()] |= offset;
                        blackStatus |= offset;
                        break;
                }

                if (isChecked) {
                    boardStatus |= offset;
                    // System.out.println(Long.toBinaryString(boardStatus));
                    isChecked = false;
                }

                offset <<= 1;
            }
        }
    }

    public void initialize() {
        // 초기 상태 설정  0, 1, 2, 3, 4, 5, 6, 7행
        //whitePawns = 0b0000000000000000000000000000000000000000000000001111111100000000L; // a2, b2, ..., h2
        //whitePawnMoves = (whitePawns << 8) & ~0xffffffffffffff00L; // a3, b3, ..., h3
    }

    public boolean isCollision(long pieceMoves, long allPieces) {
        return (pieceMoves & allPieces) != 0;
    }

    public long[] getPieces() {
        return this.pieces;
    }

    public long getPieces(int index) {
        return this.pieces[index];
    }

    public void setPieces(long[] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            this.pieces[i] = pieces[i];
        }
    }

    public void setPieces(long wp, long wn, long wb, long wr, long wq, long wk, long bp, long bn, long bb, long br, long bq, long bk, long boardStatus) {
        this.pieces[ChessPiece.WHITE_PAWNS.ordinal()] = wp;
        this.pieces[ChessPiece.WHITE_KNIGHTS.ordinal()] = wn;
        this.pieces[ChessPiece.WHITE_BISHOPS.ordinal()] = wb;
        this.pieces[ChessPiece.WHITE_ROOKS.ordinal()] = wr;
        this.pieces[ChessPiece.WHITE_QUEENS.ordinal()] = wq;
        this.pieces[ChessPiece.WHITE_KING.ordinal()] = wk;
        this.pieces[ChessPiece.BLACK_PAWNS.ordinal()] = bp;
        this.pieces[ChessPiece.BLACK_KNIGHTS.ordinal()] = bn;
        this.pieces[ChessPiece.BLACK_BISHOPS.ordinal()] = bb;
        this.pieces[ChessPiece.BLACK_ROOKS.ordinal()] = br;
        this.pieces[ChessPiece.BLACK_QUEENS.ordinal()] = bq;
        this.pieces[ChessPiece.BLACK_KING.ordinal()] = bk;
        this.boardStatus = boardStatus;
    }

    public long getBoardStatus() {
        return this.boardStatus;
    }

    public void setBoardStatus(long boardStatus) {
        this.boardStatus = boardStatus;
    }

    public long getQueenMoves(int square, long occupied) {
        long attacks = getBishopMoves(square, occupied, 1) | getRookMoves(square, occupied);
        return attacks;
    }

    public long getRookMoves(int square, long occupied) {
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

    public long getKnightMoves(int square, long occupied) {
        long bitboard = 0L;
        int rank = square / 8;
        int file = square % 8;


        int rankOffset = rank + 2;
        int fileOffset = file + 1;

        if (rankOffset <= 7 && fileOffset <= 7) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)) {
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            }
            ;
        }

        fileOffset = file - 1;

        if (rankOffset <= 7 && file - 1 >= 0) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)) {
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            }
            ;
        }

        rankOffset = rank + 1;
        fileOffset = file + 2;

        if (rankOffset <= 7 && file + 2 <= 7) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)) {
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            }
            ;
        }

        fileOffset = file - 2;

        if (rankOffset <= 7 && file - 2 >= 0) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)) {
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            }
            ;
        }

        rankOffset = rank - 1;
        fileOffset = file + 2;

        if (rankOffset >= 0 && file + 2 <= 7) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)) {
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            }
            ;
        }

        fileOffset = file - 2;

        if (rankOffset >= 0 && file - 2 >= 0) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)) {
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            }
            ;
        }

        rankOffset = rank - 2;
        fileOffset = file + 1;

        if (rankOffset >= 0 && fileOffset <= 7) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)) {
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            }
            ;
        }

        fileOffset = file - 1;

        if (rankOffset >= 0 && file - 1 >= 0) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)) {
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            }
            ;
        }

        return bitboard;
    }

    public long getBishopMoves(int square, long occupied, long board) {
        long bitboard = 0L;
        int rank = square / 8;
        int file = square % 8;
        int i;
        int j;

        // Moves in up-right direction
        for (i = rank + 1, j = file + 1; i <= 7 && j <= 7; i++, j++) {
            if ((occupied & (1L << (i * 8 + j))) != 0L) {
                if ((board & (1L << (i * 8 + j))) != 0L) {
                    bitboard = 0L;
                    bitboard |= (1L << (i * 8 + j));
                    return bitboard;
                }
            }
            bitboard |= (1L << (i * 8 + j));
        }

        // Moves in up-left direction
        for (i = rank + 1, j = file - 1; i <= 7 && j >= 0; i++, j--) {
            if ((occupied & (1L << (i * 8 + j))) != 0L) {
                if ((board & (1L << (i * 8 + j))) != 0L) {
                    bitboard = 0L;
                    bitboard |= (1L << (i * 8 + j));
                    return bitboard;
                }
            }
            bitboard |= (1L << (i * 8 + j));
        }

        // Moves in down-right direction
        for (i = rank - 1, j = file + 1; i >= 0 && j <= 7; i--, j++) {
            if ((occupied & (1L << (i * 8 + j))) != 0L) {
                if ((board & (1L << (i * 8 + j))) != 0L) {
                    bitboard = 0L;
                    bitboard |= (1L << (i * 8 + j));
                    return bitboard;
                }
            }
            bitboard |= (1L << (i * 8 + j));
        }

        // Moves in down-left direction
        for (i = rank - 1, j = file - 1; i >= 0 && j >= 0; i--, j--) {
            if ((occupied & (1L << (i * 8 + j))) != 0L) {
                if ((board & (1L << (i * 8 + j))) != 0L) {
                    bitboard = 0L;
                    bitboard |= (1L << (i * 8 + j));
                    return bitboard;
                }
            }
            bitboard |= (1L << (i * 8 + j));
        }

        return bitboard;
    }

    public long getKingMoves(int square, long occupied) {
        long bitboard = 0L;
        int rank = square / 8;
        int file = square % 8;

        int rankOffset = rank + 1;
        int fileOffset = file;

        if (rank + 1 <= 7) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)) {
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            }
        }

        rankOffset = rank - 1;
        fileOffset = file;

        if (rank - 1 >= 0) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)) {
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            }
        }

        rankOffset = rank;
        fileOffset = file + 1;

        if (file + 1 <= 7) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)) {
                bitboard |= (1L << (rankOffset * 8 + fileOffset));
            }
        }

        rankOffset = rank;
        fileOffset = file - 1;

        if (file - 1 >= 0) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)) {
                bitboard |= (1L << (rankOffset * 8 + fileOffset));
            }
        }

        rankOffset = rank + 1;
        fileOffset = file + 1;

        if (rank + 1 <= 7 && file + 1 <= 7) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)) {
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            }
        }

        rankOffset = rank - 1;
        fileOffset = file + 1;

        if (rank - 1 >= 0 && file + 1 <= 7) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)) {
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            }
        }

        rankOffset = rank + 1;
        fileOffset = file - 1;

        if (rank + 1 <= 7 && file - 1 >= 0) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)) {
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            }
        }

        rankOffset = rank - 1;
        fileOffset = file - 1;

        if (rank - 1 >= 0 && file - 1 >= 0) {
            if (!((occupied & (1L << ((rankOffset) * 8 + fileOffset))) != 0L)) {
                bitboard |= (1L << ((rankOffset) * 8 + fileOffset));
            }
        }

        return bitboard;
    }

    public long getPawnMoves(int square, long occupied, char color) {
        long mask = 0L;
        long moves = 0L;
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

    public void calculateDecimalFromPowersOfTwo(long decimal, int fromY, int fromX, ArrayList<Move> arrayList) {
        final long OFFSET = 0xFF;

        for (int i = 0; i < 8; i++) {
            long result = OFFSET & decimal;

            if (result != 0) {
                String binaryString = Long.toBinaryString(result);
                for (int j = 0; j < binaryString.length(); j++) {
                    if (binaryString.charAt(binaryString.length() - 1 - j) == '1') {
                        int toX = 7 - j;
                        int toY = 7 - i;
                        arrayList.add(new Move(fromX, fromY, toX, toY));
                    }
                }
            }
            decimal >>= 8;
        }
    }
}
