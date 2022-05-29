/*
 * Nome: Rui Alexandre Borba Vitorino
 * Número: 8190479
 * Turma: LSIRC12T1
 *
 * Nome: João Pedro Silva Oliveira
 * Número: 8210291
 * Turma: LSIRC11T2
 */

package g6.ppacg6;

import estg.ipp.pt.tp02_conferencesystem.enumerations.ConferenceState;

import estg.ipp.pt.tp02_conferencesystem.exceptions.ConferenceException;

import estg.ipp.pt.tp02_conferencesystem.interfaces.Conference;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Room;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Session;

import estg.ipp.pt.tp02_conferencesystem.io.interfaces.Statistics;

import java.time.LocalDateTime;

public class ConferenceImpl implements Conference {
    
    private String name;
    private LocalDateTime year;
    
    private String field;
    private ConferenceState conferenceState;
    
    private Session[] sessions;
    private int nSessions = 0;
    private static final int MAX_SESSIONS = 10;
    
    private Participant[] participants;
    private int nParticipants = 0;
    private static final int MAX_PARTICIPANTS = 10;
    
    
    public ConferenceImpl(String name, LocalDateTime year, String field) {
        this.name = name;
        this.year = year;
        this.field = field;
        this.conferenceState = ConferenceState.ON_EDITING;
        this.nSessions = 0;
        this.sessions = new Session[MAX_SESSIONS];
        this.nParticipants = 0;
        this.participants = new Participant[MAX_PARTICIPANTS];
    }
    
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getField() {
        return this.field;
    }

    @Override
    public ConferenceState getState() {
        return this.conferenceState;
    }

    @Override
    public void changeState() {
        if (nSessions == 0) return;
        
        for (Session session : sessions) {
            if ( LocalDateTime.now().isBefore( ((SessionImpl)session).getEndTime() )) {
                this.conferenceState = ConferenceState.IN_PROGRESS;
                return;
            }
        }
        this.conferenceState = ConferenceState.FINISHED;
    }

    @Override
    public int getYear() {
        return this.year.getYear();
    }

    
    private void increaseSessionsArr() throws OutOfMemoryError {
        Session[] tmpSessions = new Session[nSessions * 2];
        
        try {
            for ( int x = 0; x < nSessions; x++ ) {
                tmpSessions[x] = this.sessions[x];
            }
        } catch (OutOfMemoryError e) {
            throw new OutOfMemoryError();
        }
        
        this.sessions = tmpSessions;
    }
    
    private int findSession(Session sn) {
        int pos = -1, x = 0;

        while (pos == -1 && x < nSessions) {
            if ( sessions[x].equals(sn) ) {
                pos = x;
            }
            x++;
        }
        return pos;
    }
    
    @Override
    public boolean addSession(Session sn) throws ConferenceException {
        if (sn == null) throw new ConferenceException("The session to add can't be null.");
        
        int pos = findSession(sn);

        try {
            if (nSessions == sessions.length) increaseSessionsArr();
        } catch (OutOfMemoryError ex) {
            throw new ConferenceException("Couldnt increase the allocated memory space to store the sessions");
        }

        if (pos != -1) return false;

        sessions[nSessions++] = (Session) sn;
        return true;
    }

    public int addSession(Session[] sn) throws ConferenceException {
        int x = 0;
        
        try {
            for ( Session ss : sn ) {
                if (ss == null) break;
                if ( addSession(ss) ) {
                    x++;
                }
            }
        } catch (ConferenceException ex) {
            throw new ConferenceException("Couldn't add the session, already exists or is not valid");
        }
        return x;
    }
    
    
    @Override
    public void removeSession(int i) throws ConferenceException {
        if (nSessions == 0) throw new ConferenceException("There are no Sessions to remove.");
        
        try {
            if ( sessions[i] == null ) throw new ArrayIndexOutOfBoundsException();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ConferenceException("Couldn't find the Session to remove.");
        }
        
        //sessions[i] = null;

        for ( int x = i; x < nSessions - 1; x++ ) {
            if ( sessions[x] == null ) {
                sessions[x] = sessions[x + 1];
            }
            x++;
        }
        sessions[--nSessions] = null;
    }

