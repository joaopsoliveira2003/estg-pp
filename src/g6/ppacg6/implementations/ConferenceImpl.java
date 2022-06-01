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

import estg.ipp.pt.tp02_conferencesystem.enumerations.ConferenceState;

import estg.ipp.pt.tp02_conferencesystem.exceptions.ConferenceException;

import estg.ipp.pt.tp02_conferencesystem.exceptions.SessionException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Conference;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Room;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Session;

import estg.ipp.pt.tp02_conferencesystem.io.interfaces.Statistics;
import g6.ppacg6.enumerations.ParticipantTypeEnum;
import g6.ppacg6.auxiliary.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public int getnSessions() {
        return this.nSessions;
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

    public LocalDateTime getDate() {
        return this.year;
    }

    public int getnParticipants() {
        return this.nParticipants;
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
        if (! (this.conferenceState.equals(ConferenceState.ON_EDITING)) ) throw new ConferenceException("The conference is not in editing mode.");

        // ver se a sala esta ocupada, considerando startTime e duration
        //sera?
        for ( int sR = 0; sR < nSessions; sR++ ) {
            if ( this.sessions[sR].getRoom().equals(sn.getRoom()) ) {
                if ( this.sessions[sR].getStartTime().isBefore(sn.getStartTime()) &&
                        this.sessions[sR].getDuration() == sn.getDuration() ) {
                    throw new ConferenceException("The session is overlapping with another session.");
                }
            }
        }
        // se participant ja esta a apresnetar em outra sessions no mesmo intervalo
        // sera?
        try {
            for (int s = 0; s < nSessions; s++) {
                if ( this.sessions[s].getStartTime().isAfter(sn.getStartTime()) ) {
                    for (int sA = 0; sA < this.sessions[s].getNumberOfPresentations(); sA++) {
                        this.sessions[s].getPresentation(sA).getPresenter().equals(sn.getPresentation(sA).getPresenter());
                    }
                }
            }
        } catch (SessionException e) {
            throw new ConferenceException(e.getMessage());
        }
        // se uma apresentacao de esta sessao esta noutras sessoes

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
        if (! (this.conferenceState.equals(ConferenceState.ON_EDITING)) ) throw new ConferenceException("The conference is not in editing mode.");

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
        if (! (this.conferenceState.equals(ConferenceState.ON_EDITING)) ) throw new ConferenceException("The conference is not in editing mode.");

        if (nSessions == 0) throw new ConferenceException("There are no Sessions to remove.");
        
        try {
            if ( sessions[i] == null ) throw new ArrayIndexOutOfBoundsException();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ConferenceException("Couldn't find the Session to remove.");
        }
        
        sessions[i] = null;

        for ( int x = i; x < nSessions - 1; x++ ) {
            if ( sessions[x] == null ) {
                sessions[x] = sessions[x + 1];
            }
            x++;
        }
        sessions[--nSessions] = null;
    }

    public void removeSessions(Session[] sn) throws ConferenceException {
        if (! (this.conferenceState.equals(ConferenceState.ON_EDITING)) ) throw new ConferenceException("The conference is not in editing mode.");

        if (sn == null) throw new ConferenceException("The sessions to remove can't be null.");
        try {
            for ( Session ss : sn ) {
                if (ss == null) throw new NullPointerException();
                removeSession(findSession(ss));
            }
        } catch (NullPointerException ex) {
            throw new ConferenceException("Couldn't find the Session to remove.");
        }
    }
    
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
        if (! (this.conferenceState.equals(ConferenceState.IN_PROGRESS)) ) throw new ConferenceException("The conference is not in progress.");

        if ( nSessions == 0 ) throw new ConferenceException("There are no Sessions in the Conference.");

        // Add the Participant to the Conference
        if ( p == null ) throw new ConferenceException("The Participant can't be null");

        int pos = findParticipant(p);

        if ( pos != -1 ) throw new ConferenceException("The Participant is already checked-in");

        try {
            if ( nParticipants == this.participants.length ) increaseParticipantsArr();
        } catch (OutOfMemoryError ex) {
            throw new ConferenceException("Couldn't increase the allocated memory space to store the Participants");
        }

        this.participants[nParticipants++] = p;

        // Add the Participant to all Sessions in the Conference
        try {
            for (int x = 0; x < nSessions; x++) {
                ((SessionImpl) this.sessions[x]).addParticipant(p);
            }
        } catch (SessionException ex) {
            throw new ConferenceException(ex.getMessage());
        }
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
        //remove duplicates ones
        Participant[] speakerParticipants = new Participant[nParticipants];

        boolean canAdd = false;
        int counter = 0;
        
        for (int x = 0; x < nParticipants; x++) {
            if ( ((ParticipantImpl)this.participants[x]).getParticipantType().equals(ParticipantTypeEnum.SPEAKER) ) {
                canAdd = true;
            }
            if (canAdd) {
                speakerParticipants[counter++] = this.participants[x];
                canAdd = false;
            }
        }
        return speakerParticipants;
    }

    private boolean findRoom(int i) throws ConferenceException {
        if ( nSessions == 0 ) throw new ConferenceException("There are no Sessions in the Conference.");

        for ( int x = 0; x < nSessions; x++ ) {
            if ( sessions[x].getRoom().getId() == i ) return true;
        }
        return false;
    }

    @Override
    public Session[] getRoomSessions(int i, LocalDateTime ldt, LocalDateTime ldt1) throws ConferenceException {
        if ( i < 0 ) throw new ConferenceException("The room ID can't be negative.");

        if (!this.findRoom(i)) throw new ConferenceException("The room doesn't exist.");

        if(! (this.conferenceState.equals(ConferenceState.IN_PROGRESS)) ) throw new ConferenceException("The conference is not in progress.");

        if ( ldt == null || ldt1 == null ) throw new ConferenceException("The start and end times can't be null.");

        try {
            DateValidations.isValidDate(ldt, this.year);
        } catch (ConferenceException ex) {
            throw new ConferenceException(ex.getMessage());
        }

        Session[] tempSessions = new Session[this.nSessions];
        
        for ( int x = 0; x < this.nSessions; x++ ) {
            if ( this.sessions[x].getRoom().getId() == i ) {
                if ( this.sessions[x].getStartTime().isAfter(ldt) &&
                        ((SessionImpl)this.sessions[x]).getEndTime().isBefore(ldt1) ) {
                    tempSessions[x] = this.sessions[x];
                }
            }
        }
        return tempSessions;
    }

    @Override
    public Room[] getRooms() {
        Room[] tempRooms = new Room[this.nSessions];
        int nRooms = 0;
        boolean found;
        for ( int x = 0; x < this.nSessions; x++ ) {
            tempRooms[x] = this.sessions[x].getRoom();
        }

        for (int x = 1; x < tempRooms.length; x++) {
            found = false;
            for (int y = 0; y < nRooms; y++) {
                if (tempRooms[x].equals(tempRooms[y])) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                tempRooms[nRooms++] = tempRooms[x];
            }
        }

        Room[] rooms = new Room[nRooms];
        for ( int x = 0; x < nRooms; x++ ) {
            rooms[x] = tempRooms[x];
        }
        return rooms;
    }

    //file path
    @Override
    public void generateSpeakerCertificates(String string) throws ConferenceException {
        if (! (this.conferenceState.equals(ConferenceState.FINISHED)) ) throw new ConferenceException("The Conference is not finished.");

        // GERAR EM JSON, nome, nomeCOnferencia, ano, titulo apresentacao, data
        // string e nome da pasta
        // erros se nao der para gerar o ficheiro
        // nome ficheiro: idParticipant + idPresentation

        // iterar sobre todos os participantes da CONFERENCIA e ver quais sao speaker
        // para cada speaker gerar um certificado
        Participant[] speakers = this.getSpeakerParticipants();

        File directory = new File("conferenceCertificates/");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        for ( int x = 0; x < speakers.length; x++ ) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(directory + speakers[x].getName() + string));
                out.writeObject(speakers[x].toString());
                out.close();
            } catch (Exception e) {
                throw new ConferenceException(e.getMessage());
            }
        }
    }

    @Override
    public void generateParticipantCertificates(String string) throws ConferenceException {
        if (! (this.conferenceState.equals(ConferenceState.FINISHED)) ) throw new ConferenceException("The Conference is not finished.");

        // GERAR EM JSON, nome, nomeConferencia, ano
        // string e nome da pasta
        // erros se nao der para gerar o ficheiro
        // nome ficheiro: idParticipant

        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getSchedule() {
        // ordenar e nao usar stringBuilder
        StringBuilder sb = new StringBuilder();
        for (Session session : this.sessions) {
            if (session != null) {
                sb.append(session.getName()).append(":\n\tStart: ").append(session.getStartTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))).append("\n\tEnd: ").append(((SessionImpl) session).getEndTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))).append("\n\tRoom: ").append(session.getRoom().getName()).append("\n");
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public Statistics[] getNumberOfParticipantsBySession() {
        if (! (this.conferenceState.equals(ConferenceState.FINISHED)) ) return null;

        Statistics[] tempStatistics = new Statistics[nSessions];
        for ( int x = 0; x < nSessions; x++ ) {
            tempStatistics[x] = new StatisticsImpl(sessions[x].getName(), ((SessionImpl) sessions[x]).getnParticipants());
        }
        return tempStatistics;
    }

    @Override
    public Statistics[] getNumberOfSessionsByRoom() {
        // We check on JsonGenerator if the conference is finished, since we can't throw an exception here
        if (! (this.conferenceState.equals(ConferenceState.FINISHED)) ) return null;

        Room[] rooms = this.getRooms();
        int nRooms = rooms.length;
        Statistics[] tempStatistics = new Statistics[nRooms];
        int[] sessionsByRoom = new int[nRooms];

        for ( int x = 0; x < nRooms; x++ ) {
            sessionsByRoom[x] = 0;
        }

        for (int x = 0; x < nSessions; x++) {
            for (int y = 0; y < nRooms; y++) {
                if (this.sessions[x].getRoom().equals(rooms[y])) {
                    sessionsByRoom[y]++;
                }
            }
        }

        for (int x = 0; x < nRooms; x++) {
            tempStatistics[x] = new StatisticsImpl(rooms[x].getName(), sessionsByRoom[x]);
        }
        return tempStatistics;
    }

}
