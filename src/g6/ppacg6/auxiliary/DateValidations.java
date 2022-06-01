package g6.ppacg6.auxiliary;

import estg.ipp.pt.tp02_conferencesystem.exceptions.ConferenceException;

import java.time.LocalDateTime;

/** Class responsible for date validations */
public class DateValidations {

    /**
     * Checks if a given interval of time is valid, with some default conditions
     * @param startTime the start time
     * @param endTime the end time
     * @throws ConferenceException if the interval is invalid (start time after end time, start time != end time, same year)
     * @return true if the date is valid
     */
    public static boolean isValidDate(LocalDateTime startTime, LocalDateTime endTime) throws ConferenceException {

        if (startTime == null || endTime == null) {
            throw new ConferenceException("Invalid Date's");
        }

        if (startTime.isAfter(endTime)) {
            throw new ConferenceException("Start time must be before end time");
        } else if (startTime.isEqual(endTime)) {
            throw new ConferenceException("Start time and end time must be different");
        }

        if (startTime.getYear() != endTime.getYear()) {
            throw new ConferenceException("Start time and end time must be in the same year");
        }

        return true;
    }

}