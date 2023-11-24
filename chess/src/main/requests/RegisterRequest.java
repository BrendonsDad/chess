package requests;

/**
 * This class is made so that our RegisterService will know how to make a propore repsonse
 * given the JSON object.
 */
public class RegisterRequest {

    /**
     * Contains our objects value for our players username
     */
    private String username = null;
    /**
     * Contains our objects value for our players password
     */
    private String password = null;
    /**
     * Contains our objects value for our players email
     */
    private String email = null;


    /**
     * Constructor which we use to fill the values of our objects username, password, and email with the input.
     * @param username : param containing the value of our username
     * @param password : param containing the value of our password
     * @param email : param containing the value of our email
     */
    public RegisterRequest(String username, String password, String email) {
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
