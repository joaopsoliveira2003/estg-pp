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

public enum EquipmentEnum {
    COMPUTER, PROJECTOR, MOBILE_PHONE, LASER_POINTER;
    
     /**
     * Return a more visual representation of a given course
     * @param equip
     * @return String
     */
    public static String toString(EquipmentEnum equip) {
        return switch(equip) {
            case COMPUTER -> "Computer";
            case PROJECTOR -> "Projector";
            case MOBILE_PHONE -> "Mobile Phone";
            case LASER_POINTER -> "Laser Pointer";
        };
    }
}
