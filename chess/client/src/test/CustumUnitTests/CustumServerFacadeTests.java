package CustumUnitTests;

import exception.ResponseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requests.*;
import responses.*;
import serverFac.ServerFacade;

import java.io.IOException;
import java.net.URISyntaxException;

public class CustumServerFacadeTests {

    private ServerFacade server = new ServerFacade("http://localhost:8080");
    private String authToken;

    /**
     * These tests require that you run the Server first
     */

    /**
     * WEBSOCETS IN CLASS
     * - Every client will connect ot the server with a connection that stays open
     * - We just hold it open
     * - In the websocet protocole, we hae join player and join observer
     * - ANy time a plyaer makes a move, every player gets a load game message
     */
    @BeforeEach
    public void clearEach() throws ResponseException {
        RegisterRequest request = new RegisterRequest("SuperduperLights", "aregonnafindmebutIwont", "feelbluelikeialwaysdocuasesomewhereinthecrowdtheresyou");
        RegisterResponse response = server.register(request);
        authToken = response.getAuthToken();

        //clear the db
        try {
            server.clearDataBase(authToken);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    @DisplayName("Test Clear")
    public void ClearTest() throws ResponseException {
        RegisterRequest request = new RegisterRequest("username", "password", "email");
        RegisterResponse response = server.register(request);
        authToken = response.getAuthToken();

        //clear the db
        try {
            server.clearDataBase(authToken);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        LoginRequest loginReq = new LoginRequest("username", "password");

        int assertNum = server.getStatusCode("POST", "/session", null, loginReq);

        //change to == 401
        Assertions.assertEquals(assertNum, 401, "Expected error, but got something else.");
    }

    @Test
    @DisplayName("Register positive")
    public void RegisterPositiveTest() throws ResponseException, URISyntaxException, IOException {

        RegisterRequest request = new RegisterRequest("username", "password", "email");
        RegisterResponse response = server.register(request);
        authToken = response.getAuthToken();


        Assertions.assertTrue(response.getUsername().equals("username"), "Expected message to be null, but got error.");

        //clear the db
        try {
            server.clearDataBase(authToken);
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }

    @Test
    @DisplayName("Register negative")
    public void RegisterNegativeTest() throws ResponseException {
        //POST
        //path: /user


        RegisterRequest request = new RegisterRequest("username", " password", "email");
        RegisterResponse response = server.register(request);
        authToken = response.getAuthToken();


        // Test without registering beforehand
        RegisterRequest request2 = new RegisterRequest("username", " password", "email");

        int assertNum = server.getStatusCode("POST", "/user", authToken, request2);

        //change to == 403
        Assertions.assertTrue(assertNum == 403, "Expected error, but got something else.");

        //clear the db
        try {
            server.clearDataBase(authToken);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    @DisplayName("Login Positive")
    public void LoginPositiveTest() throws ResponseException {

        //POST
        //path: /session

        RegisterRequest request = new RegisterRequest("username", "password", "email");
        RegisterResponse response = server.register(request);
        authToken = response.getAuthToken();

        LogoutRequest logRequest = new LogoutRequest(authToken);
        LogoutResponse logResponse = server.logout(logRequest);

        LoginRequest loginReq = new LoginRequest("username", "password");
        LogInResponse loginResp = server.login(loginReq);
        authToken = loginResp.getAuthToken();



        Assertions.assertTrue(response.getUsername().equals("username"), "Expected message to be username, but got error.");

        //clear the db
        try {
            server.clearDataBase(authToken);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    @DisplayName("Login Negative")
    public void LoginNegativeTest() throws ResponseException {


        LoginRequest loginReq = new LoginRequest("falsetest", "falsetest");

        int assertNum = server.getStatusCode("POST", "/session", null, loginReq);

        //change to == 401
        Assertions.assertTrue(assertNum == 401, "Expected error, but got something else.");

    }

    @Test
    @DisplayName("Logout Negative")
    public void LogoutNegativeTest() throws ResponseException {

        //DELETE
        //path: /session

        LogoutRequest logReq = new LogoutRequest(null);

        int assertNum = server.getStatusCode("DELETE", "/session", null, logReq);

        //change to == 401
        Assertions.assertTrue(assertNum != 200, "Expected error, but got something else.");
    }

    @Test
    @DisplayName("Logout Positive")
    public void LogoutPositiveTest() throws ResponseException {

        RegisterRequest request = new RegisterRequest("username", "password", "email");
        RegisterResponse response = server.register(request);
        authToken = response.getAuthToken();

        LogoutRequest logReq = new LogoutRequest(authToken);
        LogoutResponse logResp = server.logout(logReq);

        Assertions.assertTrue(logResp.getMessage() == null, "Expected message to be null, but got error.");

        try {
            server.clearDataBase(authToken);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    @DisplayName("ListGames Negative")
    public void ListGamesNegativeTest() throws ResponseException {

        //GET
        //path: /game

        ListGameRequest listReq = new ListGameRequest("");
        int assertNum = server.getStatusCode("DELETE", "/session", null, listReq);

        //change to == 401
        Assertions.assertTrue(assertNum != 200, "Expected error, but got something else.");
    }

    @Test
    @DisplayName("ListGames Positive")
    public void ListGamesPositiveTest() throws ResponseException {

        RegisterRequest request = new RegisterRequest("username", "password", "email");
        RegisterResponse response = server.register(request);
        authToken = response.getAuthToken();

        ListGameRequest listReq = new ListGameRequest(authToken);
        ListGamesResponse listResp = server.listGames(listReq);

        Assertions.assertTrue(listResp.getMessage() == null, "Expected message to be null, but got error.");

        try {
            server.clearDataBase(authToken);
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }

    @Test
    @DisplayName("CreateGame Negative")
    public void CreateGameNegativeTest() throws ResponseException {


        //POST
        //path: /game
        CreateGameRequest createReq = new CreateGameRequest("test");
        int assertNum = server.getStatusCode("DELETE", "/session", null, createReq);

        //change to == 401
        Assertions.assertTrue(assertNum != 200, "Expected error, but got something else.");

    }

    @Test
    @DisplayName("CreateGame Positive")
    public void CreateGamePositiveTest() throws ResponseException {


        RegisterRequest request = new RegisterRequest("username", "password", "email");
        RegisterResponse response = server.register(request);
        authToken = response.getAuthToken();

        CreateGameRequest createReq = new CreateGameRequest("test");
        CreateGameResponse createResp = server.createGame(createReq, authToken);

        Assertions.assertTrue(createResp.getMessage() == null, "Expected message to be null, but got error.");

        try {
            server.clearDataBase(authToken);
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }

    @Test
    @DisplayName("JoinGame Negative")
    public void JoinGameNegativeTest() throws ResponseException {

        //PUT
        //path: /game
        JoinGameRequest joinReq = new JoinGameRequest("WHITE", 1);
        int assertNum = server.getStatusCode("DELETE", "/session", null, joinReq);

        //change to == 401
        Assertions.assertTrue(assertNum != 200, "Expected error, but got something else.");

    }

    @Test
    @DisplayName("JoinGame Positive")
    public void JoinGamePositiveTest() throws ResponseException {

        //Register to get in the system
        RegisterRequest request = new RegisterRequest("username", "password", "email");
        RegisterResponse response = server.register(request);
        authToken = response.getAuthToken();

        //make a game
        CreateGameRequest createReq = new CreateGameRequest("test");
        CreateGameResponse createResp = server.createGame(createReq, authToken);

        //Join it
        JoinGameRequest joinReq = new JoinGameRequest("WHITE", 1);
        joinReq.setAuthToken(authToken);
        JoinGameResponse joinResp = server.joinGame(joinReq);

        Assertions.assertTrue(joinResp.getMessage() == null, "Expected message to be null, but got error.");

        try {
            server.clearDataBase(authToken);
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }


}
