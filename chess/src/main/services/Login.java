package services;

import dao.AuthDAO;
import dao.UserDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import model.User;
import requests.LoginRequest;
import responses.LogInResponse;

import java.sql.SQLException;

/**
 * Logs in an existing user (returns a new authToken)
 */
public class Login {

    /**
     * This class takes in the request and uses that info to log in to the game database
     * if the request fails it will return a request with an error message. If it succeeds,
     * it will return a message with the authToken as part of the response
     * @param request : The instance of LoginRequest that will help us make a response
     * @return : The LogInResponse containing the message as well as the authToken
     */
    public LogInResponse loginService(LoginRequest request) throws DataAccessException {

        Database myDatabase = new Database();

        try (var conn = myDatabase.getConnection();) {

            ///This is my original code////

            // Initialize the User DAO
            UserDAO userDao = new UserDAO(conn);
            // Initialize the AuthTokenDAO
            AuthDAO authDao = new AuthDAO(conn);
            // Initialize the variables
            String userName;
            String authToken;
            String message;


            User findUsername = userDao.findUser(request.getUsername());


            if (findUsername != null) {

                String actualPassword = findUsername.getPassword();
                String requestPassword = request.getPassword();
                if (actualPassword.equals(requestPassword)) {
                    userName = request.getUsername();
                    authToken = authDao.createAuth();
                    authDao.addAuth(authToken, userName);
                    message = null;
                }

                else {
                    message = "Error: unauthorized";
                    userName = null;
                    authToken = null;
                }


            }
            else if (userDao.findUser(request.getUsername()) == null) {
                message = "Error: unauthorized";
                userName = null;
                authToken = null;

            }
            else {
                //message = "Error: Server Error";
                message = "description";
                userName = null;
                authToken = null;

            }

            myDatabase.returnConnection(conn);
            LogInResponse response = new LogInResponse(message, authToken, userName);
            return response;

            // your og return statement
            //This is where my original code ends//

        } catch (SQLException e) {
            throw new DataAccessException("Error while trying to access data");
        }

    }
}
