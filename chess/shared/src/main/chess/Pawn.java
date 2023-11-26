package chess;

import java.util.Collection;
import java.util.HashSet;

public class Pawn implements ChessPiece{


    private ChessPiece.PieceType myType = PieceType.PAWN;

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
        //Make set that stores all posible chess moves
        HashSet<ChessMove> allMoves = new HashSet<>();
        ChessPositionImpl newPositionImp = new ChessPositionImpl(myPosition.getRow(), myPosition.getColumn());



        //for each valid move, make a new chessMove object
        if(myTeam == ChessGame.TeamColor.WHITE) {
            //double on first turn
            if (myPosition.getRow() == 2) {
                if (board.getboard()[2][myPosition.getColumn()-1] == null && board.getboard()[3][myPosition.getColumn()-1] == null) {
                    ChessPositionImpl endPosition1 = new ChessPositionImpl(4, myPosition.getColumn());

                    ChessMove newMove1 = new ChessMoveImpl(newPositionImp, endPosition1, null);
                    allMoves.add(newMove1);
                }
            }
            //Moving Forward
            if (myPosition.getRow() != 8) {
                if (board.getboard()[myPosition.getRow()][myPosition.getColumn() - 1] == null) {
                    ChessPositionImpl endPosition2 = new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn());
                    if (endPosition2.getRow() == 8) {
                        ChessMove newMoveRook = new ChessMoveImpl(newPositionImp, endPosition2, PieceType.ROOK);
                        allMoves.add(newMoveRook);
                        ChessMove newMoveKnight = new ChessMoveImpl(newPositionImp, endPosition2, PieceType.KNIGHT);
                        allMoves.add(newMoveKnight);
                        ChessMove newMoveQueen = new ChessMoveImpl(newPositionImp, endPosition2, PieceType.QUEEN);
                        allMoves.add(newMoveQueen);
                        ChessMove newMoveBishop = new ChessMoveImpl(newPositionImp, endPosition2, PieceType.BISHOP);
                        allMoves.add(newMoveBishop);
                    } else {
                        ChessMove newMove2 = new ChessMoveImpl(newPositionImp, endPosition2, null);
                        allMoves.add(newMove2);
                    }
                }//If the statements below throw errors, add another if statment encapselating them making sure they are not gameboard[?][0] or [?][7] for them respectively

                //Going to left
                if (myPosition.getColumn() != 1) {
                    if (board.getboard()[myPosition.getRow()][myPosition.getColumn() - 2] != null) {
                        if (board.getboard()[myPosition.getRow()][myPosition.getColumn() - 2].getTeamColor() == ChessGame.TeamColor.BLACK) {
                            ChessPositionImpl endPosition3 = new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 1);
                            if (endPosition3.getRow() == 8) {
                                ChessMove newMoveRook = new ChessMoveImpl(newPositionImp, endPosition3, PieceType.ROOK);
                                allMoves.add(newMoveRook);
                                ChessMove newMoveKnight = new ChessMoveImpl(newPositionImp, endPosition3, PieceType.KNIGHT);
                                allMoves.add(newMoveKnight);
                                ChessMove newMoveQueen = new ChessMoveImpl(newPositionImp, endPosition3, PieceType.QUEEN);
                                allMoves.add(newMoveQueen);
                                ChessMove newMoveBishop = new ChessMoveImpl(newPositionImp, endPosition3, PieceType.BISHOP);
                                allMoves.add(newMoveBishop);
                            } else {
                                ChessMove newMove3 = new ChessMoveImpl(newPositionImp, endPosition3, null);
                                allMoves.add(newMove3);
                            }
                        }
                    }
                }
                if (myPosition.getColumn() < 8) {
                    if (board.getboard()[myPosition.getRow()][myPosition.getColumn()] != null) {
                        //Going to right
                        if (board.getboard()[myPosition.getRow()][myPosition.getColumn()].getTeamColor() == ChessGame.TeamColor.BLACK) {
                            ChessPositionImpl endPosition4 = new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 1);
                            if (endPosition4.getRow() == 8) {
                                ChessMove newMoveRook = new ChessMoveImpl(newPositionImp, endPosition4, PieceType.ROOK);
                                allMoves.add(newMoveRook);
                                ChessMove newMoveKnight = new ChessMoveImpl(newPositionImp, endPosition4, PieceType.KNIGHT);
                                allMoves.add(newMoveKnight);
                                ChessMove newMoveQueen = new ChessMoveImpl(newPositionImp, endPosition4, PieceType.QUEEN);
                                allMoves.add(newMoveQueen);
                                ChessMove newMoveBishop = new ChessMoveImpl(newPositionImp, endPosition4, PieceType.BISHOP);
                                allMoves.add(newMoveBishop);
                            } else {
                                ChessMove newMove4 = new ChessMoveImpl(newPositionImp, endPosition4, null);
                                allMoves.add(newMove4);
                            }
                        }
                    }
                }

            }
        }
        else {
            //Team is black
            if (myPosition.getRow() == 7) {
                if (board.getboard()[5][myPosition.getColumn()-1] == null && board.getboard()[4][myPosition.getColumn()-1] == null) {
                    ChessPositionImpl endPosition1 = new ChessPositionImpl(5, myPosition.getColumn());

                    ChessMove newMove1 = new ChessMoveImpl(newPositionImp, endPosition1, null);
                    allMoves.add(newMove1);
                }
            }
            //Moving Forward
            if (myPosition.getRow() != 1) {
                if (board.getboard()[myPosition.getRow() - 2][myPosition.getColumn() - 1] == null) {
                    ChessPositionImpl endPosition2 = new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn());
                    if (endPosition2.getRow() == 1) {
                        ChessMove newMoveRook = new ChessMoveImpl(newPositionImp, endPosition2, PieceType.ROOK);
                        allMoves.add(newMoveRook);
                        ChessMove newMoveKnight = new ChessMoveImpl(newPositionImp, endPosition2, PieceType.KNIGHT);
                        allMoves.add(newMoveKnight);
                        ChessMove newMoveQueen = new ChessMoveImpl(newPositionImp, endPosition2, PieceType.QUEEN);
                        allMoves.add(newMoveQueen);
                        ChessMove newMoveBishop = new ChessMoveImpl(newPositionImp, endPosition2, PieceType.BISHOP);
                        allMoves.add(newMoveBishop);
                    } else {
                        ChessMove newMove2 = new ChessMoveImpl(newPositionImp, endPosition2, null);
                        allMoves.add(newMove2);
                    }
                }//If the statements below throw errors, add another if statment encapselating them making sure they are not gameboard[?][0] or [?][7] for them respectively

                //Going to left
                if (myPosition.getColumn() > 1) {
                    if (board.getboard()[myPosition.getRow() - 2][myPosition.getColumn() - 2] != null) {
                        if (board.getboard()[myPosition.getRow() - 2][myPosition.getColumn() - 2].getTeamColor() == ChessGame.TeamColor.WHITE) {
                            ChessPositionImpl endPosition3 = new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() - 1);
                            if (endPosition3.getRow() == 1) {
                                ChessMove newMoveRook = new ChessMoveImpl(newPositionImp, endPosition3, PieceType.ROOK);
                                allMoves.add(newMoveRook);
                                ChessMove newMoveKnight = new ChessMoveImpl(newPositionImp, endPosition3, PieceType.KNIGHT);
                                allMoves.add(newMoveKnight);
                                ChessMove newMoveQueen = new ChessMoveImpl(newPositionImp, endPosition3, PieceType.QUEEN);
                                allMoves.add(newMoveQueen);
                                ChessMove newMoveBishop = new ChessMoveImpl(newPositionImp, endPosition3, PieceType.BISHOP);
                                allMoves.add(newMoveBishop);
                            } else {
                                ChessMove newMove3 = new ChessMoveImpl(newPositionImp, endPosition3, null);
                                allMoves.add(newMove3);
                            }
                        }
                    }
                }
                //Going to right
                if (myPosition.getColumn() < 8) {
                    if (board.getboard()[myPosition.getRow() - 2][myPosition.getColumn()] != null) {
                        if (board.getboard()[myPosition.getRow() - 2][myPosition.getColumn()].getTeamColor() == ChessGame.TeamColor.WHITE) {
                            ChessPositionImpl endPosition4 = new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() + 1);
                            if (endPosition4.getRow() == 1) {
                                ChessMove newMoveRook = new ChessMoveImpl(newPositionImp, endPosition4, PieceType.ROOK);
                                allMoves.add(newMoveRook);
                                ChessMove newMoveKnight = new ChessMoveImpl(newPositionImp, endPosition4, PieceType.KNIGHT);
                                allMoves.add(newMoveKnight);
                                ChessMove newMoveQueen = new ChessMoveImpl(newPositionImp, endPosition4, PieceType.QUEEN);
                                allMoves.add(newMoveQueen);
                                ChessMove newMoveBishop = new ChessMoveImpl(newPositionImp, endPosition4, PieceType.BISHOP);
                                allMoves.add(newMoveBishop);
                            } else {
                                ChessMove newMove4 = new ChessMoveImpl(newPositionImp, endPosition4, null);
                                allMoves.add(newMove4);
                            }
                        }
                    }
                }
            }
        }

        return allMoves;
    }

}
