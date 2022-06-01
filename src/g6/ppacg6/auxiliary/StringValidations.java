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
     * Checks if a given string is valid, with some default conditions
     * @param string the string to be checked
     * @throws NullPointerException when the given string is null
     * @throws StringIndexOutOfBoundsException when the given string is either bigger than 49 characters or empty or blank (only has spaces)
     * @return true if the string is valid, false otherwise
     */
    public static boolean isValidString(String string) throws StringIndexOutOfBoundsException, NullPointerException {
        if ( string == null ) throw new NullPointerException();
        if ( string.length() > 49 || string.isBlank() || string.isEmpty() ) throw new StringIndexOutOfBoundsException();
        return true;
    }
    
    /**
     * Checks if a given string is valid, based on the conditions told by the caller
     * @param string the string to be checked
     * @param limitCharacters the maximum number of characters the string can have
     * @throws NullPointerException when the given string is null
     * @throws StringIndexOutOfBoundsException when the string is either bigger than the limit of characters or empty
     * @return true if the string is valid, false otherwise
     */
    public static boolean isValidString(String string, int limitCharacters) throws StringIndexOutOfBoundsException, NullPointerException {
        if ( string == null ) throw new NullPointerException();
        if ( string.length() >= limitCharacters || string.isBlank() || string.isEmpty() ) throw new StringIndexOutOfBoundsException();
        return true;
    }
}

