package dao;

import dataAccess.DataAccessException;
import model.AuthToken;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Class that has the ability to add AuthTokens to the game as well as:
 *      - See if they exist in the data base
 *      - Update them if needed
 *      - Delete them if needed
 */
public class AuthDAO {


    /**
     * This will temporaily store our database while we are not using SQL
     */
    private static HashMap<String, AuthToken> AuthTokens = new HashMap<>();

    private Connection conn;

    public AuthDAO(Connection conn) throws DataAccessException {
        //One way is to take a connection and save it, then

        this.conn = conn;

    }

    /**
     * Creates a new Auth to go into the database.
     * @throws DataAccessException
     */
    public void addAuth(String authTokenString, String username) throws DataAccessException, SQLException {
        // Will this function now just ass authTokenString and username to the table,
        // not worrying about the model itself?

        try (var preparedStatement = conn.prepareStatement("INSERT INTO authdao (authToken, username) VALUES(?, ?)", RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, authTokenString);
            preparedStatement.setString(2, username);

            preparedStatement.executeUpdate();
        }
    }

    public String createAuth() throws DataAccessException {


        String authTokenString = UUID.randomUUID().toString();

        return authTokenString;
    }

    /**
     * Finds the Auth to see if they are in the database.
     * @param aT
     * @throws DataAccessException
     */
    public AuthToken findAuth(String aT) throws DataAccessException, SQLException {
//        if (AuthTokens.get(aT) == null) {
//            return null;
//        }
//        else {
//            return AuthTokens.get(aT);
//        }

        try (var preparedStatement = conn.prepareStatement("SELECT authToken, username FROM authdao WHERE authToken=?")) {
            preparedStatement.setString(1, aT);
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var authToken = rs.getString("authToken");
                    var username = rs.getString("username");

                    AuthToken returnAuth = new AuthToken(authToken, username);

                    return returnAuth;
                }
            }
        }
    return null;
    }

    /**
     * Updates the Auth if needed.
     * @param aT
     * @throws DataAccessException
     */
    public void updateAuth(AuthToken aT) throws DataAccessException, SQLException {
//        AuthTokens.replace(aT.getAuthToken(), aT);

        try (var preparedStatement = conn.prepareStatement("UPDATE authdao SET username=? WHERE authToken=?")) {
            preparedStatement.setString(1, aT.getUsername());
            preparedStatement.setString(2, aT.getAuthToken());

            preparedStatement.executeUpdate();
        }
    }

    /**
     * Deletes the Auth if needed.
     * @param aT
     * @throws DataAccessException
     */
    public void deleteAuth(String aT) throws DataAccessException , SQLException{
//        AuthTokens.remove(aT);
        try (var preparedStatement = conn.prepareStatement("DELETE FROM authdao WHERE authToken=?")) {
            preparedStatement.setString(1, aT);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Finds every game in the database
     * @throws DataAccessException
     */
    public HashMap<String, AuthToken> findAll() throws DataAccessException, SQLException {

        AuthTokens.clear();

        try (var preparedStatement = conn.prepareStatement("SELECT authToken, username FROM authdao")) {
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var authToken = rs.getString("authToken");
                    var username = rs.getString("username");

                    AuthToken returnAuth = new AuthToken(authToken, username);

                    AuthTokens.put(returnAuth.getAuthToken(), returnAuth);
                }
            }
        }


        return AuthTokens;
    }

    /**
     * Clears everthing in the database
     * @throws DataAccessException
     */
    public void clearAll() throws DataAccessException, SQLException {
        AuthTokens.clear();
        try (var prepareStatement = conn.prepareStatement("TRUNCATE TABLE authdao;")) {
            prepareStatement.executeUpdate();
        }
    }


}
