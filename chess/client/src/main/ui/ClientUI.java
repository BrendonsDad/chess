package ui;

import exception.ResponseException;
import requests.RegisterRequest;
import responses.RegisterResponse;
import serverFac.ServerFacade;

import java.util.Arrays;

public class ClientUI {
    private String serverUrl;
    private final ServerFacade server;
    private String authToken;
    private State state = State.LOGGED_OUT;

    public ClientUI(String serverUrl) {
        this.serverUrl = serverUrl;
        this.server = new ServerFacade(serverUrl);
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "regiser" -> register(params);
                case "login" -> login(params);
                case "create" -> createGame(params);
                case "list" -> listGames();
                case "join" -> joinGame(params);
                case "observe" -> observeGame(params);
                case "logout" -> logout();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String register(String... params) throws ResponseException {
        int numOfParams = params.length;
        if (numOfParams == 3) {
            // Make a request object and send it to the facade
            var username = params[0];
            var password = params[1];
            var email = params[2];
            RegisterRequest regRequest = new RegisterRequest(username, password, email);
            RegisterResponse response = this.server.register(regRequest);

            authToken = response.getAuthToken();
            state = State.LOGGED_IN;
            return String.format("Thank you for joining us %s, you are now logged in", response.getUsername());
        }
        //figure a way to get num of parames in here.
        throw new ResponseException(400, "Expected: <USERNAME> <PASSWORD> <EMAIL>  \nIf you do not wish to give an email, type NA.");

    }

    public String login(String... params) throws ResponseException {
        state = State.LOGGED_IN;
        return "";
    }

    public String createGame(String... params) throws ResponseException {
        assertSignedIn();
        return "";
    }

    public String listGames() throws ResponseException {
        assertSignedIn();
        return "";
    }

    public String joinGame(String... params) throws ResponseException {
        assertSignedIn();
        state = State.IN_GAME_PLAYER;
        return "";
    }

    public String observeGame(String... params) throws ResponseException {
        assertSignedIn();
        state = State.IN_GAME_OBSERVER;
        return "";
    }

    public String logout() throws ResponseException {
        assertSignedIn();
        state = State.LOGGED_OUT;
        return "";
    }

    public String returnState() {
        if (state == State.LOGGED_OUT) {
            return "[LOGGED_OUT] >>> ";
        }
        else if (state == State.LOGGED_IN) {
            return "[LOGGED_IN] >>> ";
        }
        else if (state == State.IN_GAME_OBSERVER) {
            return "[IN_GAME_OBSERVER] >>> ";
        }
        else {
            return "[IN_GAME_PLAYER] >>> ";
        }
    }

    public String help() {
        if (state == State.LOGGED_OUT) {
            return """
                   register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                   login <USERNAME> <PASSWORD> - to play chess
                   quit - playing chess
                   help with possible commands
                   
                   """;
        }
        else {
            return """
                    create <NAME> - a game
                    list - games
                    join <ID> [WHITE|BLACK|<empty>] - a game
                    observe <ID> - a game
                    logout - when you are done
                    quit - playing chess
                    help - with possible commands
                    
                    """;
        }
    }

    public void assertSignedIn() throws ResponseException {
        if (state == State.LOGGED_OUT) {
            throw new ResponseException(400, "You must sign in");
        }
    }
}
