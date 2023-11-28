package chess;

import java.util.Collection;
import java.util.HashSet;

public class ChessGameImpl implements ChessGame{
    ///////////////////////////////////////////////////////
    // Top-level management  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Executes moves as well as reporting the game status
    ///////////////////////////////////////////////////////


    private ChessBoard myGameBoard = new ChessBoardImpl();
    private TeamColor curTurn = TeamColor.WHITE;
    public ChessGameImpl() {
        myGameBoard.resetBoard();
    }

    @Override
    public TeamColor getTeamTurn() {
        return curTurn;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        curTurn = team;
    }

    @Override
    public Collection<ChessMove> validMoves(ChessPositionImpl startPosition) {

        /////////////////////////////////////////////////////////////
        //Takes as input a position on the chessboard and returns  //
        // all the moves the piece there can legally make. If      //
        // there is no piece at that location, this method         //
        // returns null                                            //
        /////////////////////////////////////////////////////////////

        // I will have this call the board that should have the peices in it
        // this way i can do all the move logic in the individual game pieces
        Collection<ChessMove> allMoves = new HashSet<>();
        Collection<ChessMove> allKosherMoves = new HashSet();
        if (startPosition.getRow() > 8 || startPosition.getRow() < 1 || startPosition.getColumn() > 8 || startPosition.getColumn() < 1) {
            return null;
        }

        if (myGameBoard.getboard()[startPosition.getRow()-1][startPosition.getColumn()-1] == null) {
            return null;
        }
        else {
            ChessPiece myPiece = myGameBoard.getboard()[startPosition.getRow() - 1][startPosition.getColumn() -1];
            //Get the color for later
            TeamColor myColor = myPiece.getTeamColor();
            allMoves = myPiece.pieceMoves(myGameBoard, startPosition);
            ChessBoard copyBoard = new ChessBoardImpl();
            ChessBoard originalBoard = new ChessBoardImpl();
            Collection<ChessMove> opMoves = new HashSet<>();


            for (ChessMove move : allMoves) {
                copyBoard = myGameBoard;
                originalBoard = myGameBoard;
                 ChessPositionImpl firstPos = move.getStartPosition();
                 ChessPositionImpl endPos = move.getEndPosition();
                 //make the variables to help us move the piece
                 int firstX = firstPos.getRow() - 1;
                 int firstY = firstPos.getColumn() -1;

                 int endX = endPos.getRow() -1;
                 int endY = endPos.getColumn() -1;

                 ChessPiece copyPiece = myPiece;
                copyBoard.getboard()[firstX][firstY] = null;
                //Make sure to delete anything there if it is an op
                ChessPiece oldEnd = myGameBoard.getboard()[endX][endY];
                copyBoard.getboard()[endX][endY] = null;
                //add our piece to the spot
                copyBoard.getboard()[endX][endY] = copyPiece;

                //Check all of the other color and see if there end position lands on our king

                if (isInCheck(myColor) != true) {
                    allKosherMoves.add(move);
                }

                myGameBoard.getboard()[firstX][firstY] = myPiece;
                myGameBoard.getboard()[endX][endY] = oldEnd;


//                outerloop:
//                for (int x = 0; x < copyBoard.getboard().length; x++) {
//                    for (int y = 0; y < copyBoard.getboard().length; y++) {
//                        if (copyBoard.getboard()[x][y] != null) {
//                            if (copyBoard.getboard()[x][y].getTeamColor() != myColor) {
//                                ChessPiece opponent = copyBoard.getboard()[x][y];
//                                ChessPosition opPos = new ChessPositionImpl(x + 1, y + 1);
//                                opMoves = opponent.pieceMoves(copyBoard, opPos);
//
//                                //Check to see if they land on the king
//                                for (ChessMove endmoves : opMoves) {
//                                    int opx = endmoves.getEndPosition().getRow() - 1;
//                                    int opy = endmoves.getEndPosition().getColumn() - 1;
//                                    if (copyBoard.getboard()[opx][opy] instanceof King) {
//                                        //Remove that move becuase it is not legal
//                                        allMoves.remove(move);
//                                        break outerloop;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
            }

            //loop through allmoves, deleting the previous spot and then putting the piece in the new spot
            //Loop through everypiece on the board seeing if that move results in the king being captured
            //(team color king being the end piece for any of the opposite color
        }


        return allKosherMoves;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {

        /////////////////////////////////////////////////////////////////////
        //Recieves a given move and executes it, provided it is a          //
        // legal move. If the move is illegal, it throws a                 //
        // InvalidMoveException . A move is illegal if the chess           //
        // piece cannot move there, if the move leaves the team's          //
        // king in danger, or if it's not the corresponding teams turn.    //
        // //////////////////////////////////////////////////////////////////
        Collection<ChessMove> allValidMoves = validMoves(move.getStartPosition());
        if (myGameBoard.getboard()[move.getStartPosition().getRow() -1][move.getStartPosition().getColumn() -1] == null) {
            throw new InvalidMoveException("Move is not valid");
        }
        if (myGameBoard.getboard()[move.getStartPosition().getRow() -1][move.getStartPosition().getColumn() -1].getTeamColor() != curTurn) {
            throw new InvalidMoveException("Move is not valid");
        }


        if (allValidMoves == null) {
            throw new InvalidMoveException("Move is not valid");
        }
        else {
            ChessPiece myPiece = myGameBoard.getboard()[move.getStartPosition().getRow()-1][move.getStartPosition().getColumn()-1];
            TeamColor myteam = myPiece.getTeamColor();
            for (ChessMove ApporvedMove : allValidMoves) {
                if (move.getEndPosition().getColumn() == ApporvedMove.getEndPosition().getColumn()
                && move.getEndPosition().getRow() == ApporvedMove.getEndPosition().getRow()) {
                    if (ApporvedMove.getPromotionPiece() != null ) {
                        if (move.getPromotionPiece() == ChessPiece.PieceType.ROOK) {
                            myPiece = new Rook();
                            myPiece.updateTeamColor(myteam);
                        }
                        if (move.getPromotionPiece() == ChessPiece.PieceType.KNIGHT) {
                            myPiece = new Knight();
                            myPiece.updateTeamColor(myteam);
                        }
                        if (move.getPromotionPiece() == ChessPiece.PieceType.QUEEN) {
                            myPiece = new Queen();
                            myPiece.updateTeamColor(myteam);
                        }
                        if (move.getPromotionPiece() == ChessPiece.PieceType.BISHOP) {
                            myPiece = new Bishop();
                            myPiece.updateTeamColor(myteam);
                        }

                        myGameBoard.getboard()[move.getStartPosition().getRow()-1][move.getStartPosition().getColumn()-1] = null;
                        myGameBoard.getboard()[move.getEndPosition().getRow()-1][move.getEndPosition().getColumn()-1] = myPiece;
                        if (curTurn == TeamColor.WHITE) {
                            curTurn = TeamColor.BLACK;
                        }
                        else {
                            curTurn = TeamColor.WHITE;
                        }
                        return;
                    }
                    else {
                        myGameBoard.getboard()[move.getStartPosition().getRow()-1][move.getStartPosition().getColumn()-1] = null;
                        myGameBoard.getboard()[move.getEndPosition().getRow()-1][move.getEndPosition().getColumn()-1] = myPiece;
                        if (curTurn == TeamColor.WHITE) {
                            curTurn = TeamColor.BLACK;
                        }
                        else {
                            curTurn = TeamColor.WHITE;
                        }
                        return;
                    }

                }
            }
            throw new InvalidMoveException("Move is not valid");
        }

    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {

        //////////////////////////////////////////////////////////////////////
        //Returns true if the specified team's king could be captured by an //
        // opposing piece                                                   //
        //////////////////////////////////////////////////////////////////////
        Collection<ChessMove> opMoves = new HashSet<>();

        outerloop:
        for (int x = 0; x < myGameBoard.getboard().length; x++) {
            for (int y = 0; y < myGameBoard.getboard().length; y++) {
                if (myGameBoard.getboard()[x][y] != null) {
                    if (myGameBoard.getboard()[x][y].getTeamColor() != teamColor) {
                        ChessPiece opponent = myGameBoard.getboard()[x][y];
                        ChessPosition opPos = new ChessPositionImpl(x + 1, y + 1);
                        opMoves = opponent.pieceMoves(myGameBoard, opPos);

                        //Check to see if they land on the king
                        for (ChessMove endmoves : opMoves) {
                            int opx = endmoves.getEndPosition().getRow() - 1;
                            int opy = endmoves.getEndPosition().getColumn() - 1;
                            if (myGameBoard.getboard()[opx][opy] instanceof King) {
                                //Remove that move becuase it is not legal
                                return true;
                            }
                        }
                    }
                }
            }
        }


        return false;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {

        ////////////////////////////////////////////////////////////////////
        //Returns true if the given team has no way to protect their king //
        // from being captured.                                           //
        ////////////////////////////////////////////////////////////////////
        if (isInCheck(teamColor) == true) {
            Collection<ChessMove> anyMoves = new HashSet<>();
            for (int x = 0; x < myGameBoard.getboard().length; x++) {
                for (int y = 0; y < myGameBoard.getboard().length; y++) {
                    if (myGameBoard.getboard()[x][y] != null) {
                        if (myGameBoard.getboard()[x][y].getTeamColor() == teamColor) {
                            ChessPiece teamate = myGameBoard.getboard()[x][y];
                            ChessPositionImpl teamPos = new ChessPositionImpl(x + 1, y + 1);
                            anyMoves = validMoves(teamPos);
                            if (anyMoves.size() != 0) {
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {

        ////////////////////////////////////////////////////////////////////
        //Returns true if the given team has no legal moves, and it is    //
        // currently that team's turn.                                    //
        ////////////////////////////////////////////////////////////////////

        Collection<ChessMove> anyMoves = new HashSet<>();
        for (int x = 0; x < myGameBoard.getboard().length; x++) {
            for (int y = 0; y < myGameBoard.getboard().length; y++) {
                if (myGameBoard.getboard()[x][y] != null) {
                    if (myGameBoard.getboard()[x][y].getTeamColor() == teamColor) {
                        ChessPiece teamate = myGameBoard.getboard()[x][y];
                        ChessPositionImpl teamPos = new ChessPositionImpl(x + 1, y + 1);
                        anyMoves = validMoves(teamPos);

                        if (anyMoves.size() != 0) {
                            return false;
                        }
                    }
                }
            }
        }


        return true;
    }

    @Override
    public void setBoard(ChessBoard board) {
        myGameBoard = board;
    }

    @Override
    public ChessBoard getBoard() {
        return myGameBoard;
    }
}
