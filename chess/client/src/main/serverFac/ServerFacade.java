package serverFac;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.adapters.ChessBoardAdapter;
import dao.adapters.ChessPieceAdapter;
import dao.adapters.GameAdapter;
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

    public RegisterResponse register(RegisterRequest request) throws ResponseException {
        //POST
        //path: /user
        var path = "/user";
        return this.makeRequest("POST", path, request, RegisterResponse.class, null);
    }

    public void clearDataBase(String authToken) throws URISyntaxException, IOException {
        //DELETE
        //path: /db
        var path = "/db";
        var method = "DELETE";

        URL url = (new URI(serverUrl + path)).toURL();
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod(method);
        //What does this do? vv
        http.setDoOutput(true);
        if (authToken != null) {
            http.setRequestProperty("Authorization", authToken);
        }


        http.connect();
        try {
            throwIfNotSuccessful(http);
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }


    }

    public LogInResponse login(LoginRequest request) throws ResponseException {
        //POST
        //path: /session
        var path = "/session";
        return  this.makeRequest("POST", path, request, LogInResponse.class, null);
    }

    public LogoutResponse logout(LogoutRequest request) throws ResponseException {
        //DELETE
        //path: /session
        var path = "/session";
        return this.makeRequest("DELETE", path, request, LogoutResponse.class, request.getAuthToken());
    }

    public ListGamesResponse listGames(ListGameRequest request) throws ResponseException {
        //GET
        //path: /game
        var path = "/game";
        return this.makeRequest("GET", path, request, ListGamesResponse.class, request.getAuthToken());

    }

    public CreateGameResponse createGame(CreateGameRequest request, String authToken) throws ResponseException {
        //POST
        //path: /game
        var path = "/game";
        return this.makeRequest("POST", path, request, CreateGameResponse.class, authToken);
    }

    public JoinGameResponse joinGame(JoinGameRequest request) throws ResponseException {
        //PUT
        //path: /game
        var path = "/game";



        return this.makeRequest("PUT", path, request, JoinGameResponse.class, request.getAuthToken());
    }

    public int getStatusCode(String method, String path, String authToken, Object request) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            //What does this do? vv
            http.setDoOutput(true);
            if (authToken != null) {
                http.setRequestProperty("Authorization", authToken);
            }

            if (!method.equals("GET")) {
                writeBody(request, http);
            }

            http.connect();
            //throwIfNotSuccessful(http);
            return http.getResponseCode();
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String authToken) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            //What does this do? vv
            http.setDoOutput(true);
            if (authToken != null) {
                http.setRequestProperty("Authorization", authToken);
            }

            if (!method.equals("GET")) {
                writeBody(request, http);
            }

            http.connect();
            //throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private  <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    if (responseClass.equals(ListGamesResponse.class)) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        // gsonBuilder.registerTypeAdapter(ChessGame.class, new GameAdapter());
                        gsonBuilder.registerTypeAdapter(ChessBoard.class, new ChessBoardAdapter());
                        gsonBuilder.registerTypeAdapter(ChessPiece.class, new ChessPieceAdapter());

                        response = gsonBuilder.create().fromJson(reader, responseClass);
                    }
                    else {
                        Gson gson = new Gson();
                        response = gson.fromJson(reader, responseClass);
                    }

                }
            }
        }
        return response;
    }

    private boolean isSuccessful(int status) { return status / 100 == 2;}
}
