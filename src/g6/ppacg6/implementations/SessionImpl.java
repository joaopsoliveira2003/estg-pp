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

import estg.ipp.pt.tp02_conferencesystem.exceptions.SessionException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Presentation;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Room;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Session;
import g6.ppacg6.classes.Equipment;
import g6.ppacg6.classes.Theme;

import java.time.Duration;
import java.time.LocalDateTime;


public class SessionImpl implements Session {

    private int id = 0;
    private static int CID = 0;
    
    private String name;
    private int duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    private int nPresentations;
    private Presentation[] presentations;
    private static final int MAX_PRESENTATIONS = 10;
    
    private Theme sessionTheme;
    
    private Room room;

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
    }
    
    
    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getDuration() {
        Duration startTimeD = Duration.between(this.startTime, LocalDateTime.now());
        Duration endTimeD = Duration.between(this.endTime, LocalDateTime.now());
        return (int) (startTimeD.toMinutes() - endTimeD.toMinutes());
    }

    @Override
    public int getMaxDurationPerPresentation() {
        return (int) (Duration.between(this.startTime, this.endTime).toMinutes() / this.nPresentations); //??
    }

    @Override
    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    @Override
    public String getSessionTheme() {
        return this.sessionTheme.getTheme();
    }
    
    @Override
    public Room getRoom() {
        return this.room;
    }

    
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
        
        if (nPresentations == presentations.length) throw new 
        SessionException("Can't add more Presentations to this Session");
        
        int pos = findPresentation(prsntn);
        
        if ( pos != -1 ) throw new SessionException("The Presentation is already set in the Session");
        
        if ( ((PresentationImpl)prsntn).getnRequiredEquipments() <= 0 ) {
            throw new SessionException("Couldn't add the Presentation since it does not have the required Equipments");
        }
        
        Equipment[] requiredEquipments = ((PresentationImpl)prsntn).getRequiredEquipments();
        Equipment[] roomEquipments = ((RoomImpl)room).getEquipments();
        int nEquip = (requiredEquipments.length > roomEquipments.length) ? requiredEquipments.length : roomEquipments.length;
        
        for ( int x = 0; x < nEquip; x++ ) {
            if ( requiredEquipments[x] == null ) break;
            if (! (requiredEquipments[x].equals( roomEquipments[x] )) ) {
                throw new SessionException("The Room of the Session does not have the required Equipments to accomodate the Presentation");
            } else if ( requiredEquipments[x].hasProblems() ) {
                throw new SessionException("The Room of the Session does not have the required Equipments to accomodate the Presentation");
            }
        }
        
        presentations[nPresentations++] = prsntn;
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
        
        try {
            if ( presentations[i] == null ) throw new ArrayIndexOutOfBoundsException();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new SessionException("Couldnt find the presentation in the session.");
        }
        return presentations[i];
    }

    @Override
    public Presentation[] getPresentations() {
        Presentation[] tempPresentions = new Presentation[this.nPresentations];
        for (int x = 0; x < nPresentations; x++) {
            tempPresentions[x] = presentations[x];
        }
        return tempPresentions;
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
        return (this.startTime.isAfter(LocalDateTime.now()));
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

    @Override
    public String toString() {
        return "SessionImpl{" + "id=" + id + ", name=" + name + ", duration=" + 
        duration + ", startTime=" + startTime + ", endTime=" + endTime +
        ", nPresentations=" + nPresentations + ", presentations=[" + 
        listPresentations() + "], sessionTheme=" + sessionTheme + ", room=" + room + '}';
    }

    
    
    
}
