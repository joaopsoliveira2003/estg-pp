/*
 * Nome: Rui Alexandre Borba Vitorino
 * Número: 8190479
 * Turma: LSIRC12T1
 *
 * Nome: João Pedro Silva Oliveira
 * Número: 8210291
 * Turma: LSIRC11T2
 */

package implementations;

import classes.*;
import auxiliary.*;
import exceptions.*;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Room;
import estg.ipp.pt.tp02_conferencesystem.exceptions.RoomException;

/** Class that represents a room in the conference system. */
public class RoomImpl implements Room {

    /** The room's ID */
    private int id = 0;

    /** The class's ID Counter */
    private static int CID = 0;

    /** The room's name */
    private String name;

    /** The room's capacity */
    private int numberOfSeats = 0;

    /** The number of equipments in the rooms */
    private int nEquipments = 0;

    /** The room's equipments array */
    private Equipment[] equipments;

    /** The initial size of the room's equipment's array  */
    private static final int INITIAL_EQUIPMENTS = 10;
    
    /**
     * Constructor for the Room
     * @param name - name of the Room
     * @param numberOfSeats - numberOfSeats available in the Room
     * id - Unique (ID)entifier for each Room
     */
    public RoomImpl(String name, int numberOfSeats) {
        this.id = ++CID;
        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.nEquipments = 0;
        this.equipments = new Equipment[INITIAL_EQUIPMENTS];
    }
    
    /**
     * Get the ID of the Room
     * @return int
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Get the name of the Room
     * @return String
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of the Room, is the String is valid
     * @param name - String to be set as the name of the Room
     * @throws RoomException - if the String is not valid
     */
    public void setName(String name) throws RoomException {
        try {
            if (StringValidations.isValidString(name, 50)) this.name = name;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RoomException("The name of the room cant exceed the limit of allowed characters.");
        } catch (NullPointerException e) {
            throw new RoomException("The name of the room cant be set as null");
        }
    }
    
    /**
     * Get the number of seats of the Room
     * @return int
     */
    @Override
    public int getNumberOfSeats() {
        return this.numberOfSeats;
    }

    /**
     * Get the number of equipments in the Room
     * @return int
     */
    public int getnEquipments() {
        return this.nEquipments;
    }

    /**
     * Get the equipments array of the Room
     * @return Equipment[]
     */
    public Equipment[] getEquipments() {
        return this.equipments;
    }

    /**
     * Set the number of seats of the Room, is the number is valid
     * @param numberOfSeats - int to be set as the number of seats of the Room
     * @throws RoomException - if the number is 0 or below, or above 500
     */
    public void setNumberOfSeats(int numberOfSeats) throws RoomException {
        if (numberOfSeats <= 0) throw new RoomException("The number of seats of the room cant be set as 0 or below.");
        if (numberOfSeats > 500) throw new RoomException("The number of seats of the room cant be set as 500.");
        this.numberOfSeats = numberOfSeats;
    }


    /**
     * Find an equipment in the array of equipments
     * @param equipment - Equipment to be found
     * @return int - the index of the equipment in the array, or -1 if it is not found
     */
    private int findEquipment(Equipment equipment) {
        int pos = -1, x = 0;
        
        while ( pos == -1 && x < nEquipments ) {
            if ( equipments[x].equals(equipment) ) {
                pos = x;
            }
            x++;
        }
        return pos;
    }

    
        /**
     * Increase the capacity of the array participants[], twice the current size
     * This is possible creating a temporary array with the new size, and then
     * copying the old array to the new one, replacing it
     * @throws OutOfMemoryError when the programs runs out of memory 
     */
    private void increaseEquipmentArr() throws OutOfMemoryError {
        Equipment[] tmpEquipments = new Equipment[this.equipments.length * 2];

        int size = this.equipments.length;
        try {
            for ( int x = 0; x < size; x++ ) {
                tmpEquipments[x] = this.equipments[x];
            }
        } catch (OutOfMemoryError e) {
            throw new OutOfMemoryError("There is no more memory to allocate the new array of participants");
        }

        this.equipments = tmpEquipments;
    }

