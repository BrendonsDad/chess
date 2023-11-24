package handlers;

import com.google.gson.Gson;
import requests.ListGameRequest;
import responses.ListGamesResponse;
import services.ListGames;
import spark.Request;
import spark.Response;

/**
 * The ListGamesHandler deserialzes the HTTP request and send it to the ListGame
 * service in order return a response
 */
public class ListGamesHandler  extends Handler{
    @Override
    public Object handle(Request request, Response response) throws Exception {

        Gson gson = new Gson();

        // Initialize the login service
        ListGames service = new ListGames();
        // Make a response object using the service
        ListGamesResponse newResponse = new ListGamesResponse("Error: unauthorized", null);

        try {
            boolean isAuthenticated = super.Authenticate(request);
            if (isAuthenticated) {
                ListGameRequest newRequest = new ListGameRequest(request.headers("authorization"));
                newResponse = service.listGamesService(newRequest);
            }
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

        return gson.toJson(newResponse);


    }
}