package requests;

public class LogoutRequest {

    /**
     * The authToken which identifies if the user is valid.
     */
    String authToken;

    /**
     * Takes in a string representing the authToken of the given request
     * @param authToken
     */
    public LogoutRequest(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }
}
