package dao;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessGameImpl;
import chess.ChessPiece;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.adapters.ChessBoardAdapter;
import dao.adapters.ChessPieceAdapter;
import dao.adapters.GameAdapter;
import dataAccess.DataAccessException;
import model.Game;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Class that has the ability to add Game to the game as well as:
 *      - See if they exist in the data base
 *      - Update them if needed
 *      - Delete them if needed
 *      - Find every game
 *      - Clear the database
 */

//weird instructions on Canvas are only for the game field in the Game model
//    String pieceType = jsonElement.getAsJsonObject().get("type").getAsString();
public class GameDAO {

    /**
     * This will temporaily store our database while we are not using SQL
     */
    private static HashMap<Integer, Game> games = new HashMap<>();
    private static int gameCount = 1;

    private Connection conn;


    public GameDAO(Connection conn) {
        this.conn = conn;
    }


    /**
     * Creates a new Game to go into the database.
     * @param g
     * @throws DataAccessException
     */
    public void createGame(Game g) throws DataAccessException, SQLException {

        g.setGameID(this.gameCount);
        this.gameCount += 1;

        try (var preparedStatement = conn.prepareStatement("INSERT INTO gamedao (whiteUsername, blackUsername, gameName, game) VALUES(?, ?, ?, ?)", RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, g.getWhiteUsername());
            preparedStatement.setString(2, g.getBlackUsername());
            preparedStatement.setString(3, g.getGameName());
            // NEED HELP WITH THIS ONE
            // NEed to serialize chess game, chess board, and chess piece, have a switch statement for piece
            // You should be able to use gson here to convert it correctly

            Gson gson = new Gson();
            preparedStatement.setString(4, gson.toJson(g.getGame()));

            preparedStatement.executeUpdate();
        }


        // Create a service class, give it a request, call the methods on the service and see if it corrects the right stuff.

    }

    public ChessGameImpl gameSerializer(java.sql.ResultSet rs) throws SQLException {
        var json = rs.getString("game");

        var builder = new GsonBuilder();
        builder.registerTypeAdapter(ChessGame.class, new GameAdapter());
        builder.registerTypeAdapter(ChessPiece.class, new ChessPieceAdapter());
        builder.registerTypeAdapter(ChessBoard.class, new ChessBoardAdapter());

        ChessGameImpl game = builder.create().fromJson(json, ChessGameImpl.class);

        return game;
    }

    /**
     * Finds the Game to see if they are in the database.
     * @param gID
     * @throws DataAccessException
     */
    public Game findGame(Integer gID) throws DataAccessException, SQLException {
        //return games.get(gID);

        try (var preparedStatement = conn.prepareStatement("SELECT gameID, whiteUsername, blackUsername, gameName, game FROM gamedao WHERE gameID=?")) {
            preparedStatement.setInt(1, gID);
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var gameID = rs.getInt("gameID");
                    var whiteUsername = rs.getString("whiteUsername");
                    var blackUsername = rs.getString("blackUsername");
                    var gameName = rs.getString("gameName");
                    //var game = rs.getString("game");
                    // register the adaptars and call toJson

                    ChessGameImpl game = gameSerializer(rs);

                    Game returnGame = new Game(gameName, game);
                    returnGame.UpdateWhiteUserName(whiteUsername);
                    returnGame.UpdateBlackUserName(blackUsername);
                    returnGame.setGameID(gameID);

                    return returnGame;
                }
            }
        }
        return null;

    }

    /**
     * Updates the Game if needed.
     * @param g
     * @throws DataAccessException
     */
    public void updateGame(Game g) throws DataAccessException, SQLException {
       // games.replace(g.getGameID(), g);

        try (var preparedStatement = conn.prepareStatement("UPDATE gamedao SET whiteUsername=?, blackUsername=?, gameName=?, game=? WHERE gameID=?")) {
            preparedStatement.setString(1, g.getWhiteUsername());
            preparedStatement.setString(2, g.getBlackUsername());
            preparedStatement.setString(3, g.getGameName());

            Gson gson = new Gson();
            preparedStatement.setString(4, gson.toJson(g.getGame()));
            preparedStatement.setInt(5, g.getGameID());

            preparedStatement.executeUpdate();
        }
    }

    /**
     * Deletes the Game if needed.
     * @param g
     * @throws DataAccessException
     */
    public void deleteGame(Game g) throws DataAccessException, SQLException {
        //games.remove(g.getGameID());

        try (var preparedStatement = conn.prepareStatement("DELETE FROM gamedao WHERE gameID=?")) {
            preparedStatement.setInt(1, g.getGameID());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Finds every game in the database
     * @param g
     * @throws DataAccessException
     */
    public Collection<Game> findAll() throws DataAccessException, SQLException {
        // return games.values();
        games.clear();

        try (var preparedStatement = conn.prepareStatement("SELECT gameID, whiteUsername, blackUsername, gameName, game FROM gamedao")) {
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var gameID = rs.getInt("gameID");
                    var whiteUsername = rs.getString("whiteUsername");
                    var blackUsername = rs.getString("blackUsername");
                    var gameName = rs.getString("gameName");

                    ChessGameImpl game = gameSerializer(rs);

                    Game returnGame = new Game(gameName, game);
                    returnGame.UpdateWhiteUserName(whiteUsername);
                    returnGame.UpdateBlackUserName(blackUsername);
                    returnGame.setGameID(gameID);


                    games.put(returnGame.getGameID(), returnGame);
                }
            }
        }


        return games.values();
    }

    /**
     * Clears everthing in the database
     * @throws DataAccessException
     */
    public void clearAll() throws DataAccessException, SQLException {
        games.clear();
        try (var prepareStatement = conn.prepareStatement("TRUNCATE TABLE gamedao;")) {
            prepareStatement.executeUpdate();
        }
    }

    /**
     * Claims a spot for our user if available.
     * @param u
     * @throws DataAccessException
     */

    // Reduce game to a game id and consoludate assign color and Claim Spot,
    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }


}