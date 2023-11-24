package serverTests;

import ServerError.ServerErrorException;
import dataAccess.DataAccessException;
import dataAccess.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requests.*;
import responses.*;
import services.*;


public class CustumAPITests {

    @Test
    @DisplayName("Clear Application")
    public void ClearApplicationTest() throws ServerErrorException, DataAccessException {


        // Clear the Database and make sure it returns success
        ClearApplication service = new ClearApplication();
        BaseResponse response = service.clearService();

        Assertions.assertNull(response.getMessage(),
                "Expected message to be null, but got error.");
    }


    @Test
    @DisplayName("Create Game")
    public void CreateGameTest() throws DataAccessException, ServerErrorException {


        // Make sure we can create a game and that it has the correct order
        CreateGame service = new CreateGame();
        CreateGameRequest testGameRequest = new CreateGameRequest("testGame");
        CreateGameResponse testGameResponse = service.createGameService(testGameRequest);


        Assertions.assertTrue((testGameResponse.getgameID() == 1), "gameID should be 1 but got some other value");

        // Make sure when we pass a null variable for gameName, it returns a bad request
        testGameRequest.setName(null);
        testGameResponse = service.createGameService(testGameRequest);

        Assertions.assertTrue((testGameResponse.getMessage().equals("Error: bad request")),
                "Should have gotten an \"Error: bad request\" message, but did not");

        ClearApplication clear = new ClearApplication();
        BaseResponse clearResponse = clear.clearService();
    }

    @Test
    @DisplayName("Join Game")
    public void JoinGameTest() throws DataAccessException, ServerErrorException {


        // Ensure that we join a game, the gameID is correct
        CreateGame service = new CreateGame();
        CreateGameRequest testGameRequest = new CreateGameRequest("testGame");
        CreateGameResponse testGameResponse = service.createGameService(testGameRequest);


        Assertions.assertTrue((testGameResponse.getgameID() == 1), "gameID should be 1 but got some other value");

        // Ensure a request with the name set to null
        testGameRequest.setName(null);
        testGameResponse = service.createGameService(testGameRequest);

        Assertions.assertTrue((testGameResponse.getMessage().equals("Error: bad request")),
                "Should have gotten an \"Error: bad request\" message, but did not");

        ClearApplication clear = new ClearApplication();
        BaseResponse clearResponse = clear.clearService();
    }

    @Test
    @DisplayName("List Games")
    public void ListGamesTest() throws DataAccessException, ServerErrorException {

        ListGames listGames = new ListGames();

        // Test with no auth token
        ListGameRequest request = new ListGameRequest(null);
        ListGamesResponse response = listGames.listGamesService(request);

        Assertions.assertTrue((response.getMessage().equals("Error: unauthorized")), "Expected Error: unauthorized");

        // Now we should succeed
        Register registerService = new Register();
        RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");

        RegisterResponse registerResponse = registerService.registerService(registerRequest);
        request.setAuthToken(registerResponse.getAuthToken());
        response = listGames.listGamesService(request);

        Assertions.assertTrue((response.getMessage() == null), "Message should be null but got some other value");

        ClearApplication clear = new ClearApplication();
        BaseResponse clearResponse = clear.clearService();

    }

    @Test
    @DisplayName("Login")
    public void LoginTest() throws DataAccessException, ServerErrorException {

        LoginRequest request = new LoginRequest("username", "password");
        Login service = new Login();
        LogInResponse response = service.loginService(request);

        // Test without registering beforehand

        Assertions.assertTrue((response.getMessage().equals("Error: unauthorized")), "Expected Error: unauthorized");

        // Now we should succeed
        Register registerService = new Register();
        RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");

        LoginRequest request2 = new LoginRequest("username", "password");
        RegisterResponse registerResponse = registerService.registerService(registerRequest);
        response = service.loginService(request2);

        Assertions.assertTrue((response.getMessage() == null), "Message should be null but got some other value");

        ClearApplication clear = new ClearApplication();
        BaseResponse clearResponse = clear.clearService();

    }

    @Test
    @DisplayName("Logout")
    public void LogoutTest() throws DataAccessException, ServerErrorException {

        LogoutRequest request = new LogoutRequest(null);
        Logout service = new Logout();
        LogoutResponse response = service.logoutService(request);

        // Test without registering beforehand

        Assertions.assertTrue((response.getMessage().equals("Error: unauthorized")), "Expected Error: unauthorized");

        // Now we should succeed
        Register registerService = new Register();
        RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");


        RegisterResponse registerResponse = registerService.registerService(registerRequest);
        LogoutRequest request2 = new LogoutRequest(registerResponse.getAuthToken());
        response = service.logoutService(request2);

        Assertions.assertTrue((response.getMessage() == null), "Message should be null but got some other value");

        ClearApplication clear = new ClearApplication();
        BaseResponse clearResponse = clear.clearService();

    }

    @Test
    @DisplayName("Register")
    public void RegisterTest() throws DataAccessException, ServerErrorException {

        Register registerService = new Register();
        RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
        RegisterResponse response = registerService.registerService(registerRequest);

        Assertions.assertTrue((response.getMessage() == null), "Message should be null but got some other value");

        // Test without registering beforehand

        Register registerService2 = new Register();
        RegisterRequest registerRequest2 = new RegisterRequest("username", "password", "email");
        RegisterResponse response2 = registerService2.registerService(registerRequest2);

        Assertions.assertTrue((response2.getMessage().equals("Error: already taken")), "Expected Error: unauthorized");

        ClearApplication clear = new ClearApplication();
        BaseResponse clearResponse = clear.clearService();


    }

}
