/*
 * Nome: Rui Alexandre Borba Vitorino
 * Número: 8190479
 * Turma: LSIRC12T1
 *
 * Nome: João Pedro Silva Oliveira
 * Número: 8210291
 * Turma: LSIRC11T2
 */

package g6.ppacg6.classes;

import estg.ipp.pt.tp02_conferencesystem.exceptions.ConferenceException;
import g6.ppacg6.enumerations.EquipmentEnum;
import g6.ppacg6.exceptions.EquipmentException;

public class Equipment {

    private int id;
    private EquipmentEnum equipment;
    private boolean hasProblems = false;

    /**
     * Constructor for the Equipment.
     * @param id - ID of the Equipment
     * @param equipment - Enumeration of the type of Equipment
     * hasProblems - If the Equipment has problems (default false)
     */
    public Equipment(int id, EquipmentEnum equipment) {
        this.id = id;
        this.equipment = equipment;
        this.hasProblems = false;
    }

    
    /**
     * Get the ID of the Equipment
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID of the Equipment
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the type of the Equipment
     * @return EquipmentEnum
     */
    public EquipmentEnum getEquipment() {
        return equipment;
    }


    /**
     * Set the type of the Equipment
     * @param equipment - EquipmentEnum
     */
    public void setEquipment(EquipmentEnum equipment) {
        this.equipment = equipment;
    }

    /**
     * Check if the Equipment has problems
     * @return boolean
     */
    public boolean hasProblems() {
        return hasProblems;
    }

    /**
     * Change the hasProblems field of the Equipment
     * @param hasProblems - boolean
     */
    public void setHasProblems(boolean hasProblems) {
        this.hasProblems = hasProblems;
    }


    /**
     * Compare two Equipments, by ID and type of the EquipmentEnum
     * @param obj - the Equipment to compare
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        
        if (obj == null) return false;
        
        if (getClass() != obj.getClass()) return false;
        
        
        final Equipment other = (Equipment) obj;
        if (this.id == other.id) return true;
        //compare type of equipment by the EquipmentEnum
        return this.equipment == other.equipment;
    }

    
    /**
     * List all the properties of the Equipment
     * @return String
     */
    @Override
    public String toString() {
        return "Equipment{" + "id=" + id + ", equipment=" + equipment + ", hasProblems=" + hasProblems + '}';
    }
    
}
