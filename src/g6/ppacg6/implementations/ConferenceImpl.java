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
import estg.ipp.pt.tp02_conferencesystem.interfaces.*;
import estg.ipp.pt.tp02_conferencesystem.io.interfaces.Exporter;
import estg.ipp.pt.tp02_conferencesystem.io.interfaces.Statistics;
import g6.ppacg6.auxiliary.DateValidations;
import g6.ppacg6.enumerations.ParticipantTypeEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

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
     * @apiNote the <b>conferenceState</b> is set as on_editing, <b>nSessions</b> is set as 0, the <b>sessions</b> array is initialized with the initial size, the <b>nParticipants</b> is set as 0, the <b>participants</b> array is initialized with the initial size
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
     * @param cs the ConferenceState
     */
    public void changeStateManual(ConferenceState cs) {
        this.conferenceState = cs;
    }

    /**
     * Gets the year of the Conference
     * @return int
     */
    @Override
    public int getYear() {
        if (this.year.getYear() > LocalDateTime.now().getYear()) return LocalDateTime.now().getYear();
        return this.year.getYear();
    }

    /**
     * Gets the date of the conference
     * @return LocalDateTime
     */
    public LocalDateTime getDate() {
        return this.year;
    }

    /**
     * Gets the number of participants of the Conference
     * @return int
     */
    public int getnParticipants() {
        return this.nParticipants;
    }

    /**
     * Increases the sessions array size
     * @throws OutOfMemoryError if the computer runs out of memory (potato pc)
     */
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
     * @throws ConferenceException if the session is null, if there is any problem with the redimension of the sessions array, if the conference is not in editing mode, if the room assigned to the session is already occupied, if the presenter of any session is already presenting in another session or if there's any presentation already scheduled in other session
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

        //check if occupied
        for ( Session session : this.getSessions() ) {
            if (session.getStartTime().plusMinutes(session.getDuration()).isAfter(sn.getStartTime())) {
                if (session.getRoom().equals(sn.getRoom())) {
                    throw new ConferenceException("The room is already occupied.");
                }
            }
        }

        //check if presenter is already in another session
        for ( Session session : this.getSessions() ) {
            if (session.getStartTime().plusMinutes(session.getDuration()).isAfter(sn.getStartTime())) {
                for (Participant p : session.getAllPresenters()) {
                    if (p == null) break;
                    for (Participant pSn : sn.getAllPresenters()) {
                        if (p.equals(pSn)) {
                            throw new ConferenceException("The presenter is already in another session.");
                        }
                    }
                }
            }
        }

        //check if the presentation is in another session
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
     * @return true if the session was removed, false otherwise
     * @throws ConferenceException if the conference is not in editing mode, if there are no sessions to remove or if the sessions doesn't exist
     */
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

    /**
     * Gets a specific session from the sessions array by index
     * @return Session
     * @throws ConferenceException if there are no sessions or if the session doesn't exist
     */
    @Override
    public Session getSession(int i) throws ConferenceException {
        // POR ID
        if (nSessions == 0) throw new ConferenceException("There are no Sessions in Conferenec.");
        
        try {
            if ( sessions[i] == null ) throw new NullPointerException();
        } catch (NullPointerException e) {
            throw new ConferenceException("Couldn't find the Session.");
        }
        return sessions[i];
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
     * Increases the Participants array size
     * @throws OutOfMemoryError if the computer runs out of memory
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
     * @throws ConferenceException if the conference is not in progress, if there are no sessions, if the participant is null, if the participant is already checked-in or with any memory error
     */
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
            // do nothing
            assert true;
        }
    }

    /**
     * Gets a specific participant from the participants array by id
     * @param i id
     * @return Participant
     * @throws ConferenceException if there are no participants checked-in
     */
    @Override
    public Participant getParticipant(int i) throws ConferenceException {
        if (nParticipants == 0) throw new ConferenceException("There are no Participants checked-in");
        
        try {
            for ( int p = 0; p < nParticipants; p++ ) {
                if ( this.participants[p].getId() == i ) {
                    return this.participants[p];
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ConferenceException("Couldn't find the Participant in the Conference");
        }
        return null;
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
     * @return Participant[]
     */
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

    /**
     * Finds a specific room by id
     * @param i id
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
     * @throws ConferenceException if the room ID is negative, if the room doesn't exists, if the conference is not in progress and if the Dates fail the validation
     */
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

    /**
     * Gets all rooms of the conference
     * @return Room[]
     */
    @Override
    public Room[] getRooms() {
        Room[] tempRooms = new Room[this.nSessions];
        int nRooms = 0;
        boolean found;

        for ( int x = 0; x < this.nSessions; x++ ) {
            if ( this.sessions[x].getRoom() == null ) break;
            tempRooms[x] = this.sessions[x].getRoom();
            nRooms++;
        }

        // Remove duplicates
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

        // Compress array
        Room[] rooms = new Room[nRooms];
        for ( int x = 0; x < nRooms; x++ ) {
            rooms[x] = tempRooms[x];
        }
        return rooms;
    }

    /**
     * Generates speaker certificates for all speakers
     * @param string file path
     * @throws ConferenceException if the conference is not finished or if the file path is invalid
     */
    @Override
    public void generateSpeakerCertificates(String string) throws ConferenceException {
        if (! (this.conferenceState.equals(ConferenceState.FINISHED)) ) throw new ConferenceException("The Conference is not finished.");

        try {
            if (string == null || string.equals("")) throw new
                    ConferenceException("The file path can't be null or empty.");
        } catch (ConferenceException e) {
            throw new ConferenceException(e.getMessage());
        }

        File directory = new File(string);
        if (!directory.exists()) {
            directory.mkdir();
        }

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

                            try {
                                String filename = string + "/" + p.getName() + pS.getId() + ".json";
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
     * @throws ConferenceException if the conference is not finished or if the file path is invalid
     */
    @Override
    public void generateParticipantCertificates(String string) throws ConferenceException {
        if (! (this.conferenceState.equals(ConferenceState.FINISHED)) ) throw new ConferenceException("The Conference is not finished.");

        try {
            if (string == null || string.equals("")) throw new
                    ConferenceException("The file path can't be null or empty.");
        } catch (ConferenceException e) {
            throw new ConferenceException(e.getMessage());
        }

        File directory = new File(string);
        if (!directory.exists()) {
            directory.mkdir();
        }

        for ( Participant p : this.participants ) {
            if ( p == null ) break;
            try {
                FileWriter file = new FileWriter(string + "/" + p.getId() + ".txt");
                file.write("We certify that the participant " + p.getName() + " is a participant of the conference " + this.getName() + " in the year " + this.getYear() + ".");
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

            Presentation[] tmpPresentations = s.getPresentations();
            for ( int a = 1; a < tmpPresentations.length; a++ ) {
                for( int b = 0; b < tmpPresentations.length - a; b++ ) {
                    if ( ( ( ((PresentationImpl)tmpPresentations[b]).getStartTime()).isAfter( ((PresentationImpl)tmpPresentations[b+1]).getStartTime()) ) )
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
            tempStatistics[x] = new StatisticsImpl(sessions[x].getName(), ((SessionImpl) sessions[x]).getnParticipants());
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

    /**
     * Exports all data to json files for posterior use
     * @return String (we suppose that this should be an array of strings, not just a string)
     * @throws IOException if the conference is not finished, if the conference has no sessions to export or any other error with files
     */
    @Override
    public String export() throws IOException {

        if (! (this.getState().equals(ConferenceState.FINISHED)) ) throw new IOException("The Conference is not finished");

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
            return this.generateNumberOfSessionsByRoom(this.getNumberOfSessionsByRoom()) + this.generateNumberOfParticipantsBySession(this.getNumberOfParticipantsBySession());
        } catch (ConferenceException e) {
            throw new IOException(e.getMessage());
        }
    }


    /**
     * Generates a JSON string with the number of sessions by room
     * @param statistics the statistics array
     * @return the JSON string
     */
    public String generateNumberOfSessionsByRoom(Statistics[] statistics) throws ConferenceException {
        if (! (this.conferenceState.equals(ConferenceState.FINISHED)) ) throw new ConferenceException("The Conference is not finished");

        JSONObject json = new JSONObject();
        json.put("type", "bar");

        JSONObject data = new JSONObject();
        JSONArray labels = new JSONArray();
        int i = 1;
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
        int i = 1;
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
     * Compares two Conference objects, based on the year of the conference
     * @param obj the object to compare
     * @return true if the year of the conference is the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (this == obj) return true;

        if ( obj.getClass() != this.getClass() ) return false;

        final ConferenceImpl other = (ConferenceImpl) obj;

        return ( this.year.getYear() == other.year.getYear() );
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
