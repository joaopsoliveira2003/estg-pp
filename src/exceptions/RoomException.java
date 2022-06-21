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

/** Exception related to the Room Class */
public class RoomException extends Exception {

    /**
     * Constructor
     */
    public RoomException() {
    }

    /**
     * Constructor with message
     * @param message custom message
     */
    public RoomException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message custom message
     * @param cause cause
     */
    public RoomException(String message, Throwable cause) {
        super(message, cause);
    }

}
