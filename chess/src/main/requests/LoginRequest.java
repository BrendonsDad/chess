package requests;

/**
 * The request object for Login. This Object does holds fields for the username
 * and password
 */
public class LoginRequest {
    /**
     * username identifies who the player is
     */
    private String username;

    /**
     * password gives that player a login credential
     */
    private String password;

    /**
     * Constructor that takes in the information from a JSON file to create this object.
     * @param username: We will pass this value into our own username.
     * @param password: We will pass this value into our own password.
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
