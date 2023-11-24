package server;

import handlers.*;
import spark.Spark;


public class Server {

    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {
        // Specify the port you want the server to listen on
        Spark.port(8080);

        // Register a directory for hosting static files
        Spark.externalStaticFileLocation("web");


        // Login
        Spark.post("/session", new LoginHandler());

        // Clear
        Spark.delete("/db", new ClearApplicationHandler());

        // Register
        Spark.post("/user", new RegisterHandler());

        // Logout
        Spark.delete("/session", new LogoutHandler());

        // List Games
        Spark.get("/game", new ListGamesHandler());

        // Create Game
        Spark.post("/game", new CreateGameHandler());

        // Join a Chess Game
        Spark.put("/game", new JoinGameHandler());

    }
}
