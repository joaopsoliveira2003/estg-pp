/*
* Nome: Rui Alexandre Borba Vitorino
* Numero: 8190479
* Turma: LSIRC12T1
*
* Nome: Joao Pedro Silva Oliveira
* Numero: 8210291
* Turma: LSIRC12T2
*/

package exceptions;

/** Exception related to the Presentation Class */
public class PresentationException extends Exception {

    /** 
     * Constructor 
     */
    public PresentationException() {
    }

    /** 
     * Constructor with custom message 
     * @param message custom message
     */
    public PresentationException(String message) {
        super(message);
    }

    /** 
     * Constructor with custom message and cause
     * @param message custom message
     * @param cause cause
     */
    public PresentationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    

}
