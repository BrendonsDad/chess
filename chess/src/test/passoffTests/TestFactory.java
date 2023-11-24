package passoffTests;

import chess.*;

/**
 * Used for testing your code
 * Add in code using your classes for each method for each FIXME
 */
public class TestFactory {

    //Chess Functions
    //------------------------------------------------------------------------------------------------------------------
    public static ChessBoard getNewBoard(){
        // FIXME

		return new ChessBoardImpl();
    }

    public static ChessGame getNewGame(){
        // FIXME
		return new ChessGameImpl();
    }

    public static ChessPiece getNewPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type){
        // FIXME
        if (type == ChessPiece.PieceType.KING) {
            ChessPiece myKing = new King();
            myKing.updateTeamColor(pieceColor);
            return myKing;
        }
        if (type == ChessPiece.PieceType.QUEEN) {

            ChessPiece myQueen = new Queen();
            myQueen.updateTeamColor(pieceColor);
            return myQueen;
        }
        if (type == ChessPiece.PieceType.KNIGHT) {
            ChessPiece myKnight = new Knight();
            myKnight.updateTeamColor(pieceColor);
            return myKnight;
        }
        if (type == ChessPiece.PieceType.ROOK) {
            ChessPiece myRook = new Rook();
            myRook.updateTeamColor(pieceColor);
            return myRook;
        }
        if (type == ChessPiece.PieceType.PAWN) {
            ChessPiece myPawn = new Pawn();
            myPawn.updateTeamColor(pieceColor);
            return myPawn;
        }
        if (type == ChessPiece.PieceType.BISHOP) {
            ChessPiece myBishop = new Bishop();
            myBishop.updateTeamColor(pieceColor);
            return myBishop;
        }

    return null;
    }

    public static ChessPositionImpl getNewPosition(Integer row, Integer col){
        // FIXME
		return new ChessPositionImpl(row, col);
    }

    public static ChessMove getNewMove(ChessPositionImpl startPosition, ChessPositionImpl endPosition, ChessPiece.PieceType promotionPiece){
        // FIXME
		return new ChessMoveImpl(startPosition, endPosition, promotionPiece);
    }
    //------------------------------------------------------------------------------------------------------------------


    //Server API's
    //------------------------------------------------------------------------------------------------------------------
    public static String getServerPort(){
        return "8080";
    }
    //------------------------------------------------------------------------------------------------------------------


    //Websocket Tests
    //------------------------------------------------------------------------------------------------------------------
    public static Long getMessageTime(){
        /*
        Changing this will change how long tests will wait for the server to send messages.
        3000 Milliseconds (3 seconds) will be enough for most computers. Feel free to change as you see fit,
        just know increasing it can make tests take longer to run.
        (On the flip side, if you've got a good computer feel free to decrease it)
         */
        return 3000L;
    }
    //------------------------------------------------------------------------------------------------------------------
}
