/*
 * Nome: Rui Alexandre Borba Vitorino
 * Número: 8190479
 * Turma: LSIRC12T1
 *
 * Nome: João Pedro Silva Oliveira
 * Número: 8210291
 * Turma: LSIRC11T2
 */

package implementations;

import estg.ipp.pt.tp02_conferencesystem.enumerations.ConferenceState;
import estg.ipp.pt.tp02_conferencesystem.exceptions.ConferenceException;
import estg.ipp.pt.tp02_conferencesystem.exceptions.SessionException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.*;
import estg.ipp.pt.tp02_conferencesystem.io.interfaces.Exporter;
import estg.ipp.pt.tp02_conferencesystem.io.interfaces.Statistics;

import auxiliary.*;
import enumerations.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/** Class responsible for the conference */
public class ConferenceImpl implements Conference, Exporter {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /** The conference's name */
    private String name;

    /** The conference's year */
    private LocalDateTime year;

    /** The conference's field */
    private String field;

    /** The conference's state */
    private ConferenceState conferenceState;

    /** The conference's sessions array */
    private Session[] sessions;

    /** The conference's number of sessions */
    private int nSessions = 0;

    /** The conference's initial number of sessions */
    private static final int INITIAL_SESSIONS = 10;

    /** The conference's participants array */
    private Participant[] participants;

    /** The conference's number of participants */
    private int nParticipants = 0;

    /** The conference's initial number of participants */
    private static final int INITIAL_PARTICIPANTS = 10;

    /**
     * Constructor for the Conference
     * @param name - name of the Conference
     * @param year - year of the Conference
     * @param field - field of the Conference
     * The <b>conferenceState</b> is set as on_editing, <b>nSessions</b> is 
     * set as 0, the <b>sessions</b> array is initialized with the initial 
     * size, the <b>nParticipants</b> is set as 0, the <b>participants</b> 
     * array is initialized with the initial size
     */
    public ConferenceImpl(String name, LocalDateTime year, String field) {
        this.name = name;
        this.year = year;
        this.field = field;
        this.conferenceState = ConferenceState.ON_EDITING;
        this.nSessions = 0;
        this.sessions = new Session[INITIAL_SESSIONS];
        this.nParticipants = 0;
        this.participants = new Participant[INITIAL_PARTICIPANTS];
    }

    /**
     * Gets the name of the Conference
     * @return String
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Gets the year of the Conference
     * If the year is not the current, return the current one
     * @return int
     */
    @Override
    public int getYear() {
        if (this.year.getYear() > LocalDateTime.now().getYear()) return LocalDateTime.now().getYear();
        return this.year.getYear();
    }
    
    /**
     * Gets the field of the Conference
     * @return String
     */
    @Override
    public String getField() {
        return this.field;
    }

    /**
     * Gets the state of the Conference
     * @return ConferenceState
     */
    @Override
    public ConferenceState getState() {
        return this.conferenceState;
    }

    /**
     * Gets the number of sessions of the Conference
     * @return LocalDateTime
     */
    public int getnSessions() {
        return this.nSessions;
    }
    
    /**
     * Gets the number of participants of the Conference
     * @return int
     */
    public int getnParticipants() {
        return this.nParticipants;
    }

    /**
     * Changes the state of the Conference by checking if there is any session going on
     */
    @Override
    public void changeState() {
        if (nSessions == 0) return;
        
        for (Session session : sessions) {
            if ( session == null ) break;
            if ( LocalDateTime.now().isBefore( ((SessionImpl)session).getEndTime() )) {
                this.conferenceState = ConferenceState.IN_PROGRESS;
                return;
            }
        }
        this.conferenceState = ConferenceState.FINISHED;
    }

    /**
     * Changes the conference state manually
     * Only for testing/developing purposes
     * @param cs the ConferenceState
     */
    public void changeStateManual(ConferenceState cs) {
        this.conferenceState = cs;
    }

    /**
     * Gets the date of the conference
     * @return LocalDateTime
     */
    public LocalDateTime getDate() {
        return this.year;
    }

    /**
     * Set the name of the Conference, if valid
     * Validations are made using the StringValidations Auxiliary Class
     * @param name of the Conference to set
     * @throws ConferenceException when the given name is not valid
     */
    public void setName(String name) throws ConferenceException {
        try {
            if (StringValidations.isValidString(name, 100) ) this.name = name;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            throw new ConferenceException(e.getMessage());
        }
    }

