package g6.ppacg6.enumerations;

/** Enumeration of participant type */
public enum ParticipantTypeEnum {
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
