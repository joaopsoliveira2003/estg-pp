package g6.ppacg6.implementations;

import estg.ipp.pt.tp02_conferencesystem.enumerations.ConferenceState;
import estg.ipp.pt.tp02_conferencesystem.exceptions.ConferenceException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Room;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Session;
import g6.ppacg6.classes.Professor;
import g6.ppacg6.classes.Student;
import g6.ppacg6.interfaces.ConferenceManagement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

/** Class responsible for managing conferences */
public class ConferenceManagementImpl implements ConferenceManagement {
    // TODO - wtf do we do with this?
    /** Number of conferences */
    private int nConferences;

    /** Array of conferences */
    private ConferenceImpl[] conferences;

    /** Maximum number of conferences */
    private static final int MAX_CONFERENCES = 10;

    /**
     * Constructor of the class
     * @apiNote <b>nConferences</b> is automatically set to 0 and <b>conferences</b> is automatically allocated to the maximum number of conferences
     */
    public ConferenceManagementImpl() {
        this.nConferences = 0;
        this.conferences = new ConferenceImpl[MAX_CONFERENCES];
    }

    /**
     * Method responsible for expanding the array of conferences
     * @throws ConferenceException if any memory allocation error occurs
     */
    private void increaseConferenceArr() throws ConferenceException {
        ConferenceImpl[] tmpConferences = new ConferenceImpl[nConferences * 2];

        try {
            for ( int x = 0; x < nConferences; x++ ) {
                tmpConferences[x] = this.conferences[x];
            }
        } catch (OutOfMemoryError e) {
            throw new OutOfMemoryError();
        }

        this.conferences = tmpConferences;
    }

    private int findConference(ConferenceImpl conference) {
        int pos = -1, x = 0;
        while ( pos == -1 && x < nConferences ) {
            if ( conferences[x].equals(conference) ) {
                pos = x;
            }
            x++;
        }
        return pos;
    }

    @Override
    public boolean addConference(ConferenceImpl conference) throws ConferenceException {
        if ( conference == null ) throw new ConferenceException("The Conference is null");

        try {
            if ( nConferences == this.conferences.length ) increaseConferenceArr();
        } catch ( ConferenceException e ) {
            throw new ConferenceException(e.getMessage());
        }

        int pos = findConference(conference);

        if ( pos != -1 ) throw new ConferenceException("The Conference is already in the list");

        this.conferences[nConferences++] = conference;
        return true;
    }

    public int addConference(ConferenceImpl[] conferencesToAdd) throws ConferenceException {
        int added = 0;

        try {
            for ( ConferenceImpl conference : conferencesToAdd ) {
                if ( addConference(conference) ) added++;
            }
        } catch ( ConferenceException e ) {
            throw new ConferenceException(e.getMessage());
        }
        return added;
    }

    @Override
    public boolean delConference(ConferenceImpl conference) throws ConferenceException {
        if ( conference == null ) throw new ConferenceException("The Conference is null");

        int pos = findConference(conference);

        if ( pos == -1 ) throw new ConferenceException("The Conference is not in the list");

        for ( int x = pos; x < nConferences - 1; x++ ) {
            this.conferences[x] = this.conferences[x + 1];
        }

        this.conferences[--nConferences] = null;
        return true;
    }

    public int delConference(ConferenceImpl[] conferencesToDel) throws ConferenceException {
        int deleted = 0;

        try {
            for ( ConferenceImpl conference : conferencesToDel ) {
                if ( delConference(conference) ) deleted++;
            }
        } catch ( ConferenceException e ) {
            throw new ConferenceException(e.getMessage());
        }
        return deleted;
    }
}
