package chess;

public class ChessBoardImpl implements ChessBoard{

    ///////////////////////////////////////////////////////////////
    //This class stores all the un-captured pieces in a Game. It //
    // needs to support adding and removing pieces for testing,   //
    // as well as a resetBoard() method that sets the standard   //
    // Chess starting configuration.                             //
    ///////////////////////////////////////////////////////////////

    public ChessPiece[][] GameBoard = new ChessPiece[8][8];


    @Override
    public ChessPiece[][] getboard() {
        return GameBoard;
    }

    @Override
    public void addPiece(ChessPositionImpl position, ChessPiece piece) {
        GameBoard[position.getRow() -1][position.getColumn() -1] = piece;


    }

    @Override
    public ChessPiece getPiece(ChessPositionImpl position) {
        return GameBoard[position.getRow()-1][position.getColumn()-1];
    }

    @Override
    public void resetBoard() {
        //Clear the board
        GameBoard = new ChessPiece[8][8];

        //Make the white team//
        //Pawns
        Pawn WPawn1 = new Pawn();
        WPawn1.updateTeamColor(ChessGame.TeamColor.WHITE);
        GameBoard[1][0] = WPawn1;

        Pawn WPawn2 = new Pawn();
        WPawn2.updateTeamColor(ChessGame.TeamColor.WHITE);
        GameBoard[1][1] = WPawn2;

        Pawn WPawn3 = new Pawn();
        WPawn3.updateTeamColor(ChessGame.TeamColor.WHITE);
        GameBoard[1][2] = WPawn3;

        Pawn WPawn4 = new Pawn();
        WPawn4.updateTeamColor(ChessGame.TeamColor.WHITE);
        GameBoard[1][3] = WPawn4;

        Pawn WPawn5 = new Pawn();
        WPawn5.updateTeamColor(ChessGame.TeamColor.WHITE);
        GameBoard[1][4] = WPawn5;

        Pawn WPawn6 = new Pawn();
        WPawn6.updateTeamColor(ChessGame.TeamColor.WHITE);
        GameBoard[1][5] = WPawn6;

        Pawn WPawn7 = new Pawn();
        WPawn7.updateTeamColor(ChessGame.TeamColor.WHITE);
        GameBoard[1][6] = WPawn7;

        Pawn WPawn8 = new Pawn();
        WPawn8.updateTeamColor(ChessGame.TeamColor.WHITE);
        GameBoard[1][7] = WPawn8;

        //Rooks
        Rook WRookLeft = new Rook();
        WRookLeft.updateTeamColor(ChessGame.TeamColor.WHITE);
        GameBoard[0][0] = WRookLeft;

        Rook WRookRight = new Rook();
        WRookRight.updateTeamColor(ChessGame.TeamColor.WHITE);
        GameBoard[0][7] = WRookRight;

        //Knights
        Knight WKnightLeft = new Knight();
        WKnightLeft.updateTeamColor(ChessGame.TeamColor.WHITE);
        GameBoard[0][1] = WKnightLeft;

        Knight WKnightRight = new Knight();
        WKnightRight.updateTeamColor(ChessGame.TeamColor.WHITE);
        GameBoard[0][6] = WKnightRight;

        //Bishops
        Bishop WBishopLeft = new Bishop();
        WBishopLeft.updateTeamColor(ChessGame.TeamColor.WHITE);
        GameBoard[0][2] = WBishopLeft;

        Bishop WBishopRight = new Bishop();
        WBishopRight.updateTeamColor(ChessGame.TeamColor.WHITE);
        GameBoard[0][5] = WBishopRight;

        //Queen
        Queen WQueen = new Queen();
        WQueen.updateTeamColor(ChessGame.TeamColor.WHITE);
        GameBoard[0][3] = WQueen;

        //King
        King WKing = new King();
        WKing.updateTeamColor(ChessGame.TeamColor.WHITE);
        GameBoard[0][4] = WKing;



        //Make the Black team//
        //Pawns
        Pawn BPawn1 = new Pawn();
        BPawn1.updateTeamColor(ChessGame.TeamColor.BLACK);
        GameBoard[6][0] = BPawn1;

        Pawn BPawn2 = new Pawn();
        BPawn2.updateTeamColor(ChessGame.TeamColor.BLACK);
        GameBoard[6][1] = BPawn2;

        Pawn BPawn3 = new Pawn();
        BPawn3.updateTeamColor(ChessGame.TeamColor.BLACK);
        GameBoard[6][2] = BPawn3;

        Pawn BPawn4 = new Pawn();
        BPawn4.updateTeamColor(ChessGame.TeamColor.BLACK);
        GameBoard[6][3] = BPawn4;

        Pawn BPawn5 = new Pawn();
        BPawn5.updateTeamColor(ChessGame.TeamColor.BLACK);
        GameBoard[6][4] = BPawn5;

        Pawn BPawn6 = new Pawn();
        BPawn6.updateTeamColor(ChessGame.TeamColor.BLACK);
        GameBoard[6][5] = BPawn6;

        Pawn BPawn7 = new Pawn();
        BPawn7.updateTeamColor(ChessGame.TeamColor.BLACK);
        GameBoard[6][6] = BPawn7;

        Pawn BPawn8 = new Pawn();
        BPawn8.updateTeamColor(ChessGame.TeamColor.BLACK);
        GameBoard[6][7] = BPawn8;

        //Rooks
        Rook BRookLeft = new Rook();
        BRookLeft.updateTeamColor(ChessGame.TeamColor.BLACK);
        GameBoard[7][0] = BRookLeft;

        Rook BRookRight = new Rook();
        BRookRight.updateTeamColor(ChessGame.TeamColor.BLACK);
        GameBoard[7][7] = BRookRight;

        //Knights
        Knight BKnightLeft = new Knight();
        BKnightLeft.updateTeamColor(ChessGame.TeamColor.BLACK);
        GameBoard[7][1] = BKnightLeft;

        Knight BKnightRight = new Knight();
        BKnightRight.updateTeamColor(ChessGame.TeamColor.BLACK);
        GameBoard[7][6] = BKnightRight;

        //Bishops
        Bishop BBishopLeft = new Bishop();
        BBishopLeft.updateTeamColor(ChessGame.TeamColor.BLACK);
        GameBoard[7][2] = BBishopLeft;

        Bishop BBishopRight = new Bishop();
        BBishopRight.updateTeamColor(ChessGame.TeamColor.BLACK);
        GameBoard[7][5] = BBishopRight;

        //Queen
        Queen BQueen = new Queen();
        BQueen.updateTeamColor(ChessGame.TeamColor.BLACK);
        GameBoard[7][3] = BQueen;

        //King
        King BKing = new King();
        BKing.updateTeamColor(ChessGame.TeamColor.BLACK);
        GameBoard[7][4] = BKing;

    }
}
