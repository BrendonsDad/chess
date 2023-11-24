package handlers;
import com.google.gson.Gson;
import requests.LoginRequest;
import responses.LogInResponse;
import services.Login;
import spark.Request;
import spark.Response;
import spark.Route;


/**
 * The LoginHandler deserialzes the HTTP request and send it to the Login
 * service in order return a response
 */
public class LoginHandler implements Route {


    @Override
    public Object handle(Request request, Response response) throws Exception {

        Gson gson = new Gson();

        // make a new java object rom the incoming JSON request
        LoginRequest newRequest = (LoginRequest)gson.fromJson(request.body(), LoginRequest.class);

        // Initialize the login service
        Login service = new Login();
        // Make a response object using the service
        LogInResponse newResponse = new LogInResponse(null, null, null);

        try {
            newResponse = service.loginService(newRequest);
            // Check what the response looks like and change the status based off that.
            if (newResponse.getMessage() == null) {
                // how do I update the status code? 200
                response.status(200);
            }
            else if (newResponse.getMessage() == "Error: unauthorized") {
                // Set the code to 401
                response.status(401);

            }
            else {
                // Set the code to 500
                response.status(500);

            }
        }catch (Exception e) {
            response.status(500);
            newResponse.setMessage("Error: " + e.getMessage());
        }


        // return the Json object from our java object
        return gson.toJson(newResponse);

    }
}





