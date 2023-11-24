package dao.adapters;
import chess.ChessPiece;
import chess.Pawn;
import chess.*;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessPieceAdapter implements JsonDeserializer<ChessPiece> {
    @Override
    public ChessPiece deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        // Ask the TA about this
        //look at the json string and find the piece type within the string to find out what class

        // You should be able to dubug to see what this is
        el.getAsString().contains("PAWN");


        if (el.getAsString().contains("PAWN")) {

            return new Gson().fromJson(el, Pawn.class);

        } else if (el.getAsString().contains("BISHOP")) {

            return new Gson().fromJson(el, Bishop.class);

        } else if (el.getAsString().contains("KING")) {

            return new Gson().fromJson(el, King.class);

        } else if (el.getAsString().contains("KNIGHT")) {

            return new Gson().fromJson(el, Knight.class);

        } else if (el.getAsString().contains("QUEEN")) {

            return new Gson().fromJson(el, Queen.class);

        } else if (el.getAsString().contains("ROOK")) {

            return new Gson().fromJson(el, Rook.class);

        }
        else {
            return new Gson().fromJson(el, ChessPiece.class);
        }
    }
}
