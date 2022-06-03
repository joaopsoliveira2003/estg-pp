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
import g6.ppacg6.classes.Equipment;
import g6.ppacg6.classes.Theme;

import java.time.Duration;
import java.time.LocalDateTime;

/** Class responsible for the sessions */
public class SessionImpl implements Session {
    // TODO - triple check the class

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


    private static final int MAX_PRESENTATIONS = 10;

    /** The number of participants */
    private int nParticipants = 0;

    /** The array of participants */
    private Participant[] participants;
    private static final int MAX_PARTICIPANTS = 10;

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
        this.presentations = new Presentation[MAX_PRESENTATIONS];
        this.nParticipants = 0;
        this.participants = new Participant[MAX_PARTICIPANTS];
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


    @Override
    public int getDuration() {
        Duration startTimeD = Duration.between(this.startTime, LocalDateTime.now());
        Duration endTimeD = Duration.between(this.endTime, LocalDateTime.now());
        return (int) (startTimeD.toMinutes() - endTimeD.toMinutes());
        //return (int) this.endTime.getMinute() - this.startTime.getMinute();
    }

    //TODO: check the method for negative values
    /**
     * Gets the maximum duration per presentation
     * @return timeLeft
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
    
    @Override
    public boolean addPresentation(Presentation prsntn) throws SessionException {
        if (prsntn == null) throw new SessionException("Can't add a Presentation that is null");

        if ( prsntn.getDuration() > this.getMaxDurationPerPresentation() ) {
            throw new SessionException("There is no time left in the session to add the" +
                    " Presentation.");
        }
        
        if (nPresentations == presentations.length) throw new 
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
                throw new SessionException("The Room of the Session does not have the required Equipments to accomodate the Presentation");
            }
        }

        try {
            this.addParticipant(prsntn.getPresenter());
        } catch (SessionException e) {
            throw new SessionException(e.getMessage());
        } finally {
            presentations[nPresentations++] = prsntn;
        }

        return true;
    }
    
    @Override
    public void removePresentation(int i) throws SessionException {
        if (nPresentations == 0) throw new SessionException("There are no sessions to remove");
        
        try {
            if ( presentations[i] == null ) throw new ArrayIndexOutOfBoundsException();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new SessionException("Couldnt find the presentation to remove.");
        }
        
        presentations[i] = null;

        for ( int x = i; x < nPresentations - 1; x++ ) {
            if ( presentations[x] == null ) {
                presentations[x] = presentations[x + 1];
            }
            x++;
        }
        presentations[--nPresentations] = null;
    }

    
    @Override
    public Presentation getPresentation(int i) throws SessionException {
        if (nPresentations == 0) throw new SessionException("There are no presentatins in the session.");

        for ( int x = 0; x < nPresentations; x++ ) {
            if (this.presentations[x].getId() == i) {
                return presentations[i];
            }
        }
        return presentations[i];
    }

    @Override
    public Presentation[] getPresentations() {
        Presentation[] tmpPresentations = new Presentation[nPresentations];

        for ( int p = 0; p < nPresentations; p++ ) {
            tmpPresentations[p] = presentations[p];
        }

        return tmpPresentations;
        //return this.presentations;
    }

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

    @Override
    public Participant[] getAllPresenters() {
        Participant[] tempParticipant = new Participant[nPresentations];
        for ( int x = 0; x < nPresentations; x++ ) {
            tempParticipant[x] = presentations[x].getPresenter();
        }
        return tempParticipant;
    }

    @Override
    public int getNumberOfPresentations() {
        return this.nPresentations;
    }

    //equals

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

    public boolean addParticipant(Participant participant) throws SessionException {
        if (participant == null) throw new SessionException("Can't add a Participant that is null");

        if (nParticipants == participants.length) throw new
        SessionException("Can't add more Participants to this Session");

        int pos = findParticipant(participant);

        if ( pos != -1 ) throw new SessionException("The Participant is already set in the Session");

        participants[nParticipants++] = participant;
        return true;
    }

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

    public Participant[] getParticipants() throws SessionException {
        try {
            return this.participants;
        } catch ( NullPointerException e ) {
            throw new SessionException("There are no participants in the session.");
        }
    }

    @Override
    public String toString() {
        return "SessionImpl{" + "id=" + id + ", name=" + name + ", duration=" + 
        duration + ", startTime=" + startTime + ", endTime=" + endTime +
        ", nPresentations=" + nPresentations + ", presentations=[" + 
        listPresentations() + "], sessionTheme=" + sessionTheme + ", room=" + room + '}';
    }

    
    
    
}
