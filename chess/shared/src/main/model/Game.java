package model;

import chess.ChessGameImpl;
//Convert this into a chessgame string

/**
 * Part of the model package. The Game class will hold important information about our given game.
 * This class will hold no logic, only information.
 */
public class Game {
    /**
    gameID integer that holds the unique game ID.
     */
    private int gameID;
    /**
     * whiteUserame holds the username of the white player
     */
    private String whiteUsername = null;
    /**
     * blackUsername holds the username of the black player
     */
    private String blackUsername = null;
    /**
     * gameName holds the name of the specific game;
     */
    private String gameName;
    /**
     * game holds the game implementation for our chess game.
     */
    private ChessGameImpl game;

    /**
     * This Game constructor fills the fields for the Game model upon being called.
     * @param gameID: Input given that wil represent our objects gameID field.
     * @param gameName: Input given that wil represent our objects gameName field.
     * @param game: Input given that wil represent our objects game field.
     */
    public Game(String gameName, ChessGameImpl game) {
        this.gameName = gameName;
        this.game = game;
    }

    public int getGameID() {
        return gameID;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public String getGameName() {
        return gameName;
    }

    public ChessGameImpl getGame() {
        return game;
    }

    public void UpdateWhiteUserName(String name) {
        whiteUsername = name;
    }

    public void UpdateBlackUserName(String name) {
        blackUsername = name;
    }

    public void setGameID(int newId) {
        this.gameID = newId;
    }
}
