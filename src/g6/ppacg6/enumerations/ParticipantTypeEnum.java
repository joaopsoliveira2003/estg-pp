package g6.ppacg6.enumerations;

public enum ParticipantTypeEnum {
    SPEAKER, VISITOR;

    public static String toString(ParticipantTypeEnum p) {
        return switch (p) {
            case SPEAKER -> "Speaker";
            case VISITOR -> "Visitor";
        };
    }
}
