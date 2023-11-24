package chess;

import java.util.Collection;
import java.util.HashSet;

public class King implements ChessPiece{

    private boolean moved = false;

    private ChessPiece.PieceType myType = PieceType.KING;
    private ChessGame.TeamColor myTeam;

    @Override
    public void updateTeamColor(ChessGame.TeamColor teamColor) {
        myTeam = teamColor;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return myTeam;
    }

    @Override
    public PieceType getPieceType() {
        return myType;
    }





    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        HashSet<ChessMove> allMoves = new HashSet<>();
        ChessPositionImpl newPositionImp = new ChessPositionImpl(myPosition.getRow(), myPosition.getColumn());


        int row = myPosition.getRow() - 1;
        int col = myPosition.getColumn() -1;
        ChessPositionImpl endPosition = new ChessPositionImpl(0,0);
        ChessMove newMove = new ChessMoveImpl(newPositionImp, endPosition, null);




        //North
        if (row + 1 != 8) {
            if (board.getboard()[row+1][col] == null || board.getboard()[row+1][col].getTeamColor() != myTeam) {
                //Somthing is here
                endPosition = new ChessPositionImpl(row + 2, col + 1);
                newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                allMoves.add(newMove);
            }
        }

        //South
        if (row - 1 != -1) {
            if (board.getboard()[row-1][col] == null || board.getboard()[row-1][col].getTeamColor() != myTeam) {
                //Somthing is here
                endPosition = new ChessPositionImpl(row, col + 1);
                newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                allMoves.add(newMove);
            }
        }

        //East
        if (col - 1 != -1) {
            if (board.getboard()[row][col-1] == null || board.getboard()[row][col-1].getTeamColor() != myTeam) {
                //Somthing is here
                endPosition = new ChessPositionImpl(row+1, col);
                newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                allMoves.add(newMove);
            }
        }

        //West
        if (col + 1 != 8) {
            if (board.getboard()[row][col+1] == null || board.getboard()[row][col+1].getTeamColor() != myTeam) {
                //Somthing is here
                endPosition = new ChessPositionImpl(row + 1, col + 2);
                newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                allMoves.add(newMove);
            }
        }

        //NorthLeft
        if (row + 1 != 8 && col - 1 != -1) {
            if (board.getboard()[row+1][col-1] == null || board.getboard()[row+1][col-1].getTeamColor() != myTeam) {
                //Somthing is here
                endPosition = new ChessPositionImpl(row + 2, col);
                newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                allMoves.add(newMove);
            }
        }

        //NorthRight
        if (row + 1 != 8 && col + 1 != 8) {
            if (board.getboard()[row+1][col+1] == null || board.getboard()[row+1][col+1].getTeamColor() != myTeam) {
                //Somthing is here
                endPosition = new ChessPositionImpl(row + 2, col + 2);
                newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                allMoves.add(newMove);
            }
        }

        //SouthLeft
        if (row -1 != -1 && col - 1 != -1) {
            if (board.getboard()[row-1][col-1] == null || board.getboard()[row-1][col-1].getTeamColor() != myTeam) {
                //Somthing is here
                endPosition = new ChessPositionImpl(row, col);
                newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                allMoves.add(newMove);
            }
        }

        //SouthRight
        if (row -1 != -1 && col + 1 != 8) {
            if (board.getboard()[row-1][col+1] == null || board.getboard()[row-1][col+1].getTeamColor() != myTeam) {
                //Somthing is here
                endPosition = new ChessPositionImpl(row, col+2);
                newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                allMoves.add(newMove);
            }
        }



        return allMoves;
    }
}
