/*
 * Nome: Rui Alexandre Borba Vitorino
 * Número: 8190479
 * Turma: LSIRC12T1
 *
 * Nome: João Pedro Silva Oliveira
 * Número: 8210291
 * Turma: LSIRC11T2
 */

package interfaces;


import java.io.IOException;


/** Class responsible for the viewing the generated statistics */
public interface IOStatistics {
    
    /** Method responsible by showing the Dashboard given certain JSON files */
    void showDashboard(String[] args) throws IOException;
    
}
