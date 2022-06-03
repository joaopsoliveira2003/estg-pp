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

/** Enumeration of equipments */
public enum EquipmentEnum {
    COMPUTER, PROJECTOR, MOBILE_PHONE, LASER_POINTER;
    
     /**
     * Returns a more visual representation of a given course
     * @param equip - EquipmentEnum
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
