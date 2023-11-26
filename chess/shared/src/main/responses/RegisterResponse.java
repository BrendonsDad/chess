package responses;

/**
 * Holds the information we will send to the user which will contain differing messages
 * based on if the request failed, and if it did, why.
 */
public class RegisterResponse  {

    /**
     * The message we will send back. This can be one of the four messages that could occur.
     */
    private String message;
    private String username;

    private String authToken;

    /**
     * Sets our message to be whatever we need based on code in the service indicating if
     * this code fails or succeeds.
     * @param message : The error message or null if the request runs successfully
     */
    public RegisterResponse(String message, String authToken, String username) {

        this.message = message;
        this.authToken = authToken;
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthToken() {return authToken;}

    public void setMessage(String message) {
        this.message = message;
    }
}

