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

/** Exception related to the Theme Class */
public class ThemeException extends Exception {

    /**
     * Constructor
     */
    public ThemeException() {
    }

    /**
     * Constructor with message
     * @param message custom message
     */
    public ThemeException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message custom message
     * @param cause cause
     */
    public ThemeException(String message, Throwable cause) {
        super(message, cause);
    }
}
