package g6.ppacg6.main;

import g6.ppacg6.auxiliary.DateValidations;
import g6.ppacg6.classes.Equipment;
import g6.ppacg6.enumerations.EquipmentEnum;

import java.time.LocalDateTime;

public class DateValidationsDemo {

    static LocalDateTime date1 = LocalDateTime.of(2020, 2, 1, 0, 0);
    static LocalDateTime date2 = LocalDateTime.of(2020, 2, 1, 0, 0);

    public static void main(String[] args) {
        try {
            DateValidations.isValidDate(date1, date2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Equipment e1 = new Equipment(1, EquipmentEnum.COMPUTER);
        Equipment e2 = new Equipment(1, EquipmentEnum.LASER_POINTER);
        Equipment e3 = new Equipment(3, EquipmentEnum.MOBILE_PHONE);
        System.out.println(e1.equals(e2));
        System.out.println(e1.equals(e2));
        System.out.println(e1.equals(e3));

    }
}