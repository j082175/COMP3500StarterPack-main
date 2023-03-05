package academy.pocu.comp3500.assignment3;

public class ChessBoard {
    private long boardStatus;

    private long whitePawns; // 흰색 폰의 위치를 저장하는 배열
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
    private long blackKing;

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
        boardStatus = 0L;
        boolean isChecked = false;

        for (int y = board.length - 1; y >= 0; y--) {
            for (int x = board.length - 1; x >= 0; x--) {

                switch (board[y][x]) {
                    case 'p':
                        isChecked = true;
                        whitePawns |= offset;
                        break;
                    case 'n':
                        isChecked = true;
                        whiteKnights |= offset;
                        break;
                    case 'b':
                        isChecked = true;
                        whiteBishops |= offset;
                        break;
                    case 'r':
                        isChecked = true;
                        whiteRooks |= offset;
                        break;
                    case 'q':
                        isChecked = true;
                        whiteQueens |= offset;
                        break;
                    case 'k':
                        isChecked = true;
                        whiteKing |= offset;
                        break;
                    case 'P':
                        isChecked = true;
                        blackPawns |= offset;
                        break;
                    case 'N':
                        isChecked = true;
                        blackKnights |= offset;
                        break;
                    case 'B':
                        isChecked = true;
                        blackBishops |= offset;
                        break;
                    case 'R':
                        isChecked = true;
                        blackRooks |= offset;
                        break;
                    case 'Q':
                        isChecked = true;
                        blackQueens |= offset;
                        break;
                    case 'K':
                        isChecked = true;
                        blackKing |= offset;
                        break;
                }

                if (isChecked) {
                    boardStatus |= offset;
                    isChecked = false;
                }

                offset <<= 1;
            }
        }
    }

    public void initialize() {
        // 초기 상태 설정  0, 1, 2, 3, 4, 5, 6, 7행
        whitePawns = 0b0000000000000000000000000000000000000000000000001111111100000000L; // a2, b2, ..., h2
        whitePawnMoves = (whitePawns << 8) & ~0xffffffffffffff00L; // a3, b3, ..., h3
    }

    public boolean isCollision(long pieceMoves, long allPieces) {
        return (pieceMoves & allPieces) != 0;
    }

    private void allInitialize() {

    }
}
