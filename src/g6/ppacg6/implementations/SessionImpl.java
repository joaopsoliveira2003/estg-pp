/*
 * Nome: Rui Alexandre Borba Vitorino
 * Número: 8190479
 * Turma: LSIRC12T1
 *
 * Nome: João Pedro Silva Oliveira
 * Número: 8210291
 * Turma: LSIRC11T2
 */

package g6.ppacg6.implementations;

import estg.ipp.pt.tp02_conferencesystem.enumerations.PresentationState;
import estg.ipp.pt.tp02_conferencesystem.exceptions.SessionException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Presentation;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Room;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Session;
import g6.ppacg6.auxiliary.DateValidations;
import g6.ppacg6.classes.Equipment;
import g6.ppacg6.classes.Theme;

import java.time.Duration;
import java.time.LocalDateTime;

/** Class responsible for the sessions */
public class SessionImpl implements Session {
    /** The id */
    private int id = 0;

    /** The class's id */
    private static int CID = 0;

    /** The name */
    private String name;

    /** The duration */
    private int duration;

    /** The start time */
    private LocalDateTime startTime;

    /** The end time */
    private LocalDateTime endTime;

    /** The number of presentations */
    private int nPresentations = 0;

    /** The array of presentations */
    private Presentation[] presentations;

    /** The initial capacity of the array of Presentations */
    private static final int INITIAL_PRESENTATIONS = 10;

    /** The number of participants */
    private int nParticipants = 0;

    /** The array of participants */
    private Participant[] participants;

    /** The initial capacity of the array of Participants */
    private static final int INITIAL_PARTICIPANTS = 10;

    /** The session theme */
    private Theme sessionTheme;

    /** The session room */
    private Room room;

    /**
     * Constructor
     * @param name the name
     * @param sessionTheme the session theme
     * @param startTime the start time
     * @param endTime the end time
     * @param room the room
     */
    public SessionImpl(String name, Theme sessionTheme, LocalDateTime startTime, LocalDateTime endTime, Room room) {
        this.id = ++CID;
        this.name = name;
        this.sessionTheme = sessionTheme;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = 0;
        this.room = room;
        this.nPresentations = 0;
        this.presentations = new Presentation[INITIAL_PRESENTATIONS];
        this.nParticipants = 0;
        this.participants = new Participant[INITIAL_PARTICIPANTS];
    }

    /**
     * Gets the id
     * @return id
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Gets the name
     * @return name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Gets the duration in minutes of the session
     * @return int
     */
    @Override
    public int getDuration() {
        Duration startTimeD = Duration.between(this.startTime, LocalDateTime.now());
        Duration endTimeD = Duration.between(this.endTime, LocalDateTime.now());
        return (int) (startTimeD.toMinutes() - endTimeD.toMinutes());
    }

    /**
     * Gets the maximum available duration left for a presentation
     * @return int - timeLeft in minutes
     */
    @Override
    public int getMaxDurationPerPresentation() {
        int timeLeft = (int) Duration.between(this.startTime, this.endTime).toMinutes();

        for (int i = 0; i < this.nPresentations; i++) {
            timeLeft -= this.presentations[i].getDuration();
        }
        return (int) timeLeft;
    }

    /**
     * Gets the start time
     * @return startTime
     */
    @Override
    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    /**
     * Gets the end time
     * @return endTime
     */
    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    /**
     * Gets the theme of the session
     * @return String
     */
    @Override
    public String getSessionTheme() {
        return this.sessionTheme.getTheme();
    }

    /**
     * Gets the room of the session
     * @return Room
     */
    @Override
    public Room getRoom() {
        return this.room;
    }

    /**
     * Gets the number of participants
     * @return nParticipants
     */
    public int getnParticipants() {
        return this.nParticipants;
    }

    /**
     * Finds a specific presentation
     * @return position
     */
    private int findPresentation(Presentation prsntn) {
        int pos = -1, x = 0;
        if (nPresentations == 0) return pos;
        
        while ( pos == -1 && x < nPresentations ) {
            if ( presentations[x].equals(prsntn) ) {
                pos = x;
            }
            x++;
        }
        return pos;
    }

