package g6.ppacg6.exceptions;

/** Exception related to the Room Class */
public class RoomException extends Exception {

    /** Constructor */
    public RoomException() {
    }

    /** Constructor with message */
    public RoomException(String message) {
        super(message);
    }

    /** Constructor with message and cause */
    public RoomException(String message, Throwable cause) {
        super(message, cause);
    }

}
