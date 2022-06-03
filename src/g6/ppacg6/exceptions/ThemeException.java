package g6.ppacg6.exceptions;

/** Exception related to the Theme Class */
public class ThemeException extends Exception {

    /** Constructor */
    public ThemeException() {
    }

    /** Constructor with message */
    public ThemeException(String message) {
        super(message);
    }

    /** Constructor with message and cause */
    public ThemeException(String message, Throwable cause) {
        super(message, cause);
    }
}
