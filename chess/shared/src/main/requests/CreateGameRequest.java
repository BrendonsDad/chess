package requests;

import responses.CreateGameResponse;

/**
 * CreateGameRequest takes the info taken from JSON and turns that into code we can use to
 * formulate our response
 */
public class CreateGameRequest {
    /**
     * String holding the name or our game that we will play or observe.
     */
    private String gameName;

    /**
     * This constructor helps build our response depending on what is passed into gameName
     * @param gameName: A String that will help us determine our own gameName
     */
    public CreateGameRequest(String gameName) {
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setName(String gameName) {
        this.gameName = gameName;
    }
}
