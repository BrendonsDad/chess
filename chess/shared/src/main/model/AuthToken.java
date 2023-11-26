package model;

/**
 * Part of the model package. The AuthToken class will hold important information about our user's authentication.
 * This class will hold no logic, only information.
 */
public class AuthToken {
    /**
     * authToken holds the authentication token for the associated user.
     */
    private String authToken;
    /**
     * username holds the username of the current user.
     */
    private String username;

    /**
     * The AuthToken Constructor. This fills the fields of authToken and username with the given input.
     * @param authToken: Input given that wil represent our objects authToken field.
     * @param username: Input given that wil represent our objects username field.
     */
    public AuthToken(String authToken, String username) {
        this.authToken = authToken;
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUsername() {
        return username;
    }
}
