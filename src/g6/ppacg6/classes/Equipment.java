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

import g6.ppacg6.enumerations.EquipmentEnum;

/**
 * Class responsible for the equipments of the rooms
 */
public class Equipment {

    /**
     * The equipment's id
     */
    private int id;
    /**
     * The equipment's type
     */
    private EquipmentEnum equipment;


    /**
     * Constructor for the Equipment
     * @param id ID of the Equipment
     * @param equipment enumeration of the type of the Equipment
     * <br><b>hasProblems</b> is set to false by default
     */
    public Equipment(int id, EquipmentEnum equipment) {
        this.id = id;
        this.equipment = equipment;
    }
    
    /**
     * Gets the ID of the Equipment
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the Equipment
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the type of the Equipment
     * @return EquipmentEnum
     */
    public EquipmentEnum getEquipment() {
        return equipment;
    }


    /**
     * Sets the type of the Equipment
     * @param equipment EquipmentEnum
     */
    public void setEquipment(EquipmentEnum equipment) {
        this.equipment = equipment;
    }



    /**
     * Compares two equipments, by ID and type of the EquipmentEnum
     * @param obj the Equipment to compare
     * @return true if the equipments are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (getClass() != obj.getClass()) return false;

        final Equipment other = (Equipment) obj;

        if (this.id == other.id) return true;

        return this.equipment == other.equipment;
    }

    /**
     * Lists all the properties of the Equipment
     * @return String
     */
    @Override
    public String toString() {
        return "Equipment{" + "id=" + id + ", equipment=" + equipment + '}';
    }
    
}
