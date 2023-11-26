package requests;

/**
 * If this class is given an authentic token, it will be able to give our service all the info
 * it needs in order to List all the games
 */
public class ListGameRequest {
    /**
     * The token that we will use to check if our user can see the games
     */
    private String authToken;

    /**
     * Our constructor which will fill our class's authToken with the input of authToken
     * @param authToken
     */
    public ListGameRequest(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
