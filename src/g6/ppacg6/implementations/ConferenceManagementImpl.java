package g6.ppacg6.implementations;

import estg.ipp.pt.tp02_conferencesystem.enumerations.ConferenceState;
import estg.ipp.pt.tp02_conferencesystem.exceptions.ConferenceException;
import estg.ipp.pt.tp02_conferencesystem.exceptions.SessionException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Room;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Session;
import g6.ppacg6.classes.Equipment;
import g6.ppacg6.classes.Professor;
import g6.ppacg6.classes.Student;
import g6.ppacg6.interfaces.ConferenceManagement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ConferenceManagementImpl implements ConferenceManagement {

    private int nConferences;
    private ConferenceImpl[] conferences;
    private static final int MAX_CONFERENCES = 10;

    public ConferenceManagementImpl() {
        this.nConferences = 0;
        this.conferences = new ConferenceImpl[MAX_CONFERENCES];
    }

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

    /**
     * Allows the exporting to JSON of a single Conference, if the initial validations are passed
     * @param conference
     * @return String, the filename of the generaed/exported JSON
     * @throws ConferenceException
     */
    public String exportOne(ConferenceImpl conference) throws ConferenceException {
        if (! (conference.equals(ConferenceState.FINISHED)) ) throw new ConferenceException("The Conference is not finished");

        if ( conference == null ) throw new ConferenceException("Can't export a Conference that is null");

        if ( nConferences == 0 ) throw new ConferenceException("There are no Conferences to export");

        if ( findConference(conference) == -1 ) throw new ConferenceException("The Conference is not in the list");

        if ( conference.getnSessions() == 0 ) throw new ConferenceException("The Conference has no Sessions to export");

        JSONObject obj = new JSONObject();
        obj.put("name", conference.getName());
        obj.put("date", conference.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        obj.put("field", conference.getField());
        obj.put("state", conference.getState().toString());
        obj.put("numberOfSessions", conference.getnSessions());
        JSONArray conferenceSessions = new JSONArray();
        try {
            for ( int x = 0; x < conference.getnSessions(); x++ ) {
                JSONObject s = new JSONObject();
                Session session = conference.getSession(x);

                s.put("name", session.getName());
                s.put("start-time", session.getStartTime());
                s.put("end-time", ((SessionImpl)session).getEndTime());
                s.put("duration", session.getDuration());
                s.put("theme", session.getSessionTheme());

                JSONArray sessionRoom = new JSONArray();
                try {
                    JSONObject r = new JSONObject();
                    Room room = session.getRoom();
                    r.put("id", session.getId());
                    r.put("name", room.getName());
                    r.put("numberOfSeats", room.getNumberOfSeats());
                    r.put("numberOfEquipments", ((RoomImpl)room).getnEquipments());

                    JSONArray roomEquipments = new JSONArray();
                    try {
                        for ( int y = 0; y < ((RoomImpl) room).getnEquipments(); y++ ) {
                            JSONObject e = new JSONObject();
                            e.put("id", ((RoomImpl)room).getEquipment(y).getId());
                            e.put("type", ((RoomImpl)room).getEquipment(y).getEquipment());
                            e.put("hasProblems", ((RoomImpl)room).getEquipment(y).hasProblems() ? "true" : "false");

                            roomEquipments.add(e);
                        }
                    } catch ( Exception e ) {
                        System.out.println(e.getMessage());
                    }
                    sessionRoom.add(r);
                } catch ( Exception e ) {
                    System.out.println(e.getMessage());
                }
                s.put("room", sessionRoom);
            }

        } catch (ConferenceException ex) {
            ex.getMessage();
        }
        obj.put("sessions", conferenceSessions);
        obj.put("numberOfParticipants", conference.getnParticipants());
        JSONArray conferenceParticipants = new JSONArray();
        try {
            JSONObject p = new JSONObject();
            for ( int x = 0; x < conference.getnParticipants(); x++ ) {
                Participant participant = conference.getParticipant(x);
                p.put("id", participant.getId());
                p.put("name", participant.getName());
                p.put("bio", participant.getBio());

                switch (participant.getClass().getName()) {
                    case "Professor": {
                        p.put("degree", ((Professor) participant).getDegree());
                        p.put("expertIn", ((Professor) participant).getExpertIn());

                        JSONArray papers = new JSONArray();
                        try {
                            JSONObject pap = new JSONObject();
                            for (int z = 0; z < ((Professor) participant).getnPapers(); z++) {
                                pap.put("id", ((Professor) participant).getPaper(z).getId());
                                pap.put("title", ((Professor) participant).getPaper(z).getTitle());
                                pap.put("theme", ((Professor) participant).getPaper(z).getTheme());
                                pap.put("topic", ((Professor) participant).getPaper(z).getTopic());

                                papers.add(pap);
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        p.put("papers", papers);
                        break;
                    }
                    case "Student": {
                        p.put("course", ((Student)participant).getCourse());
                        p.put("courseYear", ((Student)participant).getCourseYear());
                        break;
                    }
                }
                conferenceParticipants.add(p);
            }
        } catch (ConferenceException ex) {
            System.out.println(ex.getMessage());
        }
        obj.put("participants", conferenceParticipants);
        return obj.toJSONString();


    }

    @Override
    public String export() throws IOException {
        
        JSONObject obj = new JSONObject();
        // save this JSON to a file
        return obj.toString();
    }
}