    // TODO remove sessionS
    
    @Override
    public Session getSession(int i) throws ConferenceException {
        
        if (nSessions == 0) throw new ConferenceException("There are no Sessions in Conferenec.");
        
        try {
            if ( sessions[i] == null ) throw new ArrayIndexOutOfBoundsException();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ConferenceException("Couldn't find the Session.");
        }
        return sessions[i];
    }

    @Override
    public Session[] getSessions() {
        Session[] tempSessions = new Session[this.nSessions];
        
        for ( int x = 0; x < nSessions; x++ ) {
            tempSessions[x] = sessions[x];
        }
        return tempSessions;
    }

    
    private void increaseParticipantsArr() throws OutOfMemoryError {
        Participant[] tmpParticipants = new Participant[nParticipants * 2];
        
        try {
            for ( int x = 0; x < nParticipants; x++ ) {
                tmpParticipants[x] = this.participants[x];
            }
        } catch (OutOfMemoryError e) {
            throw new OutOfMemoryError();
        }
        
        this.participants = tmpParticipants;
    }
    
    private int findParticipant(Participant p) {
        int pos = -1, x = 0;
        
        while ( pos == -1 && x < nParticipants ) {
            if ( this.participants[x].equals(p) ) {
                pos = x;
            }
            x++;
        }
        return pos;
    }
    
    @Override
    public void checkIn(Participant p) throws ConferenceException {
        if ( p == null ) throw new ConferenceException("The Participant can't be null");
        
        try {
            if ( nParticipants == this.participants.length ) increaseParticipantsArr();
        } catch (OutOfMemoryError ex) {
            throw new ConferenceException("Couldn't increase the allocated memory space to store the Participants");
        }
        
        int pos = findParticipant(p);
        
        if ( pos != -1 ) throw new ConferenceException("The Participant is already checked-in");
        
        this.participants[nParticipants++] = p;
    }

    @Override
    public Participant getParticipant(int i) throws ConferenceException {
        if (nParticipants == 0) throw new ConferenceException("There are no Participants checked-in");
        
        try {
            if ( this.participants[i] == null ) throw new ArrayIndexOutOfBoundsException();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ConferenceException("Couldn't find the Participant in the Conference");
        }
        return this.participants[i];
    }

    @Override
    public Participant[] getParticipants() {
        return this.participants;
    }

    @Override
    public Participant[] getSpeakerParticipants() {
        Participant[] speakerParticipants = new Participant[nParticipants];
        
        for (int x = 0; x < nParticipants; x++) {
            if ( this.participants[x] instanceof Presenter ) {
                speakerParticipants[x] = this.participants[x];
            }
        }
        return speakerParticipants;
    }

    @Override
    public Session[] getRoomSessions(int i, LocalDateTime ldt, LocalDateTime ldt1) throws ConferenceException {
        Session[] tempSessions = new Session[nSessions];
        
        for ( int x = 0; x < nSessions; x++ ) {
            if ( sessions[x].getRoom().getId() == i ) {
                if ( sessions[x].getStartTime().isAfter(ldt) && ((SessionImpl)sessions[x]).getEndTime().isBefore(ldt1) ) {
                    tempSessions[x] = sessions[x];
                }
            }
        }
        return tempSessions;
    }

    @Override
    public Room[] getRooms() {
        Room[] tempRooms = new Room[nSessions];
        
        for ( int x = 0; x < nSessions; x++ ) {
            tempRooms[x] = sessions[x].getRoom();
        }
        return tempRooms;
    }

    @Override
    public void generateSpeakerCertificates(String string) throws ConferenceException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void generateParticipantCertificates(String string) throws ConferenceException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getSchedule() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Statistics[] getNumberOfParticipantsBySession() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Statistics[] getNumberOfSessionsByRoom() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
