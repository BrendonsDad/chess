package model;

/**
 * Part of the model package. The User class will hold vital information about our player.
 * This class will hold no logic, only information.
 */
public class User {
    /**
     * username is the identifier we associate with the player.
     */
    private String username;
    /**
     * password is the players personal code given to log in to their account.
     */
    private String password;
    /**
     * email will hold the players email address.
     */
    private String email;


    /**
     *This is a constructor for our User model. It builds the model and fills the fields.
     * @param username: Input given that wil represent our objects username field.
     * @param password: Input given that wil represent our objects password field.
     * @param email: Input given that wil represent our objects username email.
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
