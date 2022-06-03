package g6.ppacg6.exceptions;

/** Exception related to the Topic Class */
public class TopicException extends Exception {

    /** Constructor */
    public TopicException() {
    }

    /** Constructor with message */
    public TopicException(String message) {
        super(message);
    }

    /** Constructor with message and cause */
    public TopicException(String message, Throwable cause) {
        super(message, cause);
    }
}
