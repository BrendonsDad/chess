package services;

import dao.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import requests.LogoutRequest;
import responses.LogoutResponse;

import java.sql.SQLException;

/**
 * Logs out the user represented by the authToken.
 */
public class Logout {

    /**
     * Does the work of loging out the user if an appropriate request was given
     * @param request : an instance of LogoutRequest that gives us the info we need to make a request
     * @return : returns an instance of LogoutResponse which message either be null or one of the error
     */
    public LogoutResponse logoutService(LogoutRequest request) throws DataAccessException {

        Database myDatabase = new Database();

        try (var conn = myDatabase.getConnection();) {

            ///This is my original code////
            if (request.getAuthToken() == null) {
                LogoutResponse response = new LogoutResponse("Error: unauthorized");
                return response;
            }
            LogoutResponse response = new LogoutResponse(null);

            AuthDAO authDAO = new AuthDAO(conn);

            authDAO.deleteAuth(request.getAuthToken());

            myDatabase.returnConnection(conn);
            return response;

            // your og return statement
            //This is where my original code ends//

        } catch (SQLException e) {
            throw new DataAccessException("Error while trying to access data");
        }


    }
}
