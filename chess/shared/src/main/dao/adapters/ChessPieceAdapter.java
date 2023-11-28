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
        //pieceType.equals("PAWN");

        String pieceType = el.getAsJsonObject().get("myType").getAsString();
        
        if (pieceType.equals("PAWN")) {

            return ctx.deserialize(el, Pawn.class);

        } else if (pieceType.equals("BISHOP")) {

            return ctx.deserialize(el, Bishop.class);

        } else if (pieceType.equals("KING")) {

            return ctx.deserialize(el, King.class);

        } else if (pieceType.equals("KNIGHT")) {

            return ctx.deserialize(el, Knight.class);

        } else if (pieceType.equals("QUEEN")) {

            return ctx.deserialize(el, Queen.class);

        } else if (pieceType.equals("ROOK")) {

            return ctx.deserialize(el, Rook.class);

        }
        else {
            return ctx.deserialize(el, ChessPiece.class);
        }
    }
}
