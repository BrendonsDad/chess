package services;

import chess.ChessGameImpl;
import dao.GameDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import model.Game;
import requests.CreateGameRequest;
import responses.CreateGameResponse;

import java.sql.SQLException;

/**
 * Create game service works with the hanlder to create a game for our database
 */
public class CreateGame {

    /**
     * createGameService takes the request variable, make the game, and if not, responds with an error.
     * @param request: request is our request object we use to know how to make our response
     * @return : returns a CreateGameResponse instance that
     */
    public CreateGameResponse createGameService(CreateGameRequest request) throws DataAccessException {

        Database myDatabase = new Database();

        try (var conn = myDatabase.getConnection();) {

            ///This is my original code////
            Integer gameID;
            String message;
            String gameName = request.getGameName();
            GameDAO gameDAO = new GameDAO(conn);

            if (gameName == null || gameName.equals("none") || gameName.equals("")) {
                gameID = null;
                message = "Error: bad request";
            }
            else {
                ChessGameImpl chessGame = new ChessGameImpl();
                Game newGame = new Game(gameName, chessGame);
                gameDAO.createGame(newGame);
                message = null;
                gameID = newGame.getGameID();
            }

            myDatabase.returnConnection(conn);
            CreateGameResponse response = new CreateGameResponse(message, gameID);
            return response;

            // your og return statement
            //This is where my original code ends//

        } catch (SQLException e) {
            throw new DataAccessException("Error while trying to access data");
        }

    }
}
