package services;

import ServerError.ServerErrorException;
import dao.AuthDAO;
import dao.GameDAO;
import dao.UserDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import responses.BaseResponse;

import java.sql.SQLException;

/**
 * Clears the database. Removes all users, games, and authTokens.
 */
public class ClearApplication {


    /** clearDataBase removes all users, games, and authTokens from our DataBase. It also returns a String
     * indicating weather the command worked or not. Please help TA to make sure this is right.
     */
    public  BaseResponse clearService() throws ServerErrorException, DataAccessException {

        Database myDatabase = new Database();

        try (var conn = myDatabase.getConnection();) {

            ///This is my original code////
            // Make instances of the Object
            AuthDAO auth = new AuthDAO(conn);
            GameDAO game = new GameDAO(conn);
            UserDAO user = new UserDAO(conn);

            // Clear all the data in each of the DAOs
            auth.clearAll();
            game.clearAll();
            user.clearAll();
            game.setGameCount(1);

            String message = null;

            BaseResponse response = new BaseResponse(message);

            myDatabase.returnConnection(conn);

            return response;

            // your og return statement
            //This is where my original code ends//

        } catch (SQLException e) {
            throw new DataAccessException("Error while trying to access data");
        }

    }
}
