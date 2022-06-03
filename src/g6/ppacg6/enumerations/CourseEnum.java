/*
 * Nome: Rui Alexandre Borba Vitorino
 * Número: 8190479
 * Turma: LSIRC12T1
 *
 * Nome: João Pedro Silva Oliveira
 * Número: 8210291
 * Turma: LSIRC11T2
 */

package g6.ppacg6.enumerations;

/** Enumeration of courses */
public enum CourseEnum {
    LSIRC, LEI, LSIG, MEI;
    
    /**
     * Returns a more visual representation of a given course
     * @param course - CourseEnum
     * @return String
     */
    public static String toString(CourseEnum course) {
        return switch(course) {
            case LSIRC -> "Licenciatura em Segurança Informática em Redes de Computadores";
            case LEI -> "Licenciatura em Engenharia Informática";
            case LSIG -> "Licenciatura em Sistemas de Informação para a Gestão";
            case MEI -> "Mestrado em Engenharia Informática";
        };
    }
}