    /**
     * Set the year (date) of the Conference
     * @param year 
     * @throws ConferenceException if the date is null, or not the current year
     */
    public void setYear(LocalDateTime year) throws ConferenceException {
        if ( year == null ) throw new ConferenceException("The Date can't be null");
        
        if ( year.getYear() != LocalDateTime.now().getYear() ) throw new
                ConferenceException("The Date must be in the same Year");
        
        this.year = year;
    }

    /**
     * Set the field of the Conference, if valid
     * Validations are made using the StringValidations Auxiliary Class
     * @param field of the Conference to set
     * @throws ConferenceException when the given name is not valid
     */
    public void setField(String field) throws ConferenceException {
        try {
            if (StringValidations.isValidString(field, 100) ) this.field = field;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            throw new ConferenceException(e.getMessage());
        }
    }
    
    

    /**
     * Increase the capacity of the array participants[], twice the current size
     * This is possible creating a temporary array with the new size, and then
     * copying the old array to the new one, replacing it
     * @throws OutOfMemoryError when the programs runs out of memory 
     */
    private void increaseSessionsArr() throws OutOfMemoryError {
        Session[] tmpSessions = new Session[this.nSessions * 2];
        
        try {
            for ( int x = 0; x < nSessions; x++ ) {
                tmpSessions[x] = this.sessions[x];
            }
        } catch (OutOfMemoryError e) {
            throw new OutOfMemoryError();
        }
        
        this.sessions = tmpSessions;
    }

    /**
     * Finds a specific session in the sessions array
     * @param sn Session
     * @return position
     */
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

    /**
     * Adds a session to the sessions array
     * @param sn the session to add
     * @return true if the session was added, false otherwise
     * @throws ConferenceException if the session is null, if there is any 
     * problem with the redimension of the sessions array, if the conference 
     * is not in editing mode, if the room assigned to the session is already 
     * occupied, if the presenter of any session is already presenting in 
     * another session or if there's any presentation already scheduled in other session
     */
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

        if (! (this.conferenceState.equals(ConferenceState.ON_EDITING)) )
            throw new ConferenceException("The conference is not in editing mode.");

        // Check if the Room is already occupied in that schedule
        for ( Session session : this.getSessions() ) {
            if (session.getStartTime().plusMinutes(session.getDuration()).isAfter(sn.getStartTime())) {
                if (session.getRoom().equals(sn.getRoom())) {
                    throw new ConferenceException("The Room is already occupied.");
                }
            }
        }

        // Check if the Presenter is already in another Session int the same schedule
        for ( Session session : this.getSessions() ) {
            if (session.getStartTime().plusMinutes(session.getDuration()).isAfter(sn.getStartTime())) {
                for (Participant p : session.getAllPresenters()) {
                    if (p == null) break;
                    for (Participant pSn : sn.getAllPresenters()) {
                        if (p.equals(pSn)) {
                            throw new ConferenceException("The Presenter is already in another session.");
                        }
                    }
                }
            }
        }

        // Check if the Presentation is in another Session
        for ( Session session : this.getSessions() ) {
            for (Presentation p : session.getPresentations()) {
                if (p == null) break;
                for (Presentation pSn : sn.getPresentations()) {
                    if (p.equals(pSn)) {
                        throw new ConferenceException("The Presentation is already in another Session.");
                    }
                }
            }
        }

