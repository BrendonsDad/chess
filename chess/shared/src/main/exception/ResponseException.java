package exception;

public class ResponseException extends Exception{
    //final private int statusCode;
    String message;

    public ResponseException(int statusCode, String message) {
        super(message);
        //this.statusCode = statusCode;
        this.message = message;
    }

    public String StatusCode() {
        return message;
    }
}
