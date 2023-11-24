package handlers;

import com.google.gson.Gson;
import responses.BaseResponse;
import services.ClearApplication;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * The CLearApplicationHandler deserialzes the HTTP request and send it to the ClearApplication
 * service in order return a response
 */
public class ClearApplicationHandler implements Route {
    @Override
    public Object handle(Request request, Response response)  {

        Gson gson = new Gson();


        ClearApplication clearService = new ClearApplication();

        BaseResponse responseBody = new BaseResponse(null);

        try {
            responseBody = clearService.clearService();
            response.status(200);
        } catch (Exception e) {
            response.status(500);
            responseBody.setMessage("Error: " + e.getMessage());
        }

        return gson.toJson(responseBody);


    }
}
