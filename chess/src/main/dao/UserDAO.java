package dao;

import dataAccess.DataAccessException;
import model.AuthToken;
import model.User;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.HashMap;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Class that has the ability to add Users to the game as well as:
 *      - See if they exist in the data base
 *      - Update them if needed
 *      - Delete them if needed
 */
public class UserDAO {

    /**
     * This will temporaily store our database while we are not using SQL
     * String is the persons username and User is the user object
     */
    private static HashMap<String, User> Users = new HashMap<>();

    private Connection conn;

    public UserDAO(Connection conn) throws DataAccessException {

        this.conn = conn;
    }


    /**
     * Creates a new user to go into the database.
     * @param u
     * @throws DataAccessException
     */
    public void createUser(User u) throws DataAccessException, SQLException {
            //Users.put(u.getUsername(), u);
        try (var preparedStatement = conn.prepareStatement("INSERT INTO userdao (username, password, email) VALUES(?, ?, ?)", RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, u.getUsername());
            preparedStatement.setString(2, u.getPassword());
            preparedStatement.setString(3, u.getEmail());

            preparedStatement.executeUpdate();
        }

    }

    /**
     * Finds the User to see if they are in the database.
     * @param uID
     * @throws DataAccessException
     */
    public User findUser(String uID) throws DataAccessException, SQLException {
        // Would this take an authToken?
//        return Users.get(uID);

        try (var preparedStatement = conn.prepareStatement("SELECT username, password, email FROM userdao WHERE username=?")) {
            preparedStatement.setString(1, uID);
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var username = rs.getString("username");
                    var password = rs.getString("password");
                    var email = rs.getString("email");

                    User returnUser = new User(username, password, email);

                    return returnUser;
                }
            }
        }
    return null;
    }

    /**
     * Updates the user if needed.
     * @param u
     * @throws DataAccessException
     */
    public void updateUser(User u) throws DataAccessException, SQLException {
//        Users.replace(u.getUsername(), u);
        try (var preparedStatement = conn.prepareStatement("UPDATE userdao SET password=?, email=? WHERE username=?")) {
            preparedStatement.setString(1, u.getPassword());
            preparedStatement.setString(2, u.getEmail());
            preparedStatement.setString(3, u.getUsername());

            preparedStatement.executeUpdate();
        }
    }

    /**
     * Deletes the user if needed.
     * @param u
     * @throws DataAccessException
     */
    public void deleteUser(User u) throws DataAccessException, SQLException {

        //Users.remove(u.getUsername());
        try (var preparedStatement = conn.prepareStatement("DELETE FROM userdao WHERE username=?")) {
            preparedStatement.setString(1, u.getUsername());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Finds every game in the database
     * @throws DataAccessException
     */
    public HashMap<String, User> findAll() throws DataAccessException, SQLException {
        Users.clear();

        try (var preparedStatement = conn.prepareStatement("SELECT username, password, email FROM userdao")) {
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var username = rs.getString("username");
                    var password = rs.getString("password");
                    var email = rs.getString("email");

                    User returnUser = new User(username, password, email);

                    Users.put(returnUser.getUsername(), returnUser);
                }
            }
        }


        return Users;
    }

    /**
     * Clears everthing in the database
     * @throws DataAccessException
     */
    public void clearAll() throws DataAccessException, SQLException {
        Users.clear();
        try (var prepareStatement = conn.prepareStatement("TRUNCATE TABLE userdao;")) {
            prepareStatement.executeUpdate();
        }
    }

}
