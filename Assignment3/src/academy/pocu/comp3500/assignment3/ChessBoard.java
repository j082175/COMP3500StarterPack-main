package academy.pocu.comp3500.assignment3;

public class ChessBoard {
    private long boardStatus;
    private long[] pieces = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public enum ChessPiece {
        whitePawns('p', 1),
        whiteKnights('n', 3),
        whiteBishops('b', 3),
        whiteRooks('r', 5),
        whiteQueens('q', 9),
        whiteKing('k', 200),
        blackPawns('P', -1),
        blackKnights('N', -3),
        blackBishops('B', -3),
        blackRooks('R', -5),
        blackQueens('Q', -9),
        blackKing('K', -200);

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

    public void boardInitializer(char[][] board) {
        long offset = 0b0000000000000000000000000000000000000000000000000000000000000001L;
        boolean isChecked = false;

        for (int y = board.length - 1; y >= 0; y--) {
            for (int x = board.length - 1; x >= 0; x--) {

                switch (board[y][x]) {
                    case 'p':
                        isChecked = true;
                        pieces[ChessPiece.whitePawns.ordinal()] |= offset;
                        break;
                    case 'n':
                        isChecked = true;
                        pieces[ChessPiece.whiteKnights.ordinal()] |= offset;
                        break;
                    case 'b':
                        isChecked = true;
                        pieces[ChessPiece.whiteBishops.ordinal()] |= offset;
                        break;
                    case 'r':
                        isChecked = true;
                        pieces[ChessPiece.whiteRooks.ordinal()] |= offset;
                        break;
                    case 'q':
                        isChecked = true;
                        pieces[ChessPiece.whiteQueens.ordinal()] |= offset;
                        break;
                    case 'k':
                        isChecked = true;
                        pieces[ChessPiece.whiteKing.ordinal()] |= offset;
                        break;
                    case 'P':
                        isChecked = true;
                        pieces[ChessPiece.blackPawns.ordinal()] |= offset;
                        break;
                    case 'N':
                        isChecked = true;
                        pieces[ChessPiece.blackKnights.ordinal()] |= offset;
                        break;
                    case 'B':
                        isChecked = true;
                        pieces[ChessPiece.blackBishops.ordinal()] |= offset;
                        break;
                    case 'R':
                        isChecked = true;
                        pieces[ChessPiece.blackRooks.ordinal()] |= offset;
                        break;
                    case 'Q':
                        isChecked = true;
                        pieces[ChessPiece.blackQueens.ordinal()] |= offset;
                        break;
                    case 'K':
                        isChecked = true;
                        pieces[ChessPiece.blackKing.ordinal()] |= offset;
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

    public void setPieces(long[] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            this.pieces[i] = pieces[i];
        }
    }

    public void setPieces(long WP, long WN, long WB, long WR, long WQ, long WK, long BP, long BN, long BB, long BR, long BQ, long BK, long boardStatus) {
        this.pieces[ChessPiece.whitePawns.ordinal()] = WP;
        this.pieces[ChessPiece.whiteKnights.ordinal()] = WN;
        this.pieces[ChessPiece.whiteBishops.ordinal()] = WB;
        this.pieces[ChessPiece.whiteRooks.ordinal()] = WR;
        this.pieces[ChessPiece.whiteQueens.ordinal()] = WQ;
        this.pieces[ChessPiece.whiteKing.ordinal()] = WK;
        this.pieces[ChessPiece.blackPawns.ordinal()] = BP;
        this.pieces[ChessPiece.blackKnights.ordinal()] = BN;
        this.pieces[ChessPiece.blackBishops.ordinal()] = BB;
        this.pieces[ChessPiece.blackRooks.ordinal()] = BR;
        this.pieces[ChessPiece.blackQueens.ordinal()] = BQ;
        this.pieces[ChessPiece.blackKing.ordinal()] = BK;
        this.boardStatus = boardStatus;
    }

    public long getBoardStatus() {
        return this.boardStatus;
    }

    public void setBoardStatus(long boardStatus) {
        this.boardStatus = boardStatus;
    }
}