    /**
     * Add an equipment to the array of equipments
     * @param equipment - Equipment to be added
     * @return boolean - true if the equipment was added, false if it was not
     * @throws EquipmentException - if the equipment is already in the array, or null
     */
    public boolean addEquipment(Equipment equipment) throws EquipmentException {
        if (equipment == null) throw new NullPointerException("Couldn't add the specified equipment.");
        
        try {
            if ( nEquipments == this.equipments.length ) increaseEquipmentArr();
        } catch (OutOfMemoryError e) {
            throw new EquipmentException("There is no more memory available to accommodate more equipments.");
        }
        
        int pos = findEquipment(equipment);
        
        if ( pos != -1 ) throw new EquipmentException("The equipment is already in the array.");
        
        equipments[nEquipments++] = equipment;
        return true;
    }

    /**
     * Add's an array of equipments to the array of equipments
     * @param equipmentsToAdd - array of equipments to be added
     * @return int - the number of equipments added successfully
     * @throws EquipmentException - if the array is null or empty
     */
    public int addEquipment(Equipment[] equipmentsToAdd) throws EquipmentException {
        if ( equipmentsToAdd.length == 0 ) throw new EquipmentException("The array of equipments to be added is empty.");

        int x = 0;
        
        for ( Equipment equipment : equipmentsToAdd ) {
            try {
                if (addEquipment(equipment)) {
                    x++;
                }
            } catch (EquipmentException ex) {
                throw new EquipmentException(ex.getMessage());
            }
        }
        return x;
    }

    /**
     * Remove an equipment from the array of equipments
     * @param equipment - Equipment to be removed
     * @return boolean - true if the equipment was removed, false if it was not
     * @throws EquipmentException - if the equipment is not in the array, or null
     */
    public boolean delEquipment(Equipment equipment) throws EquipmentException {
        if ( nEquipments == 0 ) throw new EquipmentException("The room has no equipments.");
        
        if ( equipment == null ) throw new EquipmentException("Couldn't remove the specified equipment.");
        
        int pos = findEquipment(equipment);
        
        if (pos == -1) throw new EquipmentException("The equipment is not in the array.");
        
        for ( int x = pos; x < nEquipments - 1; x++ ) {
            equipments[x] = equipments[x + 1];
        }
        
        equipments[--nEquipments] = null;
        return true;
    }

    /**
     * Remove an array of equipments from the array of equipments
     * @param equipmentsToDel - array of equipments to be removed
     * @return int - the number of equipments removed successfully
     * @throws EquipmentException - if the array is null or empty
     */
    public int delEquipment(Equipment[] equipmentsToDel) throws EquipmentException {
        if ( equipmentsToDel.length == 0 ) throw new EquipmentException("The array of equipments to be deleted is empty.");

        int x = 0;
        
        for ( Equipment equipment : equipmentsToDel ) {
            try {
                if (delEquipment(equipment)) {
                    x++;
                }
            } catch (EquipmentException ex) {
                throw new EquipmentException(ex.getMessage());
            }
        }
        return x;
    }

    /**
     * List all the equipments in the array of equipments
     * @return String - the list of equipments
     */
    public String listEquipments() {
        String str = "";
        for ( Equipment equipment : this.equipments ) {
            if (equipment == null) break;
            str += equipment.toString() + " ";
        }
        return str;
    }

    /**
     * Compares two room's, by ID and name
     * @param obj the room to compare with
     * False when:
     * Object is null, or not an object of the Room Class
     * True when:
     * It's the same object, has the same ID, or has the same name
     * @return boolean 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        
        if (obj == null) return false;
        
        if (getClass() != obj.getClass()) return false;
        
        final RoomImpl other = (RoomImpl) obj;
        if ( this.id == other.id ) return true;

        return ( this.name.equals(other.name) );
    }

    /**
     * List all the properties of the Room
     * @return String - the list of properties
     */
    @Override
    public String toString() {
        return "RoomImpl{" + "id=" + id + ", name=" + name + ", numberOfSeats=" + numberOfSeats + ", nEquipments=" + nEquipments + ", equipments=[" + listEquipments() + "]}";
    }
        
}
