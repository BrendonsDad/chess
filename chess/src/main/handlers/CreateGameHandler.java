package handlers;

import com.google.gson.Gson;
import requests.CreateGameRequest;
import responses.CreateGameResponse;
import services.CreateGame;
import spark.Request;
import spark.Response;

/**
 * The CreateGameHandler deserialzes the HTTP request and send it to the CreateGame
 * service in order return a response
 */
public class CreateGameHandler extends Handler {


    @Override
    public Object handle(Request request, Response response) throws Exception {

        Gson gson = new Gson();

        // Initialize the login service
        CreateGame service = new CreateGame();
        // Make a response object using the service
        CreateGameResponse newResponse = new CreateGameResponse("Error: unauthorized", null);
        CreateGameRequest newRequest = (CreateGameRequest) gson.fromJson(request.body(), CreateGameRequest.class);

        try {
            boolean isAuthenticated = super.Authenticate(request);
            if (isAuthenticated) {

                newResponse = service.createGameService(newRequest);
            }
            // Check what the response looks like and change the status based off that.
            if (newResponse.getMessage() == null) {
                // how do I update the status code? 200
                response.status(200);
            } else if (newResponse.getMessage().equals("Error: bad request")) {
                // Set the code to 400
                response.status(400);
            } else if (newResponse.getMessage().equals("Error: unauthorized")) {
                // Set the code to 401
                response.status(401);
            } else {
                // Set the code to 500
                response.status(500);

            }
        } catch (Exception e) {
            response.status(500);
            newResponse.setMessage("Error: " + e.getMessage());
        }

        return gson.toJson(newResponse);
    }

}
