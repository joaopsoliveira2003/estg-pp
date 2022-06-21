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

/** Enumeration of participant type */
public enum ParticipantTypeEnum {
    /**
     * Enumeration Values
     */
    SPEAKER, VISITOR;

    /**
     * Returns a more visual representation of a given participant type
     * @param p - ParticipantTypeEnum
     * @return String
     */
    public static String toString(ParticipantTypeEnum p) {
        return switch (p) {
            case SPEAKER -> "Speaker";
            case VISITOR -> "Visitor";
        };
    }
}
