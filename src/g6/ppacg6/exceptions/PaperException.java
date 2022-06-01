package g6.ppacg6.exceptions;

public class PaperException extends Exception{

        public PaperException() {
        }

        public PaperException(String message) {
                super(message);
        }

        public PaperException(String message, Throwable cause) {
                super(message, cause);
        }
}
