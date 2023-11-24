package chess;

import java.util.Collection;
import java.util.HashSet;

public class Knight implements ChessPiece{


    private ChessPiece.PieceType myType = PieceType.KNIGHT;
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




        //1 up 2 right
        if (row + 1 != 8 && col + 2 < 8) {
            if (board.getboard()[row+1][col+2] != null) {
                if (board.getboard()[row+1][col+2].getTeamColor() != myTeam) {
                    //Something is here
                    endPosition = new ChessPositionImpl(row + 2, col + 3);
                    newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                    allMoves.add(newMove);
                }
            }
            else {
                endPosition = new ChessPositionImpl(row + 2, col+3);
                newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                allMoves.add(newMove);
            }
        }

        //1 up 2 left
        if (row + 1 != 8 && col >=2) {

            if (board.getboard()[row+1][col-2] != null) {
                //Somthing is here
                if (board.getboard()[row+1][col-2].getTeamColor() != myTeam) {
                    endPosition = new ChessPositionImpl(row + 2, col - 1);
                    newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                    allMoves.add(newMove);
                }
            }
            else {
                endPosition = new ChessPositionImpl(row + 2, col -1);
                newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                allMoves.add(newMove);
            }
        }

        //2 up 1 right
        if (row + 2 < 8 && col + 1 != 8) {
            if (board.getboard()[row+2][col+1] != null) {
                //Somthing is here
                if(board.getboard()[row+2][col+1].getTeamColor() != myTeam) {
                    endPosition = new ChessPositionImpl(row + 3, col + 2);
                    newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                    allMoves.add(newMove);
                }
            }
            else {
                endPosition = new ChessPositionImpl(row + 3, col + 2);
                newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                allMoves.add(newMove);
            }
        }

        //2 up 1 left
        if (row + 2 < 8 && col - 1 != -1) {
            if (board.getboard()[row+2][col-1] != null) {
                if (board.getboard()[row+2][col-1].getTeamColor() != myTeam) {
                    //Somthing is here
                    endPosition = new ChessPositionImpl(row + 3, col);
                    newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                    allMoves.add(newMove);
                }

            }
            else {
                //Somthing is here
                endPosition = new ChessPositionImpl(row + 3, col);
                newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                allMoves.add(newMove);
            }
        }


        //1 down 2 right
        if (row - 1 != -1 && col + 2 < 8) {
            if (board.getboard()[row-1][col+2] != null) {
                if (board.getboard()[row-1][col+2].getTeamColor() != myTeam) {
                    //Somthing is here
                    endPosition = new ChessPositionImpl(row, col + 3);
                    newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                    allMoves.add(newMove);
                }
            }
            else {
                endPosition = new ChessPositionImpl(row, col + 3);
                newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                allMoves.add(newMove);
            }
        }

        //2 down 1 right
        if (row -2 > -1 && col + 1 != 8) {
            if (board.getboard()[row-2][col+1] != null) {
                if (board.getboard()[row-2][col+1].getTeamColor() != myTeam) {
                    //Somthing is here
                    endPosition = new ChessPositionImpl(row - 1, col + 2);
                    newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                    allMoves.add(newMove);
                }
            }
            else {
                endPosition = new ChessPositionImpl(row - 1, col + 2);
                newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                allMoves.add(newMove);
            }
        }

        //2 down 1 left
        if (row -2 > -1 && col - 1 > -1) {
            if (board.getboard()[row-2][col-1] != null) {
                if (board.getboard()[row-2][col-1].getTeamColor() != myTeam) {
                    //Somthing is here
                    endPosition = new ChessPositionImpl(row - 1, col);
                    newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                    allMoves.add(newMove);
                }
            }
            else {
                endPosition = new ChessPositionImpl(row - 1, col);
                newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                allMoves.add(newMove);
            }
        }

        //1 down 2 left
        if (row - 1 > -1 && col >= 2) {
            if (board.getboard()[row-1][col-2] != null) {
                if (board.getboard()[row-1][col-2].getTeamColor() != myTeam) {
                    //Somthing is here
                    endPosition = new ChessPositionImpl(row, col - 1);
                    newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                    allMoves.add(newMove);
                }
            }
            else {
                endPosition = new ChessPositionImpl(row, col - 1);
                newMove = new ChessMoveImpl(newPositionImp, endPosition, null);
                allMoves.add(newMove);
            }
        }

        return allMoves;

    }
        //Collection could return hash set tree set or list

}