    /**
     * Adds a new presentation
     * @param prsntn the presentation
     * @return position
     * @throws SessionException if the presentation is null, if there is no time left, if there is any memory error, if the presentation is already in the session, if the room doesn't have the equipment needed
     */
    @Override
    public boolean addPresentation(Presentation prsntn) throws SessionException {
        if ( prsntn == null ) throw new SessionException("Can't add a Presentation that is null");

        if ( ((PresentationImpl)prsntn).getEndTime().isAfter(this.endTime) ) {
            throw new SessionException("Can't add a Presentation that ends after the session");
        }

        if ( ((PresentationImpl)prsntn).getEndTime().isAfter(this.endTime) ) throw new
                SessionException("The presentation is after the session's end time");

        if ( ((PresentationImpl)prsntn).getStartTime().isBefore(this.startTime) ) throw new
                SessionException("The presentation is before the session's start time");

        if ( prsntn.getDuration() > this.getMaxDurationPerPresentation() ) {
            throw new SessionException("There is no time left in the session to add the Presentation.");
        }
        
        if ( nPresentations == presentations.length ) throw new
        SessionException("Can't add more Presentations to this Session");
        
        int pos = findPresentation(prsntn);
        
        if ( pos != -1 ) throw new SessionException("The Presentation is already set in the Session");

        // Check if the Room has the required Equipments to run the Presentation
        Equipment[] requiredEquipments = ((PresentationImpl)prsntn).getRequiredEquipments();
        Equipment[] roomEquipments = ((RoomImpl)room).getEquipments();
        int nEquip = Math.max(requiredEquipments.length, roomEquipments.length);
        
        for ( int x = 0; x < nEquip; x++ ) {
            if ( requiredEquipments[x] == null ) break;
            if (! (requiredEquipments[x].equals( roomEquipments[x] )) ) {
                throw new SessionException("The Room of the Session does not have the required Equipments to accommodate the Presentation");
            }
        }

        /* Try to add the Presenter to the array of Participants
        if the Presenter is already in the array, it will not be added again,
        otherwise (finally) we added the presentation to the array of presentations */
        try {
            this.addParticipant(prsntn.getPresenter());
        } catch (SessionException e) {
            throw new SessionException(e.getMessage());
        } finally {
            presentations[nPresentations++] = prsntn;
        }

        return true;
    }

    /**
     * Remove a presentation given the ID
     * @param i - the ID of the presentation to remove
     * @throws SessionException - if the presentation does not exist, or there are no sessions to remove
     */
    @Override
    public void removePresentation(int i) throws SessionException {
        if ( nPresentations == 0 ) throw new SessionException("There are no sessions to remove");

        boolean found = false;
        for ( int p = 0; p < nPresentations; p++ ) {
            if ( ((PresentationImpl)this.presentations[p]).getId() == i ) {
                presentations[i] = null;
                found = true;
                break;
            }
        }

        if (! (found) ) throw new SessionException("The Presentation does not exist in the Session");

        for ( int x = i; x < nPresentations - 1; x++ ) {
            if ( presentations[x] == null ) {
                presentations[x] = presentations[x + 1];
            }
            x++;
        }
        presentations[--nPresentations] = null;
    }

    /**
     * Get a given presentation given the ID
     * @param i - the ID of the presentation to get
     * @return Presentation
     * @throws SessionException - if the presentation does not exist, or there are no sessions to get
     */
    @Override
    public Presentation getPresentation(int i) throws SessionException {
        if (nPresentations == 0) throw new SessionException("There are no presentatins in the session.");

        boolean found = false;
        for ( int x = 0; x < nPresentations; x++ ) {
            if (this.presentations[x].getId() == i) {
                found = true;
                break;
            }
        }

        if (! (found) ) throw new SessionException("The Presentation does not exist in the Session");
        return presentations[i];
    }

    /**
     * Gets the presentations array, without null values
     * @return the presentations array
     */
    @Override
    public Presentation[] getPresentations() {
        Presentation[] tmpPresentations = new Presentation[nPresentations];

        for ( int p = 0; p < nPresentations; p++ ) {
            tmpPresentations[p] = presentations[p];
        }

        return tmpPresentations;
    }

    /**
     * List all the presentations in the session
     * @return String
     */
    public String listPresentations() {
        String str = "";
        if (nPresentations == 0) {
            str += "No Presentations";
            return str;
        } else {
            for (Presentation presentation : presentations) {
                if (presentation == null) break;
                str += presentation.toString() + "\n";
            }
            return str;
        }
    }

