package handlers;

import com.google.gson.Gson;
import requests.JoinGameRequest;
import responses.JoinGameResponse;
import services.JoinGame;
import spark.Request;
import spark.Response;

/**
 * The JoinGameHAndler deserialzes the HTTP request and send it to the JoinGame
 * service in order return a response
 */
public class JoinGameHandler extends Handler{
    @Override
    public Object handle(Request request, Response response) throws Exception {

        Gson gson = new Gson();

        // Initialize the login service
        JoinGame service = new JoinGame();
        // Make a response object using the service
        JoinGameResponse newResponse = new JoinGameResponse("Error: unauthorized");
        JoinGameRequest newRequest = (JoinGameRequest) gson.fromJson(request.body(), JoinGameRequest.class);

        try {
            boolean isAuthenticated = super.Authenticate(request);
            if (isAuthenticated) {

                newRequest.setAuthToken(request.headers("authorization"));

                newResponse = service.joinGameService(newRequest);
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
            } else if (newResponse.getMessage().equals("Error: already taken")) {
                // Set the code to 403
                response.status(403);
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

