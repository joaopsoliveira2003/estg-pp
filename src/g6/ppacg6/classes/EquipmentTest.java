package g6.ppacg6.classes;

import g6.ppacg6.enumerations.EquipmentEnum;
import g6.ppacg6.exceptions.EquipmentException;

import static org.junit.jupiter.api.Assertions.*;

class EquipmentTest {

    @org.junit.jupiter.api.Test
    void getId() {
        Equipment equipment = new Equipment(1, EquipmentEnum.PROJECTOR);
        assertEquals(1, equipment.getId());
    }

    @org.junit.jupiter.api.Test
    void setId() {
        Equipment equipment = new Equipment(1, EquipmentEnum.PROJECTOR);
        try {
            equipment.setId(-1);
            fail("Should have thrown an exception");
        } catch (EquipmentException e) {
            assertEquals("ID must be positive", e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void getEquipment() {
        Equipment equipment = new Equipment(1, EquipmentEnum.PROJECTOR);
        assertEquals(EquipmentEnum.PROJECTOR, equipment.getEquipment());
    }

    @org.junit.jupiter.api.Test
    void setEquipment() {
        Equipment equipment = new Equipment(1, EquipmentEnum.PROJECTOR);
        try {
            equipment.setEquipment(null);
            fail("Should have thrown an exception");
        } catch (EquipmentException e) {
            assertEquals("Equipment must be defined", e.getMessage());
        }
        try {
            equipment.setEquipment(EquipmentEnum.COMPUTER);
            assertEquals(EquipmentEnum.COMPUTER, equipment.getEquipment());
        } catch (EquipmentException e) {
            fail("Should not have thrown an exception");
        }
    }

    @org.junit.jupiter.api.Test
    void testEquals() {
        Equipment equipment1 = new Equipment(1, EquipmentEnum.PROJECTOR);
        Equipment equipment2 = new Equipment(1, EquipmentEnum.PROJECTOR);
        assertEquals(equipment1, equipment2);
        Equipment equipment3 = new Equipment(2, EquipmentEnum.PROJECTOR);
        assertNotEquals(equipment1, equipment3);
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        Equipment equipment = new Equipment(1, EquipmentEnum.PROJECTOR);
        assertEquals("Equipment{id=1, equipment=PROJECTOR}", equipment.toString());
    }
}