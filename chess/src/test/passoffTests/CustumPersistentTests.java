package passoffTests;


import chess.ChessGameImpl;
import dao.AuthDAO;
import dao.GameDAO;
import dao.UserDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import model.AuthToken;
import model.Game;
import model.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;


public class CustumPersistentTests {

    private static AuthDAO authDao;
    private static GameDAO gameDao;

    private static UserDAO userDao;

    private static Database myDatabase = new Database();

    private static Connection conn;
    //Call the DAO methods directly
    @BeforeAll
    public static void setData() throws DataAccessException {

        conn = myDatabase.getConnection();

        gameDao = new GameDAO(conn);
        authDao = new AuthDAO(conn);
        userDao = new UserDAO(conn);
    }

    @BeforeEach
    public void clearAll() throws SQLException, DataAccessException {
        gameDao.clearAll();
        authDao.clearAll();
        userDao.clearAll();
        gameDao.setGameCount(1);
    }

    @AfterAll
    public static void CleanAll() throws SQLException, DataAccessException {
        gameDao.clearAll();
        authDao.clearAll();
        userDao.clearAll();
        conn.close();
    }

    @Test
    @DisplayName("Clear -- UserDAO)")
    public void ClearUserDAOTest() throws SQLException, DataAccessException {
        // You c
        User testUser = new User("testUsername", "testpassword", "testEmail");
        userDao.createUser(testUser);
        userDao.clearAll();
        Assertions.assertNull(userDao.findUser("testUsername"));
    }

    @Test
    @DisplayName("Clear -- GameDAO)")
    public void ClearGameDAOTest() throws SQLException, DataAccessException {

        ChessGameImpl testgame = new ChessGameImpl();
        Game testGame = new Game("testGame", testgame);
        gameDao.createGame(testGame);
        gameDao.clearAll();
        Assertions.assertNull(gameDao.findGame(1));
    }

    @Test
    @DisplayName("Clear -- AuthDAO)")
    public void ClearAuthDAOTest() throws SQLException, DataAccessException {
        // You c
        AuthToken testAuth = new AuthToken("123456789", "testUsername");
        authDao.addAuth("123456789", "testUsername");
        authDao.clearAll();
        Assertions.assertNull(authDao.findAuth("123456789"));
    }


    @Test
    @DisplayName("Insert Auth")
    public void InsertAuthTest() throws SQLException, DataAccessException {

        // Positive test
        authDao.addAuth("123456789", "testUsername");
        Assertions.assertTrue((authDao.findAuth("123456789").getUsername().equals("testUsername")), "username should have been testUsername, but got some other value");

        // False Test
        Assertions.assertTrue((authDao.findAuth("19") == null), "username should have been null, but got some other value");

    }

    @Test
    @DisplayName("Insert Game")
    public void InsertGameTest() throws SQLException, DataAccessException {

        // Positive test
        ChessGameImpl testgame = new ChessGameImpl();
        Game testGame = new Game("testGame", testgame);
        gameDao.createGame(testGame);
        Assertions.assertTrue((gameDao.findGame(1).getGameName().equals("testGame")), "gameName should have been testGame, but got some other value");

        // False Test
        Assertions.assertTrue((gameDao.findGame(10) == null), "gameName should have been testGame, but got some other value");

    }

    @Test
    @DisplayName("Insert User")
    public void InsertUserTest() throws SQLException, DataAccessException {

        // Positive test
        User testUser = new User("testUsername", "testpassword", "testEmail");
        userDao.createUser(testUser);
        Assertions.assertTrue((userDao.findUser("testUsername").getEmail().equals("testEmail")), "email should have been testEmail, but got some other value");

        // False Test
        Assertions.assertTrue((userDao.findUser("gabagoo") == null), "email should have been null, but got some other value");
    }

    @Test
    @DisplayName("Delete Auth")
    public void DeleteAuthTest() throws SQLException, DataAccessException {

        // False test
        authDao.addAuth("123456789", "testUsername");
        authDao.deleteAuth("123789");
        Assertions.assertTrue((authDao.findAuth("123456789").getUsername().equals("testUsername")), "username should have been testUsername, but got some other value");

        // Positive Test
        authDao.deleteAuth("123456789");
        Assertions.assertTrue((authDao.findAuth("123456789") == null), "username should have been null, but got some other value");

    }

