package ui;

import chess.*;
import exception.ResponseException;
import model.Game;
import requests.*;
import responses.*;
import serverFac.ServerFacade;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import static ui.EscapeSequences.*;

public class ClientUI {
    private final ServerFacade server;
    private static String authToken;
    private State state = State.LOGGED_OUT;

//    private EscapeSequences es = new EscapeSequences();
    private String chessSquareBackground = SET_BG_COLOR_DARK_GREEN;

    public ClientUI(String serverUrl) {

        this.server = new ServerFacade(serverUrl);
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "register" -> register(params);
                case "login" -> login(params);
                case "create" -> createGame(params);
                case "list" -> listGames();
                case "join" -> joinGame(params);
                case "observe" -> observeGame(params);
                case "logout" -> logout();
                case "clearall" -> clearAll();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException | IOException ex) {
            return ex.getMessage();
        }
    }

    public String  clearAll() {
        StringBuilder response = new StringBuilder();
        try {
            server.clearDataBase(authToken);
            response.append("Database cleared\n");
        }
        catch (Exception e) {
            response.append(e);
        }

        return response.toString();

    }

    private void throwIfNotSuccessful(String response) throws IOException, ResponseException {

        if (response != null) {
            throw new ResponseException(0,response);
        }
    }

    public String register(String... params) throws ResponseException, IOException {
        int numOfParams = params.length;
        if (numOfParams == 3) {
            // Make a request object and send it to the facade
            var username = params[0];
            var password = params[1];
            var email = params[2];
            RegisterRequest regRequest = new RegisterRequest(username, password, email);
            RegisterResponse response = this.server.register(regRequest);
            throwIfNotSuccessful(response.getMessage());

            authToken = response.getAuthToken();
            state = State.LOGGED_IN;
            return String.format("Thank you for joining us %s, you are now logged in", response.getUsername());
        }
        //figure a way to get num of parames in here.
        throw new ResponseException(400, "Expected: <USERNAME> <PASSWORD> <EMAIL>  \nIf you do not wish to give an email, type NA.");

    }

    public String login(String... params) throws ResponseException, IOException {
        int numOfParams = params.length;
        if (numOfParams == 2) {

            var username = params[0];
            var password = params[1];
            LoginRequest logRequest = new LoginRequest(username, password);
            LogInResponse response = this.server.login(logRequest);

            throwIfNotSuccessful(response.getMessage());

            authToken = response.getAuthToken();

            state = State.LOGGED_IN;
            return "";
        }
        throw new ResponseException(400, "Expected: <USERNAME> <PASSWORD>");
    }

    public String createGame(String... params) throws ResponseException, IOException {
        //1 param
        int numOfParams = params.length;
        if (numOfParams == 1) {
            assertSignedIn();
            var gameName = params[0];
            CreateGameRequest createRequest = new CreateGameRequest(gameName);
            CreateGameResponse response = this.server.createGame(createRequest, authToken);

            throwIfNotSuccessful(response.getMessage());
            return String.format("%s has been made with ID of %d", gameName, response.getgameID());
        }
        throw new ResponseException(400, "Expected: <NAME>");
    }

    public String listGames() throws ResponseException, IOException {
        //no param
        assertSignedIn();
        StringBuilder stringBuilder = new StringBuilder();
        ListGameRequest listRequest = new ListGameRequest(authToken);
        ListGamesResponse response = server.listGames(listRequest);

        throwIfNotSuccessful(response.getMessage());

        ArrayList<Game> games = response.getGames();

        if (games.isEmpty()) {
            return "There are no active games\n";
        }
        else {

            for (Game game : games) {
                int gameId = game.getGameID();
                String gameName = game.getGameName();
                String whiteName = game.getWhiteUsername();
                String blackName = game.getBlackUsername();

                //Game ID
                String IdToString = String.format("%d", gameId);
                stringBuilder.append("\nGame ID: ");
                stringBuilder.append(IdToString);
                stringBuilder.append("\n");

                //Game Name
                stringBuilder.append("Game Name: ");
                stringBuilder.append(gameName);
                stringBuilder.append("\n");

                //White username
                stringBuilder.append("White username: ");
                stringBuilder.append(whiteName);
                stringBuilder.append("\n");

                //Black username
                stringBuilder.append("Black username: ");
                stringBuilder.append(blackName);
                stringBuilder.append("\n");
            }
            stringBuilder.append("\n");
            return stringBuilder.toString();
        }
    }

    public String joinGame(String... params) throws ResponseException, IOException {
        // >= 1
        int numOfParams = params.length;
        if (numOfParams >= 1) {
            String gameID = params[0];
            int intID = Integer.parseInt(gameID);
            JoinGameRequest joinRequest;
            String conclusion;

            if (params.length == 1) {

                joinRequest = new JoinGameRequest(null, intID);
                conclusion = "You have joined as an observer";
            } else {
                joinRequest = new JoinGameRequest(params[1].toUpperCase(), intID);
                conclusion = "You have joined as a player";
            }
            joinRequest.setAuthToken(authToken);
            assertSignedIn();
            JoinGameResponse response = server.joinGame(joinRequest);

            throwIfNotSuccessful(response.getMessage());

            state = State.IN_GAME_PLAYER;

            ListGameRequest listRequest = new ListGameRequest(authToken);
            ListGamesResponse listResponse = server.listGames(listRequest);
            ArrayList<Game> games = listResponse.getGames();
            Game myGame = games.get(intID - 1);
            ChessGameImpl gameInQuestion = myGame.getGame();
            ChessBoard boardInQuestion = gameInQuestion.getBoard();

            return writeBoard(conclusion, boardInQuestion);
        }
        throw new ResponseException(400, "Expected: <ID> [WHITE|BLACK|<empty>]");
    }

    public String observeGame(String... params) throws ResponseException, IOException {
        // 1 param
        if (params.length == 1) {
            assertSignedIn();
            String gameID = params[0];
            int intID = Integer.parseInt(gameID);
            String conclusion = "You have joined as an observer";

            state = State.IN_GAME_OBSERVER;

            JoinGameRequest joinRequest = new JoinGameRequest(null,intID);
            joinRequest.setAuthToken(authToken);
            JoinGameResponse response = server.joinGame(joinRequest);

            throwIfNotSuccessful(response.getMessage());

            ListGameRequest listRequest = new ListGameRequest(authToken);
            ListGamesResponse listResponse = server.listGames(listRequest);
            ArrayList<Game> games = listResponse.getGames();
            Game myGame = games.get(intID - 1);
            ChessGameImpl gameInQuestion = myGame.getGame();
            ChessBoard boardInQuestion = gameInQuestion.getBoard();

            return writeBoard(conclusion, boardInQuestion);
        }
        throw new ResponseException(400, "Expected: <ID>");
    }

    private String writeBoard(String StringBuildEnder, ChessBoard board) {
        StringBuilder stringBuilder = new StringBuilder();
        //background of each peice is the same regardless of black or white.
        // loop through the board the first time, if black print blck, if white print white
        //header and footer are the same
        String whiteHeaderFooter = "\u001b[30;100m    a \u2003b \u2003c \u2003d \u2003e \u2003f \u2003g \u2003h  \u2003 \u001b[35;40m\n";
        String blackHeaderFooter = "\u001b[30;100m    h \u2003g \u2003f \u2003e \u2003d \u2003c \u2003b \u2003a \u2003  \u001b[35;40m\n";

        int colNum = 8;
        int col = 0;
        int row = 0;

        stringBuilder.append(whiteHeaderFooter);
        for (row = 7; row > -1; row--) {
            String colLeftBack = SET_BG_COLOR_LIGHT_GREY;
            String colLeftFontColor = SET_TEXT_COLOR_BLACK;
            String colLeftLetter = String.format(" %d ", colNum);
            stringBuilder.append(colLeftBack);
            stringBuilder.append(colLeftFontColor);
            stringBuilder.append(colLeftLetter);

            for (col = 0; col < board.getboard().length; col++) {
                toggle();
                stringBuilder.append(chessSquareBackground);
                if (board.getboard()[row][col] == null) {
                    stringBuilder.append(EMPTY);
                }
                else {
                    // Make the appropriate color
                    if (board.getboard()[row][col].getTeamColor() == ChessGame.TeamColor.WHITE) {
                        stringBuilder.append(SET_TEXT_COLOR_WHITE);
                    }
                    else {
                        stringBuilder.append(SET_TEXT_COLOR_BLACK);
                    }

                    //Setting the pieces
                    if (board.getboard()[row][col].getPieceType() == ChessPiece.PieceType.PAWN) {
                        stringBuilder.append(BLACK_PAWN);
                    }
                    else if (board.getboard()[row][col].getPieceType() == ChessPiece.PieceType.ROOK) {
                        stringBuilder.append(BLACK_ROOK);
                    }
                    else if (board.getboard()[row][col].getPieceType() == ChessPiece.PieceType.KING) {
                        stringBuilder.append(BLACK_KING);
                    }
                    else if (board.getboard()[row][col].getPieceType() == ChessPiece.PieceType.QUEEN) {
                        stringBuilder.append(BLACK_QUEEN);
                    }
                    else if (board.getboard()[row][col].getPieceType() == ChessPiece.PieceType.BISHOP) {
                        stringBuilder.append(BLACK_BISHOP);
                    }
                    else if (board.getboard()[row][col].getPieceType() == ChessPiece.PieceType.KNIGHT) {
                        stringBuilder.append(BLACK_KNIGHT);
                    }
                }

            }
            String colRightBack = SET_BG_COLOR_LIGHT_GREY;
            String colRightFontColor = SET_TEXT_COLOR_BLACK;
            String colRightLetter = String.format(" %d ", colNum);
            stringBuilder.append(colRightBack);
            stringBuilder.append(colRightFontColor);
            stringBuilder.append(colRightLetter);
            stringBuilder.append(SET_BG_COLOR_BLACK);
            stringBuilder.append("\n");
            colNum -= 1;
            toggle();
        }
        stringBuilder.append(whiteHeaderFooter);
        stringBuilder.append("\n");
        stringBuilder.append("\n");
        // first (white) header is a b c d e f g h
        // side numbers start at 8 and count down
        // Second (black) header is h g f e d c b a
        // numbers start at 1 and count up to 8
        // see if you can make the second loop a backwards loop


        colNum = 1;
        col = 7;
        row = 7;

        stringBuilder.append(blackHeaderFooter);
        for (row = 0; row < board.getboard().length; row++) {
            String colLeftBack = SET_BG_COLOR_LIGHT_GREY;
            String colLeftFontColor = SET_TEXT_COLOR_BLACK;
            String colLeftLetter = String.format(" %d ", colNum);
            stringBuilder.append(colLeftBack);
            stringBuilder.append(colLeftFontColor);
            stringBuilder.append(colLeftLetter);

            for (col = 7; col > -1; col--) {
                toggle();
                stringBuilder.append(chessSquareBackground);
                if (board.getboard()[row][col] == null) {
                    stringBuilder.append(EMPTY);
                }
                else {
                    // Make the appropriate color
                    if (board.getboard()[row][col].getTeamColor() == ChessGame.TeamColor.WHITE) {
                        stringBuilder.append(SET_TEXT_COLOR_WHITE);
                    }
                    else {
                        stringBuilder.append(SET_TEXT_COLOR_BLACK);
                    }

                    //Setting the pieces
                    if (board.getboard()[row][col].getPieceType() == ChessPiece.PieceType.PAWN) {
                        stringBuilder.append(BLACK_PAWN);
                    }
                    else if (board.getboard()[row][col].getPieceType() == ChessPiece.PieceType.ROOK) {
                        stringBuilder.append(BLACK_ROOK);
                    }
                    else if (board.getboard()[row][col].getPieceType() == ChessPiece.PieceType.KING) {
                        stringBuilder.append(BLACK_KING);
                    }
                    else if (board.getboard()[row][col].getPieceType() == ChessPiece.PieceType.QUEEN) {
                        stringBuilder.append(BLACK_QUEEN);
                    }
                    else if (board.getboard()[row][col].getPieceType() == ChessPiece.PieceType.BISHOP) {
                        stringBuilder.append(BLACK_BISHOP);
                    }
                    else if (board.getboard()[row][col].getPieceType() == ChessPiece.PieceType.KNIGHT) {
                        stringBuilder.append(BLACK_KNIGHT);
                    }
                }

            }
            String colRightBack = SET_BG_COLOR_LIGHT_GREY;
            String colRightFontColor = SET_TEXT_COLOR_BLACK;
            String colRightLetter = String.format(" %d ", colNum);
            stringBuilder.append(colRightBack);
            stringBuilder.append(colRightFontColor);
            stringBuilder.append(colRightLetter);
            stringBuilder.append(SET_BG_COLOR_BLACK);
            stringBuilder.append("\n");
            colNum += 1;
            toggle();
        }
        stringBuilder.append(blackHeaderFooter);
        stringBuilder.append("\n");
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }

    private void toggle() {
        if (chessSquareBackground.equals(SET_BG_COLOR_GREEN)) {
            chessSquareBackground = SET_BG_COLOR_DARK_GREEN;
        }
        else {
            chessSquareBackground = SET_BG_COLOR_GREEN;
        }
    }

    public String logout() throws ResponseException, IOException {
        // no param
        assertSignedIn();
        LogoutRequest logRequest = new LogoutRequest(authToken);
        LogoutResponse response = this.server.logout(logRequest);

        throwIfNotSuccessful(response.getMessage());

        authToken = null;
        state = State.LOGGED_OUT;
        return "You have been logged out. \n\n\uD83D\uDC51 Welcome to 240 chess. Type Help to get started. \uD83D\uDC51 \n";
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
