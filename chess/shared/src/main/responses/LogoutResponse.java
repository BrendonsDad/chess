package responses;

/**
 * Holds the information we will send to the user which will contain differing messages
 * based on if the request failed, and if it did, why.
 */
public class LogoutResponse {

    /**
     * The message we will send back. This can be one of the two messages that could occur.
     */
    private String message;

    /**
     * Sets our message to be whatever we need based on code in the service indicating if
     * this code fails or succeeds.
     * @param message : The error message or null if the request runs successfully
     */
    public LogoutResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}