package g6.ppacg6.exceptions;

/** Exception related to the Paper Class */
public class PaperException extends Exception{

        /** Constructor */
        public PaperException() {
        }

        /** Constructor with message */
        public PaperException(String message) {
                super(message);
        }

        /** Constructor with message and cause */
        public PaperException(String message, Throwable cause) {
                super(message, cause);
        }
}
