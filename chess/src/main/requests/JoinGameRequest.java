package requests;

/**
 * This class contains all the information we need to make a response upon a request
 * to join a game
 */
public class JoinGameRequest {
    /**
     * Stores the color of the given player who wishes to join the game
     */
    private String playerColor;

    /**
     * Stores the game ID the player wishes to join
     */
    private int gameID;

    private transient String authToken;


    /**
     * A constructor which fills the values of our classes playerColor and gameID with the given input.
     * @param playerColor : holds the value of our objects player color
     * @param gameID : holds the value of our objects game ID
     */
    public JoinGameRequest(String playerColor, int gameID) {
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public int getGameID() {
        return gameID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}
