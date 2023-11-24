package handlers;
import com.google.gson.Gson;
import requests.RegisterRequest;
import responses.RegisterResponse;
import services.Register;
import spark.Request;
import spark.Response;
import spark.Route;


/**
 * The RegisterHandler deserialzes the HTTP request and send it to the Register
 * service in order return a response
 */
public class RegisterHandler implements Route {


    @Override
    public Object handle(Request request, Response response) throws Exception {

        Gson gson = new Gson();

        // make a new java object rom the incoming JSON request
        RegisterRequest newRequest = (RegisterRequest) gson.fromJson(request.body(), RegisterRequest.class);

        // Initialize the login service
        Register service = new Register();
        // Make a response object using the service
        RegisterResponse newResponse = new RegisterResponse(null, null, null);

        try {
            newResponse = service.registerService(newRequest);
            // Check what the response looks like and change the status based off that.
            if (newResponse.getMessage() == null) {
                // how do I update the status code? 200
                response.status(200);
            }
            else if (newResponse.getMessage() == "Error: bad request") {
                // Set the code to 400
                response.status(400);

            }
            else if (newResponse.getMessage() == "Error: already taken") {
                // Set the code to 403
                response.status(403);
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
