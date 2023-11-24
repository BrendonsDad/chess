package services;

import dao.AuthDAO;
import dao.UserDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import model.User;
import requests.RegisterRequest;
import responses.RegisterResponse;

import java.sql.SQLException;

/**
 * Register a new user
 */
public class Register {

    /**
     * This will take the register request and use that infro to register a new user and return
     * a message indicating success or failure.
     * @param request : The instance of our RegisterRequest that helps us create a response
     * @return : Our response which contains the message.
     */
    public RegisterResponse registerService(RegisterRequest request) throws DataAccessException {

        Database myDatabase = new Database();

        try (var conn = myDatabase.getConnection();) {

            ///This is my original code////
            // Initialize the User DAO
            UserDAO userDao = new UserDAO(conn);
            // Initialize the AuthTokenDAO
            AuthDAO authDao = new AuthDAO(conn);
            // Initialize the variables
            String email = request.getEmail();
            String userName = request.getUsername();
            String requestPassword = request.getPassword();
            String authToken;
            String message;


            // success = { "username":"", "authToken":"" }
            // 400 = { "message": "Error: bad request" }
            // 403 = { "message": "Error: already taken" }
            // 500 = { "message": "Error: description" }

            if (email == null || userName == null || requestPassword == null || email.equals("") || userName.equals("") || requestPassword.equals("")) {
                authToken = null;
                userName = null;
                message = "Error: bad request";
                RegisterResponse response = new RegisterResponse(message, authToken, userName);
                return response;
            }

            User findUsername = userDao.findUser(request.getUsername());

            if (findUsername == null) {

                User newUser = new User(userName, requestPassword, email);
                userDao.createUser(newUser);
                authToken = authDao.createAuth();
                authDao.addAuth(authToken, request.getUsername());
                message = null;

            }
            else if (findUsername != null) {
                message = "Error: already taken";
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
            RegisterResponse response = new RegisterResponse(message, authToken, userName);
            return response;

            // your og return statement
            //This is where my original code ends//

        } catch (SQLException e) {
            throw new DataAccessException("Error while trying to access data");
        }


    }

}
