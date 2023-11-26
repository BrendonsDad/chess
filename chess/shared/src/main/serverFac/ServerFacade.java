package serverFac;

import exception.ResponseException;
import requests.*;
import responses.*;

import java.io.*;
import java.net.*;

public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public RegisterResponse register(RegisterRequest request) {
        //POST
        //path: /user
    }

    public LogInResponse login(LoginRequest request) {
        //POST
        //path: /session
    }

    public LogoutResponse logout(LogoutRequest request) {
        //DELETE
        //path: /session
    }

    public ListGamesResponse listGames(ListGameRequest request) {
        //GET
        //path: /game

    }

    public CreateGameResponse createGame(CreateGameRequest request) {
        //POST
        //path: /game
    }

    public JoinGameResponse joinGame(JoinGameRequest request) {
        //PUT
        //path: /game
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            //What does this do? vv
            http.setDoOutput(true);

            writeBody()
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
}
