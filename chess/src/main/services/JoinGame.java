package services;

import dao.AuthDAO;
import dao.GameDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import model.AuthToken;
import model.Game;
import requests.JoinGameRequest;
import responses.JoinGameResponse;

import java.sql.SQLException;

/**
 * Verifies that the specified game exists, and, if a color is specified, adds the caller as
 * the requested color to the game. If no color is specified the user is joined as an
 * observer. This request is idempotent.
 */
public class JoinGame {

    /**
     * joinGameResponse does the work of adding the player to a game if that game exists
     * @param request : this is the instance of JoinGameRequest created by the input
     * @return : the JoinGameRequest instance
     */
    public JoinGameResponse joinGameService(JoinGameRequest request) throws DataAccessException {

        Database myDatabase = new Database();

        try (var conn = myDatabase.getConnection();) {

            ///This is my original code////
            Integer gameID = request.getGameID();
            String message = null;
            String colorChoice = request.getPlayerColor();
            String requestAuth = request.getAuthToken();
            GameDAO gameDAO = new GameDAO(conn);
            AuthDAO authDao = new AuthDAO(conn);



            if ( gameDAO.findGame(gameID) == null) {
                message = "Error: bad request";
            } else if (gameDAO.findGame(gameID) != null) {

                if (colorChoice == null) {
                    JoinGameResponse response = new JoinGameResponse(message);
                    return response;
                }

                if ( !colorChoice.equals("BLACK") && !colorChoice.equals("WHITE")) {
                    message = "Error: bad request";
                }


                AuthToken myAuth = authDao.findAuth(requestAuth);
                String myUsername = myAuth.getUsername();

                Game selectedGame = gameDAO.findGame(gameID);
                if (colorChoice.equals("WHITE")) {

                    // if my username matches the current games name for that color or if my
                    // selected game color equals null
                    if (selectedGame.getWhiteUsername() == null || selectedGame.getWhiteUsername().equals(myUsername)) {
                        selectedGame.UpdateWhiteUserName(myUsername);
                        gameDAO.updateGame(selectedGame);
                        message = null;
                    }
                    else {
                        message = "Error: already taken";
                    }
                } else if (colorChoice.equals("BLACK")) {
                    if (selectedGame.getBlackUsername() == null || selectedGame.getBlackUsername().equals(myUsername) ) {
                        selectedGame.UpdateBlackUserName(myUsername);
                        gameDAO.updateGame(selectedGame);

                        message = null;
                    }
                    else {
                        message = "Error: already taken";
                    }
                }
            }
            else {
                message = "\"Error: description\"";
            }


            myDatabase.returnConnection(conn);

            JoinGameResponse response = new JoinGameResponse(message);
            return response;



        } catch (SQLException e) {
            throw new DataAccessException("Error while trying to access data");
        }
        }


}


