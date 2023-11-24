package services;

import dao.GameDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import model.Game;
import requests.ListGameRequest;
import responses.ListGamesResponse;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Gives a list of all the games
 */
public class ListGames {

    /**
     *
     * @param request : this is the instance of the ListGameRequest
     * @return returns the response create using the instance of ListGameRequest
     */
    public ListGamesResponse listGamesService(ListGameRequest request) throws DataAccessException {

        Database myDatabase = new Database();

        try (var conn = myDatabase.getConnection();) {

            ///This is my original code////

            GameDAO gameDAO = new GameDAO(conn);

            ArrayList<Game> games = new ArrayList<>(gameDAO.findAll());

            if (request.getAuthToken() == null) {
                ListGamesResponse response = new ListGamesResponse("Error: unauthorized", games);
                return response;
            }

            ListGamesResponse response = new ListGamesResponse(null, games);

            myDatabase.returnConnection(conn);
            return response;

            // your og return statement
            //This is where my original code ends//

        } catch (SQLException e) {
            throw new DataAccessException("Error while trying to access data");
        }

    }

}
