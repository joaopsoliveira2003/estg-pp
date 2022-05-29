/*
* Nome: Rui Alexandre Borba Vitorino
* Numero: 8190479
* Turma: LSIRC12T1
*
* Nome: Joao Pedro Silva Oliveira
* Numero: 8210291
* Turma: LSIRC12T2
*/

package g6.ppacg6.enumerations;

public enum DegreeEnum {
    CTESP, LICENCIATURA, MESTRADO, DOUTORAMENTO, POS_DOUTORAMENTO;
    
     /**
     * Return a more visual representation of a given degree
     * @param degree
     * @return String
     */
    public static String toString(DegreeEnum degree) {
        return switch(degree) {
            case CTESP -> "Curso Tecnico Superior Profissional";
            case LICENCIATURA -> "Licenciatura";
            case MESTRADO -> "Mestrado";
            case DOUTORAMENTO -> "Doutoramento";
            case POS_DOUTORAMENTO -> "Pos-Doutoramento";
        };
    }
}