    /**
     * Check if the session has started, this is, if there is any presentation in the session
     * that has not started yet (false, in this case; true, otherwise)
     * @return boolean
     */
    @Override
    public boolean isStarted() {

        for ( int p = 0; p < nPresentations; p++ ) {
            if (! ( ((PresentationImpl)this.presentations[p]).getPresentationState().equals(
                    PresentationState.NOT_PRESENTED) ) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get all the Participants that are Presenters of a Session
     * @return Participant[]
     */
    @Override
    public Participant[] getAllPresenters() {
        Participant[] tempParticipant = new Participant[nPresentations];

        for ( int x = 0; x < nPresentations; x++ ) {
            tempParticipant[x] = presentations[x].getPresenter();
        }

        return tempParticipant;
    }

    /**
     * Get the number of presentations in the session
     * @return int
     */
    @Override
    public int getNumberOfPresentations() {
        return this.nPresentations;
    }


    /**
     * Find a Participant in the Session
     * @param participant - the participant to find
     * @return int - the position of the participant in the array of participants
     */
    private int findParticipant(Participant participant) {
        int pos = -1, x = 0;
        if (nParticipants == 0) return pos;

        while ( pos == -1 && x < nParticipants ) {
            if ( this.participants[x].equals(participant) ) {
                pos = x;
            }
            x++;
        }
        return pos;
    }

    private void increaseParticipantArr() throws OutOfMemoryError {
        Participant[] tmpParticipants = new Participant[participants.length * 2];

        try {
            for ( int x = 0; x < participants.length; x++ ) {
                tmpParticipants[x] = participants[x];
            }
        } catch (OutOfMemoryError e) {
            throw new OutOfMemoryError("There is no more memory to allocate the new array of participants");
        }

        participants = tmpParticipants;
    }

    /**
     * Add a Participant to the Session
     * @param participant - the participant to add
     * @return boolean - true if the participant was added, false otherwise
     * @throws SessionException - if the participant is already in the session, or is null
     */
    public boolean addParticipant(Participant participant) throws SessionException {
        if (participant == null) throw new SessionException("Can't add a Participant that is null");

        if (nParticipants == participants.length) increaseParticipantArr();

        int pos = findParticipant(participant);

        if ( pos != -1 ) throw new SessionException("The Participant is already set in the Session");

        participants[nParticipants++] = participant;
        return true;
    }

    /**
     * Delete a Participant from the Session
     * @param participant - the participant to delete
     * @return boolean - true if the participant was deleted, false otherwise
     * @throws SessionException - when the participant is not in the session, or is null
     */
    public boolean delParticipant(Participant participant) throws SessionException {
        if (participant == null) throw new SessionException("Can't remove a Participant that is null");

        int pos = findParticipant(participant);

        if ( pos == -1 ) throw new SessionException("The Participant is not set in the Session");

        for ( int x = pos; x < nParticipants - 1; x++ ) {
            this.participants[x] = this.participants[x + 1];
        }

        participants[--nParticipants] = null;
        return true;
    }

    /**
     * Get the participants array, without null values
     * @return Participant[]
     * @throws SessionException - if there are no participants in the session
     */
    public Participant[] getParticipants() throws SessionException {
        if ( nParticipants == 0 ) throw new SessionException("There are no participants in the session");

        Participant[] tmpParticipants = new Participant[nParticipants];

        for ( int x = 0; x < nParticipants; x++ ) {
            tmpParticipants[x] = participants[x];
        }

        return tmpParticipants;
    }


    /**
     * Compare two sessions, by ID, startTime and endTime
     * We first check if the session is null or the same session
     * Then we check if they have the same ID, if so, true, otherwise
     * we check if the startTime and endTime are the same, if so, true, otherwise false
     * @param obj - the object to compare to
     * @return boolean, true if the sessions are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (getClass() != obj.getClass()) return false;

        final SessionImpl other = (SessionImpl) obj;

        if ( this.id == other.id ) return true;

        return ( this.startTime.isEqual(other.startTime) && this.endTime.isEqual(other.endTime) );
    }

    /**
     * List all the properties of the sessions
     * @return String
     */
    @Override
    public String toString() {
        return "SessionImpl{" + "id=" + id + ", name=" + name + ", duration=" + 
        duration + ", startTime=" + startTime + ", endTime=" + endTime +
        ", nPresentations=" + nPresentations + ", presentations=[" + 
        listPresentations() + "], sessionTheme=" + sessionTheme + ", room=" + room + '}';
    }

}
