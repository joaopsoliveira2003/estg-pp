package g6.ppacg6.main;

import g6.ppacg6.auxiliary.DateValidations;

import java.time.LocalDateTime;

public class DateValidationsDemo {

    static LocalDateTime date1 = LocalDateTime.of(2020, -1, 1, 0, 0);
    static LocalDateTime date2 = LocalDateTime.of(2020, 2, 1, 0, 0);

    public static void main(String[] args) {
        try {
            DateValidations.isValidDate(date1, date2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}