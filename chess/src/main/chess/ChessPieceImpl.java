package chess;

import java.util.Collection;

public class ChessPieceImpl implements ChessPiece{

    //////////////////////////////////////////////////////////////////
    //This class represents a single chess piece, with its          //
    // corresponding type and team color. There are 6 different     //
    // types of pieces: King, Queen, Bishop, Knight, Rook, and Pawn //
    //////////////////////////////////////////////////////////////////

    @Override
    public void updateTeamColor(ChessGame.TeamColor teamColor) {

    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return null;
    }

    @Override
    public PieceType getPieceType() {
        return null;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        /////////////////////////////////////////////////////////////////////////////////////
        //This method is similar to ChessGame::validMoves , except it does not check the   //
        // turn or Check (king safety) constraints. This method does account for enemy and //
        // friendly pieces blocking movement paths. Each of the 6 different implementations//
        // of this class will need a unique pieceMoves method to calculate valid moves for //
        // that type of piece. See later. Also implement the extra credit here.            //
        /////////////////////////////////////////////////////////////////////////////////////

        return null;
    }
}
