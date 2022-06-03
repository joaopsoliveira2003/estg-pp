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

/** Class responsible for string validations */
public class StringValidations {
    
    /**
     * Checks if a given string is valid, with some default conditions
     * @param string the string to be checked
     * @throws NullPointerException when the string is null
     * @throws StringIndexOutOfBoundsException when the string is either bigger than 49 characters or empty or blank (only has spaces)
     * @return true if the string is valid, false otherwise
     */
    public static boolean isValidString(String string) throws StringIndexOutOfBoundsException, NullPointerException {
        if ( string == null ) throw new NullPointerException("String can't be null.");
        if (  string.isBlank() || string.isEmpty() ) throw new NullPointerException(
                "String can't be empty or blank.");
        if ( string.length() >= 50 ) throw new
                StringIndexOutOfBoundsException("String is not allowed to have more than 50 characters.");
        return true;
    }
    
    /**
     * Checks if a given string is valid, based on the conditions told by the caller
     * @param string the string to be checked
     * @param limitCharacters the maximum number of characters the string can have
     * @throws NullPointerException when the string is null
     * @throws StringIndexOutOfBoundsException when the string is either bigger than the limit of characters or empty
     * @return true if the string is valid, false otherwise
     */
    public static boolean isValidString(String string, int limitCharacters) throws StringIndexOutOfBoundsException, NullPointerException {
        if ( string == null ) throw new NullPointerException("String can't be null.");
        if ( string.isBlank() || string.isEmpty() ) throw new NullPointerException("String can't be empty or blank.");
        if ( string.length() >= limitCharacters ) throw new
                StringIndexOutOfBoundsException("String is not allowed to have more than " + limitCharacters + " characters.");
        return true;
    }
}

