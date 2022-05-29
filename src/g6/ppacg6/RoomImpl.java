/*
* Nome: Rui Alexandre Borba Vitorino
* Numero: 8190479
* Turma: LSIRC12T1
*
* Nome: Joao Pedro Silva Oliveira
* Numero: 8210291
* Turma: LSIRC12T2
*/

package g6.ppacg6;

import g6.ppacg6.auxiliary.StringValidations;
import estg.ipp.pt.tp02_conferencesystem.exceptions.RoomException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Room;

public class RoomImpl implements Room {

    private int id = 0;
    private static int CID = 0;

    private String name;
    private int numberOfSeats = 0;
    
    private int nEquipments = 0;
    private Equipment[] equipments;
    private static final int MAX_EQUIPMENTS = 10;
    
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
        this.equipments = new Equipment[MAX_EQUIPMENTS];
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

    public void setName(String name) throws 
        ArrayIndexOutOfBoundsException, NullPointerException {
        try {
            if (StringValidations.isValidString(name, 50)) this.name = name;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("The name of the room cant exceed the limit of allowed characters.");
        } catch (NullPointerException e) {
            throw new NullPointerException("The name of the room cant be set as null");
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
     * Set the number of the seats of the Room
     * @param numberOfSeats - the number of seats of the room
     * @throws IllegalArgumentException - when the parsed variable is not int
     * @throws RoomException - when the number of seats are below or equal to 0 OR above or equal to 250
     */
    public void setNumberOfSeats(int numberOfSeats) throws
            IllegalArgumentException, RoomException, NumberFormatException, RuntimeException {
        if (numberOfSeats <= 0) throw new RoomException("The number of seats of the room cant be set as 0 or below.");
        if (numberOfSeats >= 250) throw new RoomException("The number of seats of the room cant be set as 250.");
        try {
            if (!(Integer.class.isInstance(numberOfSeats))) this.numberOfSeats = numberOfSeats;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("You must specify an integer.");
        }
    }
    
    
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
    
    
    public boolean addEquipment(Equipment equipment) throws NullPointerException {
        if (equipment == null) throw new NullPointerException();
        
        if (nEquipments == equipments.length) return false;
        
        int pos = findEquipment(equipment);
        
        if (pos != -1) return false;
        
        equipments[nEquipments++] = equipment;
        return true;
    }   
    
    public int addEquipment(Equipment[] equipmentsToAdd) {
        int x = 0;
        
        for ( Equipment equipment : equipmentsToAdd ) {
            if ( addEquipment(equipment) ) {
                x++;
            }
        }
        return x;
    }
    
    public boolean delEquipment(Equipment equipment) {
        if (nEquipments == 0) return false;
        
        if (equipment == null) return false;
        
        int pos = findEquipment(equipment);
        
        if (pos == -1) return false;
        
        for ( int x = pos; x < nEquipments - 1; x++ ) {
            equipments[x] = equipments[x + 1];
        }
        
        equipments[--nEquipments] = null;
        return true;
    }
    
    public int delEquipment(Equipment[] equipmentsToDel) {
        int x = 0;
        
        for ( Equipment equipment : equipmentsToDel ) {
            if ( delEquipment(equipment) ) {
                x++;
            }
        }
        return x;
    }
    
    public void setEquipmentStatus(int i, boolean status) {
        if ( nEquipments == 0 ) return;
        
        try {
            if ( this.equipments[i] == null ) throw new NullPointerException();
        } catch (NullPointerException ex) {
            throw new NullPointerException("Couldn't find the specified Equipment");
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new ArrayIndexOutOfBoundsException("Couldn't find the specified Equipment");
        }
        
        this.equipments[i].setHasProblems(!status);
    }
    
    public Equipment[] getEquipments() {
        return this.equipments;
    }
    
    public String listEquipments() {
        String str = "";
        for ( Equipment equipment : this.equipments ) {
            if (equipment == null) break;
            str += equipment.toString() + " ";
        }
        return str;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        
        if (obj == null) return false;
        
        if (getClass() != obj.getClass()) return false;
        
        final RoomImpl other = (RoomImpl) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "RoomImpl{" + "id=" + id + ", name=" + name + ", numberOfSeats=" + numberOfSeats + ", nEquipments=" + nEquipments + ", equipments=[" + listEquipments() + "]}";
    }
        
}
