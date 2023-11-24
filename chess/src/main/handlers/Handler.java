package handlers;

import dao.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.xml.crypto.Data;
import java.sql.SQLException;

/**
 * The Base Handler class contains an authentication method that can be inheritted to the
 * handlers who need it.
 */
public class Handler implements Route{
    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }

    //call returnConnection any time you are done.

    public boolean Authenticate(Request request) throws DataAccessException {
        Database myDatabase = new Database();

        try (var conn = myDatabase.getConnection();) {

            ///This is my original code////
            boolean authenticated = false;
            AuthDAO authDao = new AuthDAO(conn);

            if (!request.headers("authorization").equals("none") ) {
                if (authDao.findAuth(request.headers("authorization")) != null) {
                    authenticated = true;
                }
            }

            myDatabase.returnConnection(conn);
            return authenticated;
            //This is where my original code ends//

        } catch (SQLException e) {
            throw new DataAccessException("Error while trying to access data");
        }


    }

}

