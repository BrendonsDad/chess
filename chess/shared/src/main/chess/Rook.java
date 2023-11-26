package chess;

import java.util.Collection;
import java.util.HashSet;

public class Rook implements ChessPiece{

    private boolean moved = false;

    private ChessPiece.PieceType myType = PieceType.ROOK;

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
        while (row < 8) {
            //check if in bounds
            if (row + 1 == 8) {
                //outofbounds
                break;
            }
            if (board.getboard()[row + 1][col] != null) {
                //There is something in this spot
                if (board.getboard()[row + 1][col].getTeamColor() == myTeam) {
                    //hitteamate
                    break;
                }
                else {
                    endPosition = new ChessPositionImpl(row + 2, col + 1);
                    newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                    allMoves.add(newMove);
                    //capturedop
                    break;
                }
            }
            else {
                //There is nothing in the spot
                endPosition = new ChessPositionImpl(row + 2, col + 1);
                newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                allMoves.add(newMove);
                row+=1;
            }
        }

        row = myPosition.getRow() - 1;
        col = myPosition.getColumn() -1;


        //South

        while (row > -1) {
            //check if in bounds
            if (row - 1 == -1) {
                //outofbounds
                break;
            }
            if (board.getboard()[row - 1][col] != null) {
                //There is something in this spot
                if (board.getboard()[row - 1][col].getTeamColor() == myTeam) {
                    //hitteamate
                    break;
                }
                else {
                    endPosition = new ChessPositionImpl(row, col + 1);
                    newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                    allMoves.add(newMove);
                    //capturedop
                    break;
                }
            }
            else {
                //There is nothing in the spot
                endPosition = new ChessPositionImpl(row, col + 1);
                newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                allMoves.add(newMove);
                row-=1;
            }
        }

        row = myPosition.getRow() - 1;
        col = myPosition.getColumn() -1;

        //East

        while (col > -1) {
            //check if in bounds
            if (col - 1 == -1) {
                //outofbounds
                break;
            }
            if (board.getboard()[row][col - 1] != null) {
                //There is something in this spot
                if (board.getboard()[row][col - 1].getTeamColor() == myTeam) {
                    //hitteamate
                    break;
                }
                else {
                    endPosition = new ChessPositionImpl(row + 1, col);
                    newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                    allMoves.add(newMove);
                    //capturedop
                    break;
                }
            }
            else {
                //There is nothing in the spot
                endPosition = new ChessPositionImpl(row + 1, col);
                newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                allMoves.add(newMove);
                col-=1;
            }
        }
        row = myPosition.getRow() - 1;
        col = myPosition.getColumn() -1;

        //West
        while (col < 8) {
            //check if in bounds
            if (col + 1 == 8) {
                //outofbounds
                break;
            }
            if (board.getboard()[row][col + 1] != null) {
                //There is something in this spot
                if (board.getboard()[row][col + 1].getTeamColor() == myTeam) {
                    //hitteamate
                    break;
                }
                else {
                    endPosition = new ChessPositionImpl(row + 1, col + 2);
                    newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                    allMoves.add(newMove);
                    //capturedop
                    break;
                }
            }
            else {
                //There is nothing in the spot
                endPosition = new ChessPositionImpl(row + 1, col + 2);
                newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                allMoves.add(newMove);
                col+=1;
            }
        }

        row = myPosition.getRow() - 1;
        col = myPosition.getColumn() -1;




        return allMoves;
    }
}
