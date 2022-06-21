/*
 * Nome: Rui Alexandre Borba Vitorino
 * Número: 8190479
 * Turma: LSIRC12T1
 *
 * Nome: João Pedro Silva Oliveira
 * Número: 8210291
 * Turma: LSIRC11T2
 */

package enumerations;

/** Enumeration of fields */
public enum FieldEnum {
     /**
     * Enumeration Values
     */
    MATHEMATICS, PHYSICS, CHEMISTRY, COMPUTER_SCIENCE, PROGRAMMING, NETWORKING, SECURITY;
    
     /**
     * Returns a more visual representation of a given field
     * @param field - FieldEnum
     * @return String
     */
    public static String toString(FieldEnum field) {
        return switch (field) {
            case MATHEMATICS -> "Mathematics";
            case PHYSICS -> "Physics";
            case CHEMISTRY -> "Chemistry";
            case COMPUTER_SCIENCE -> "Computere Science";
            case PROGRAMMING -> "Programming";
            case NETWORKING -> "Networking";
            case SECURITY -> "Security";
        };
    }
    
}
