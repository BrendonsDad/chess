package dao.adapters;

import chess.ChessBoard;
import chess.ChessBoardImpl;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessBoardAdapter implements JsonDeserializer<ChessBoard> {
    @Override
    public ChessBoard deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        // Ask the TA about this
        return new Gson().fromJson(el, ChessBoardImpl.class);
    }
}