        sessions[nSessions++] = sn;
        return true;
    }

    /**
     * Removes a session from the sessions array
     * @param i the index of the session to remove
     * @throws ConferenceException if the conference is not in editing mode, 
     * if there are no sessions to remove or if the sessions doesn't exist
     */
    @Override
    public void removeSession(int i) throws ConferenceException {
        if (! (this.conferenceState.equals(ConferenceState.ON_EDITING)) ) throw new 
        ConferenceException("The conference is not in editing mode.");

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

    /**
     * Gets a specific session from the sessions array by index
     * @param i the ID of the session
     * @return Session
     * @throws ConferenceException if there are no sessions or if the session doesn't exist
     */
    @Override
    public Session getSession(int i) throws ConferenceException {
        if (nSessions == 0) throw new ConferenceException("There are no Sessions in Conferenec.");
        
        Session sessionFound = null;
        
        for ( Session session : this.sessions ) {
            if ( session == null ) break;
            if ( session.getId() == i ) {
                sessionFound = session;
            }
        }
        
        if (sessionFound == null) throw new ConferenceException("Couldnt find the Session");
        
        return sessionFound;
    }

    /**
     * Gets all the sessions of the conference
     * @return Session[]
     */
    @Override
    public Session[] getSessions() {
        Session[] tempSessions = new Session[this.nSessions];
        
        for ( int x = 0; x < nSessions; x++ ) {
            tempSessions[x] = sessions[x];
        }
        return tempSessions;
    }

    /**
     * Increase the capacity of the array participants[], twice the current size
     * This is possible creating a temporary array with the new size, and then
     * copying the old array to the new one, replacing it
     * @throws OutOfMemoryError when the programs runs out of memory 
     */
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

    /**
     * Finds a specific participant in the participants array
     * @param p Participant
     * @return position
     */
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

    /**
     * Checks-in a participant to the conference
     * @param p Participant
     * @throws ConferenceException 
     * If the conference is not in progress
     * If there are no sessions
     * If the participant is null
     * If the participant is already checked-in
     */
    @Override
    public void checkIn(Participant p) throws ConferenceException {
        if (! (this.conferenceState.equals(ConferenceState.IN_PROGRESS)) ) throw new ConferenceException("The conference is not in progress.");

        if ( nSessions == 0 ) throw new ConferenceException("There are no Sessions in the Conference.");

        if ( p == null ) throw new ConferenceException("The Participant can't be null");

        int pos = findParticipant(p);

        if ( pos != -1 ) throw new ConferenceException("The Participant is already checked-in");

        try {
            if ( nParticipants == this.participants.length ) increaseParticipantsArr();
        } catch (OutOfMemoryError ex) {
            throw new ConferenceException("Couldn't increase the allocated memory space to store the Participants");
        }

        // Add the Participant to the Conference
        this.participants[nParticipants++] = p;

        // Try to add the Participant to all Sessions in the Conference
        // If the Participant is already in the session, do nothing
        try {
            for (int x = 0; x < nSessions; x++) {
                ((SessionImpl) this.sessions[x]).addParticipant(p);
            }
        } catch (SessionException ex) {
            // do nothing
            assert true;
        }
    }

    /**
     * Gets a specific participant from the participants array by ID
     * @param i id of the Participant
     * @return Participant
     * @throws ConferenceException
     * If there are no participants checked-in
     * Couldn't find the participant in the conference
     */
    @Override
    public Participant getParticipant(int i) throws ConferenceException {
        if (nParticipants == 0) throw new ConferenceException("There are no Participants checked-in");
        
        Participant participantFound = null;
        
        for ( Participant participant : this.participants ) {
            if ( participant == null ) break;
            if ( participant.getId() == i ) {
                participantFound = participant;
            }
        }
        
        if (participantFound == null) {
            throw new ConferenceException("Couldn't find the Participant in the Conference");
        }
        
        return participantFound;
    }

    /**
     * Gets all the participants of the conference
     * @return Participant[]
     */
    @Override
    public Participant[] getParticipants() {
        return this.participants;
    }

    /**
     * Gets all participants that are speakers
     * Iterating over the array of participants, and filtering the ones
     * that have the participantType as SPEAKER (enum)
     * @return Participant[]
     */
    @Override
    public Participant[] getSpeakerParticipants() {
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

    /**
     * Finds a specific room by id
     * @param i id of the Room to find
     * @return true if the room exists, false otherwise
     * @throws ConferenceException - if there are no sessions
     */
    private boolean findRoom(int i) throws ConferenceException {
        if ( nSessions == 0 ) throw new ConferenceException("There are no Sessions in the Conference.");

        for ( int x = 0; x < nSessions; x++ ) {
            if ( sessions[x].getRoom().getId() == i ) return true;
        }
        return false;
    }

    /**
     * Gets the sessions of a specific room by id and time interval
     * @param i id
     * @param ldt start time
     * @param ldt1 end time
     * @return Session[]
     * @throws ConferenceException
     * If the room ID is negative
     * If the room doesn't exists
     * If the conference is not in progress
     * If the Dates fail the validation
     */
    @Override
    public Session[] getRoomSessions(int i, LocalDateTime ldt, LocalDateTime ldt1) throws ConferenceException {
        if ( i < 0 ) throw new ConferenceException("The room ID can't be negative.");

        if (! (this.findRoom(i)) ) throw new ConferenceException("The room doesn't exist.");

        if(! (this.conferenceState.equals(ConferenceState.IN_PROGRESS)) ) throw new 
        ConferenceException("The conference is not in progress.");

        if ( ldt == null || ldt1 == null ) throw new ConferenceException("The start and end times can't be null.");

        /*try {
            DateValidations.isValidDate(ldt, this.year);
        } catch (Exception ex) {
            throw new ConferenceException(ex.getMessage());
        }*/

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

    /**
     * Gets all rooms of the conference, without duplicated values
     * @return Room[]
     */
    @Override
    public Room[] getRooms() {
        Room[] tempRooms = new Room[this.nSessions];
        int nRooms = 0;
        boolean found;

        for ( int x = 0; x < this.nSessions; x++ ) {
            if ( this.sessions[x].getRoom() == null ) break;
            tempRooms[nRooms++] = this.sessions[x].getRoom();
        }

        // Remove duplicates
        for ( int x = 0; x < nRooms; x++ ) {
            for ( int y = x + 1; y < nRooms; y++ ) {
                if ( tempRooms[x].equals(tempRooms[y]) ) {
                    tempRooms[y] = tempRooms[nRooms - 1];
                    nRooms--;
                    y--;
                }
            }
        }
        
        // Compress array
        Room[] rooms = new Room[nRooms];
        for ( int x = 0; x < nRooms; x++ ) {
            rooms[x] = tempRooms[x];
        }
        return rooms;
    }

    /**
     * Generates speaker certificates for all speakers, in JSON format
     * @param string file path
     * @throws ConferenceException
     * If the conference is not finished
     * If the file path is invalid
     */
    @Override
    public void generateSpeakerCertificates(String string) throws ConferenceException {
        if (! (this.conferenceState.equals(ConferenceState.FINISHED)) ) throw new 
        ConferenceException("The Conference is not finished.");

        try {
            if (string == null || string.equals("")) throw new
                    ConferenceException("The file path can't be null or empty.");
        } catch (ConferenceException e) {
            throw new ConferenceException(e.getMessage());
        }

        // Check if the directory exists, otherwise, create it
        File directory = new File(string);
        if (!directory.exists()) {
            directory.mkdir();
        }

        // Create a JSON Object using the Collection JSONSimple
        for ( Participant p : this.participants ) {
            if ( p == null ) break;
            if ( ((ParticipantImpl)p).getParticipantType().equals(ParticipantTypeEnum.SPEAKER) ) {
                JSONObject json = new JSONObject();

                json.put("name", p.getName());
                json.put("conference", this.getName());
                json.put("year", this.getYear());

                for ( Session s : this.sessions ) {
                    if ( s == null ) break;
                    for ( Presentation pS : s.getPresentations() ) {
                        if ( pS == null ) break;
                        if ( pS.getPresenter().equals(p) ) {
                            JSONObject presentation = new JSONObject();
                            presentation.put("title", pS.getTitle());
                            String date = String.valueOf(s.getStartTime());
                            presentation.put("date", date);
                            json.put("presentation", presentation);

                            // Save the JSON Object to a JSON file
                            try {
                                String filename = string + "/" + p.getId()
                                + "_" + pS.getId() + ".json";
                                FileWriter file = new FileWriter(filename);
                                file.write(json.toJSONString());
                                file.close();
                            } catch (IOException e) {
                                throw new ConferenceException(e.getMessage());
                            }
                        }
                    }
                }
            }
        }
    }

    
    /**
     * Generates participant certificates for all participants
     * @param string file path
     * @throws ConferenceException
     * If the conference is not finished
     * If the file path is invalid
     */
    @Override
    public void generateParticipantCertificates(String string) throws ConferenceException {
        if (! (this.conferenceState.equals(ConferenceState.FINISHED)) ) throw new 
        ConferenceException("The Conference is not finished.");

        try {
            if (string == null || string.equals("")) throw new
                    ConferenceException("The file path can't be null or empty.");
        } catch (ConferenceException e) {
            throw new ConferenceException(e.getMessage());
        }

        // Check if the directory exists, otherwise, create it
        File directory = new File(string);
        if (!directory.exists()) {
            directory.mkdir();
        }

        // Create a JSON Object using the Collection JSONSimple
        for ( Participant p : this.participants ) {
            if ( p == null ) break;
            try {
                // Save the JSON Object to a txt file
                FileWriter file = new FileWriter(string + "/" + p.getId() + ".txt");
                file.write("We certify that " + p.getName() + " is a participant of the conference " +
                        this.getName() + " in the year " + this.getYear() + ".");
                file.close();
            } catch (IOException e) {
                throw new ConferenceException(e.getMessage());
            }
        }
    }


    /**
     * Generates a schedule for the conference, in this method we use the Bubble Sort Algorithm
     * to sort the sessions and presentations by start time
     * @return String
     */
    @Override
    public String getSchedule() {
        String str = this.getName() + " " + this.getYear() + "\n";

        // Bubble Sort for the Sessions
        Session[] tmpSessions = this.getSessions();
        for ( int a = 1; a < tmpSessions.length; a++ ) {
            for( int b = 0; b < tmpSessions.length - a; b++ ) {
                if ( ((tmpSessions[b].getStartTime()).isAfter((tmpSessions[b+1].getStartTime()))) )
                {
                    Session temp = tmpSessions[b];
                    tmpSessions[b] = tmpSessions[b + 1];
                    tmpSessions[b +1 ] = temp;
                }
            }
        }


        for ( Session s : tmpSessions ) {
            if ( s == null ) break;
            str += "\n" + s.getName() + " /" + s.getSessionTheme() + "\\ at " + s.getRoom().getName() +
                "\n" + s.getStartTime().format(dateTimeFormatter) + " - "
                    + ((SessionImpl)s).getEndTime().format(dateTimeFormatter) + "\nPresentations:\n";

            // Bubble Sort for the Presentations
            Presentation[] tmpPresentations = s.getPresentations();
            for ( int a = 1; a < tmpPresentations.length; a++ ) {
                for( int b = 0; b < tmpPresentations.length - a; b++ ) {
                    if ( ( ( ((PresentationImpl)tmpPresentations[b]).getStartTime()).isAfter( 
                            ((PresentationImpl)tmpPresentations[b+1]).getStartTime()) ) )
                    {
                        Presentation temp = tmpPresentations[b];
                        tmpPresentations[b] = tmpPresentations[b + 1];
                        tmpPresentations[b + 1] = temp;
                    }
                }
            }

            for ( Presentation p : tmpPresentations ) {
                if ( p == null ) break;
                str += "  " + p.getTitle() + " | Presenter: " + p.getPresenter().getName() + "\n\t" +
                ((PresentationImpl)p).getStartTime().getHour() + ":" + ((PresentationImpl)p).getStartTime().getMinute()
                 + " - " + ((PresentationImpl)p).getEndTime().getHour() + ":" + ((PresentationImpl)p).getEndTime().getMinute() + "\n";
            }
        }

        return str;
    }

    /**
     * Gets the number of participants by session
     * @return Statistics[]
     */
    @Override
    public Statistics[] getNumberOfParticipantsBySession() {

        Statistics[] tempStatistics = new Statistics[nSessions];

        for ( int x = 0; x < nSessions; x++ ) {
            tempStatistics[x] = new StatisticsImpl(sessions[x].getName(), 
                    ((SessionImpl) sessions[x]).getnParticipants());
        }
        return tempStatistics;
    }

    /**
     * Gets the number of sessions by room
     * @return Statistics[]
     */
    @Override
    public Statistics[] getNumberOfSessionsByRoom() {

        Room[] rooms = this.getRooms();
        int nRooms = rooms.length;

        Statistics[] tempStatistics = new Statistics[nRooms];
        int[] sessionsByRoom = new int[nRooms];

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

    /**
     * Exports all data to JSON files for posterior use
     * @return String (we suppose that this should be an array of strings, not just a string)
     * @throws IOException
     * If the conference is not finished
     * If the conference has no sessions to export
     * or any other error with files
     */
    @Override
    public String export() throws IOException {

        if (! (this.getState().equals(ConferenceState.FINISHED)) ) throw new 
        IOException("The Conference is not finished");

        if ( this.getnSessions() == 0 ) throw new IOException("The Conference has no Sessions to export");

        // save first Graphic to a JSON file
        try {
            FileWriter file = new FileWriter("numberOfSessionsByRoom.json");
            file.write(this.generateNumberOfSessionsByRoom(this.getNumberOfSessionsByRoom()));
            file.close();
        } catch (ConferenceException | IOException e) {
            throw new IOException(e.getMessage());
        }

        // save second Graphic to a JSON file
        try {
            FileWriter file = new FileWriter("numberOfParticipantsBySession.json");
            file.write(this.generateNumberOfParticipantsBySession(this.getNumberOfParticipantsBySession()));
            file.close();
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }

        try {
            return this.generateNumberOfSessionsByRoom(this.getNumberOfSessionsByRoom()) + 
                    this.generateNumberOfParticipantsBySession(this.getNumberOfParticipantsBySession());
        } catch (ConferenceException e) {
            throw new IOException(e.getMessage());
        }
    }


    /**
     * Generates a JSON string with the number of sessions by room
     * @param statistics the statistics array
     * @return the JSON string
     * @throws ConferenceException - when the conference is not in state FINISHED
     */
    public String generateNumberOfSessionsByRoom(Statistics[] statistics) throws ConferenceException {
        if (! (this.conferenceState.equals(ConferenceState.FINISHED)) ) throw new 
        ConferenceException("The Conference is not finished");

        JSONObject json = new JSONObject();
        json.put("type", "bar");

        JSONObject data = new JSONObject();
        JSONArray labels = new JSONArray();

        for (Statistics stat : statistics) {
            labels.add(stat.getDescription());
        }
        data.put("labels", labels);
        json.put("data", data);

        JSONArray datasets = new JSONArray();

        JSONObject rooms = new JSONObject();

        rooms.put("label", "Sessions by Rooms");
        JSONArray dataRooms = new JSONArray();
        for (Statistics stat : statistics) {
            dataRooms.add(stat.getValue());
        }
        rooms.put("data", dataRooms);
        datasets.add(rooms);

        data.put("datasets", datasets);

        System.out.println(json.toJSONString());

        return json.toString();
    }

    /**
     * Generates a JSON string with the number of participants by session
     * @param statistics the statistics array
     * @return the JSON string
     */
    public String generateNumberOfParticipantsBySession(Statistics[] statistics) {
        JSONObject json = new JSONObject();
        json.put("type", "bar");

        JSONObject data = new JSONObject();
        JSONArray labels = new JSONArray();

        for (Statistics stat : statistics) {
            labels.add(stat.getDescription());
        }
        data.put("labels", labels);
        json.put("data", data);

        JSONArray datasets = new JSONArray();

        JSONObject rooms = new JSONObject();

        rooms.put("label", "Participants by Sessions");
        JSONArray dataRooms = new JSONArray();
        for (Statistics stat : statistics) {
            dataRooms.add(stat.getValue());
        }
        rooms.put("data", dataRooms);
        datasets.add(rooms);

        data.put("datasets", datasets);

        System.out.println(json.toJSONString());

        return json.toJSONString();
    }

    /**
     * Compares two Conference's, by year (actual year, not variable)
     * @param obj the conference to compare with
     * False when:
     * Object is null, or not an object of the Conference Interface
     * True when:
     * It's the same object, or has the same properties
     * @return boolean 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (this == obj) return true;

        if ( obj.getClass() != this.getClass() ) return false;

        final Conference other = (Conference) obj;

        try {
            return ( this.year.getYear() == ((ConferenceImpl)other).year.getYear() );
        } catch (ClassCastException ex) {
            return false;
        }
    }

    /**
     * List all the Sessions in the conference
     * @return String
     */
    public String listSessions() {
        String str = "";

        if ( nSessions == 0 ) return "No Sessions";

        for (Session s : this.sessions) {
            if ( s == null ) break;
            str += s.toString() + "\n";
        }
        return str;
    }

    /**
     * List all the Participants in the conference
     * @return String
     */
    public String listParticipants() {
        String str = "";

        if ( nParticipants == 0 ) return "No Participants";

        for (Participant p : this.participants) {
            if ( p == null ) break;
            str += p.toString() + "\n";
        }
        return str;
    }

    /**
     * List all the properties of the conference
     * @return String
     */
    @Override
    public String toString() {
        return "ConferenceImpl{" +
                "name='" + name + '\'' +
                ", year=" + this.getYear() +
                ", field='" + field + '\'' +
                ", conferenceState=" + conferenceState +
                ", sessions=[" + listSessions() +
                "], nSessions=" + nSessions +
                ", participants=[" + listParticipants() +
                "], nParticipants=" + nParticipants +
                '}';
    }
}