    @Test
    @DisplayName("Delete Game")
    public void DeleteGameTest() throws SQLException, DataAccessException {

        // False test
        ChessGameImpl testgameimp = new ChessGameImpl();
        Game testGame = new Game("testGame", testgameimp);

        Game falseGame = new Game("falseGame", null);
        falseGame.setGameID(123);
        gameDao.createGame(testGame);
        gameDao.deleteGame(falseGame);
        Assertions.assertTrue((gameDao.findGame(1).getGameName().equals("testGame")), "gameName should have been testGame, but got some other value");

        // Positive Test
        gameDao.deleteGame(testGame);
        Assertions.assertTrue((gameDao.findGame(1) == null), "gameName should have been null, but got some other value");

    }

    @Test
    @DisplayName("Delete User")
    public void DeleteUserTest() throws SQLException, DataAccessException {

        // False test
        User testUser = new User("testUsername", "testpassword", "testEmail");
        User falseUser = new User("false", "false", "false");
        userDao.createUser(testUser);
        userDao.deleteUser(falseUser);
        Assertions.assertTrue((userDao.findUser("testUsername").getEmail().equals("testEmail")), "email should have been testEmail, but got some other value");

        // Positive Test
        userDao.deleteUser(testUser);
        Assertions.assertTrue((userDao.findUser("testUsername") == null), "email should have been null, but got some other value");
    }


    @Test
    @DisplayName("Update User")
    public void UpdateUserTest() throws SQLException, DataAccessException {

        // False test
        User testUser = new User("testUsername", "testpassword", "testEmail");
        User falseUser = new User("false", "false", "false");
        userDao.createUser(testUser);
        userDao.updateUser(falseUser);
        Assertions.assertTrue((userDao.findUser("testUsername").getEmail().equals("testEmail")), "email should have been testEmail, but got some other value");

        // Positive Test
        falseUser = new User("testUsername", "false", "false");
        userDao.updateUser(falseUser);
        Assertions.assertTrue((userDao.findUser("testUsername").getEmail().equals("false")), "email should have been false, but got some other value");
    }

    @Test
    @DisplayName("update Auth")
    public void UpdateAuthTest() throws SQLException, DataAccessException {

        // False test
        authDao.addAuth("123456789", "testUsername");
        AuthToken updateAuth = new AuthToken("12345", "updateUsername");
        authDao.updateAuth(updateAuth);
        Assertions.assertTrue((authDao.findAuth("123456789").getUsername().equals("testUsername")), "username should have been testUsername, but got some other value");

        // Positive Test
        updateAuth = new AuthToken("123456789", "updateUsername");
        authDao.deleteAuth("123456789");
        Assertions.assertTrue((authDao.findAuth("123456789") == null), "username should have been null, but got some other value");

    }

    @Test
    @DisplayName("Update Game")
    public void UpdateGameTest() throws SQLException, DataAccessException {

        // False test
        ChessGameImpl testgame = new ChessGameImpl();
        Game testGame = new Game("testGame", testgame);

        ChessGameImpl userGame = new ChessGameImpl();
        Game falseGame = new Game("falseGame", userGame);
        falseGame.setGameID(123);
        gameDao.createGame(testGame);
        gameDao.updateGame(falseGame);
        String name = gameDao.findGame(1).getGameName();
        Assertions.assertTrue((name.equals("testGame")), "gameName should have been testGame, but got some other value");

        // Positive Test
        falseGame = new Game("testGame", userGame);
        gameDao.updateGame(falseGame);
        Assertions.assertTrue((gameDao.findGame(1).getGameName() != null), "gameName should have been testGame, but got some other value");

    }

    @Test
    @DisplayName("Test findAll Game")
    public void FindAllGameTest() throws SQLException, DataAccessException {

        // False test
        ChessGameImpl testgame = new ChessGameImpl();
        Game testGame = new Game("testGame", testgame);
        gameDao.createGame(testGame);

        ChessGameImpl testgame1 = new ChessGameImpl();
        Game testGame1 = new Game("testGame1", testgame);
        gameDao.createGame(testGame1);


        Assertions.assertTrue((authDao.findAll() != null), "should have returned a list");

    }

    @Test
    @DisplayName("find All Auth")
    public void findAllAuthTest() throws SQLException, DataAccessException {

        // False test
        authDao.addAuth("123456789", "testUsername");
        AuthToken updateAuth = new AuthToken("12345", "updateUsername");

        Assertions.assertTrue((authDao.findAll() != null), "username should have been testUsername, but got some other value");


    }

    @Test
    @DisplayName("find All User")
    public void findAllUserTest() throws SQLException, DataAccessException {

        // False test
        User testUser = new User("testUsername", "testpassword", "testEmail");
        User falseUser = new User("false", "false", "false");
        userDao.createUser(testUser);
        userDao.createUser(falseUser);
        Assertions.assertTrue((userDao.findAll() != null), "email should have been testEmail, but got some other value");

    }


}