package responses;

/**
 * Holds the information we will send to the user which will contain differing messages
 * based on if the request failed, and if it did, why.
 */
public class CreateGameResponse {

    /**
     * The message we will send back. This can be one of the three messages that could occur.
     */
    private String message;
    private Integer gameID;

    /**
     * Sets our message to be whatever we need based on code in the service indicating if
     * this code fails or succeeds.
     * @param message : The error message or null if the request runs successfully
     */
    public CreateGameResponse(String message, Integer gameID) {

        this.message = message;
        this.gameID = gameID;
    }

    public String getMessage() {
        return message;
    }

    public Integer getgameID() {
        return gameID;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}