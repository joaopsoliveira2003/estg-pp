/*
 * Nome: Rui Alexandre Borba Vitorino
 * Número: 8190479
 * Turma: LSIRC12T1
 *
 * Nome: João Pedro Silva Oliveira
 * Número: 8210291
 * Turma: LSIRC11T2
 */

package exceptions;

/** Exception related to the Topic Class */
public class TopicException extends Exception {

    /**
     * Constructor
     */
    public TopicException() {
    }

    /**
     * Constructor with message
     * @param message custom message
     */
    public TopicException(String message) {
        super(message);
    }

    /**
     * Constructor with message
     * @param message custom message
     * @param cause cause
     */
    public TopicException(String message, Throwable cause) {
        super(message, cause);
    }
}
