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

public enum CourseEnum {
    LSIRC, LEI, LSIG, MEI;
    
    /**
     * Return a more visual representation of a given course
     * @param course
     * @return String
     */
    public static String toString(CourseEnum course) {
        return switch(course) {
            case LSIRC -> "Licenciatura em Seguranca Informatica, Redes e Computadores";
            case LEI -> "Licenciatura em Engenharia Informtica";
            case LSIG -> "Licentiaura em Sistemas de Informacao para a Gestao";
            case MEI -> "Mestrado em Engenharia Informatica";                
        };
    }
}
