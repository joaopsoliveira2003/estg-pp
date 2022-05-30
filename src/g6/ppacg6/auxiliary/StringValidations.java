/*
* Nome: Rui Alexandre Borba Vitorino
* Número: 8190479
* Turma: LSIRC12T1
*
* Nome: João Pedro Silva Oliveira
* Número: 8210291
* Turma: LSIRC11T2
*/

package g6.ppacg6.auxiliary;

public class StringValidations {
    
    /**
     * Check if a given String is valid, with some default conditions
     * @param string - String to be checked
     * @return boolean
     * @throws StringIndexOutOfBoundsException - when the given String is bigger than 50 characters
     * OR the String is empty
     * @throws NullPointerException - when the given String is null
     */
    public static boolean isValidString(String string) throws
            StringIndexOutOfBoundsException, NullPointerException {
        if ( string == null ) throw new NullPointerException();
        if ( string.length() >= 50 ) {
            throw new StringIndexOutOfBoundsException();
        } else if ( string.isBlank() || string.isEmpty() ) {
            throw new StringIndexOutOfBoundsException();
        } else {
            return true;
        }
    }
    
    /**
     * Check if a given String is valid, based on the conditions told by the caller
     * @param string - the String to be checked
     * @param limitCharacters - the maximum number of characters that the String can have
     * @return boolean
     * @throws StringIndexOutOfBoundsException - when the String is bigger then the @limitCharacters
     * OR the String is empty
     * @throws NullPointerException - when the String is null
     */
    public static boolean isValidString(String string, int limitCharacters) throws
            StringIndexOutOfBoundsException, NullPointerException {
        if ( string == null ) throw new NullPointerException();
        if ( string.length() >= limitCharacters ) {
            throw new StringIndexOutOfBoundsException();
        } else if ( string.isBlank() || string.isEmpty() ) {
            throw new StringIndexOutOfBoundsException();
        } else {
            return true;
        }
    }
}

