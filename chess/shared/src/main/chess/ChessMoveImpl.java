package chess;

import java.util.Objects;

public class ChessMoveImpl implements ChessMove{
    //add hashcode and equals and tostring

    /////////////////////////////////////////////////////////////////////
    // This class represents a possible move a chess piece could make. //
    // it contains the starting and ending positions. It also contains //
    // a field for the type of piece a pawn is being promoted to. If   //
    // the move does not result in a pawn being promoted, the promotion//
    // type field should be null.                                      //
    /////////////////////////////////////////////////////////////////////
    private ChessPositionImpl StartPosition;
    private ChessPositionImpl EndPosition;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessMoveImpl chessMove)) return false;
        return Objects.equals(StartPosition, chessMove.StartPosition) && Objects.equals(EndPosition, chessMove.EndPosition) && promotionPiece == chessMove.promotionPiece;
    }

    @Override
    public int hashCode() {
        return Objects.hash(StartPosition, EndPosition, promotionPiece);
    }

    public ChessPiece.PieceType promotionPiece;


    public ChessMoveImpl(ChessPositionImpl StartPositionI, ChessPositionImpl EndPositionI, ChessPiece.PieceType upgradeType) {
        this.StartPosition = StartPositionI;
        this.EndPosition = EndPositionI;
        promotionPiece = upgradeType;


    }

    @Override
    public ChessPositionImpl getStartPosition() {

        return this.StartPosition;
    }

    @Override
    public ChessPositionImpl getEndPosition() {

        return this.EndPosition;
    }

    @Override
    public ChessPiece.PieceType getPromotionPiece() {

        return promotionPiece;
    }
}
