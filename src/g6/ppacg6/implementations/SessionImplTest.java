package g6.ppacg6.implementations;

import estg.ipp.pt.tp02_conferencesystem.interfaces.Room;
import g6.ppacg6.classes.Theme;
import g6.ppacg6.classes.Topic;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SessionImplTest {

    @Test
    void getId() {
        Room room = new RoomImpl("room", 25);
        SessionImpl session = new SessionImpl("session", new Theme("theme"), LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 1, 1, 0), room);
        assertEquals(1, session.getId());
    }

    @Test
    void getName() {
        Room room = new RoomImpl("room", 25);
        SessionImpl session = new SessionImpl("session", new Theme("theme"), LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 1, 1, 0), room);
        assertEquals("session", session.getName());
    }

    @Test
    void getDuration() {
        Room room = new RoomImpl("room", 25);
        SessionImpl session = new SessionImpl("session", new Theme("theme"), LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 1, 1, 0), room);
        assertEquals(60, session.getDuration());
    }

    @Test
    void getMaxDurationPerPresentation() {
        Room room = new RoomImpl("room", 25);
        SessionImpl session = new SessionImpl("session", new Theme("theme"), LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 1, 1, 0), room);
        assertEquals(60, session.getMaxDurationPerPresentation());
    }

    @Test
    void getStartTime() {
        Room room = new RoomImpl("room", 25);
        SessionImpl session = new SessionImpl("session", new Theme("theme"), LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 1, 1, 0), room);
        assertEquals(LocalDateTime.of(2020, 1, 1, 0, 0), session.getStartTime());
    }

    @Test
    void getEndTime() {
        Room room = new RoomImpl("room", 25);
        SessionImpl session = new SessionImpl("session", new Theme("theme"), LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 1, 1, 0), room);
        assertEquals(LocalDateTime.of(2020, 1, 1, 1, 0), session.getEndTime());
    }

    @Test
    void getSessionTheme() {
        Room room = new RoomImpl("room", 25);
        SessionImpl session = new SessionImpl("session", new Theme("theme"), LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 1, 1, 0), room);
        assertEquals(new Theme("theme").getTheme(), session.getSessionTheme());
    }

    @Test
    void getRoom() {
        Room room = new RoomImpl("room", 25);
        SessionImpl session = new SessionImpl("session", new Theme("theme"), LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 1, 1, 0), room);
        assertEquals(room, session.getRoom());
    }

    @Test
    void getnParticipants() {
        Room room = new RoomImpl("room", 25);
        SessionImpl session = new SessionImpl("session", new Theme("theme"), LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 1, 1, 0), room);
        assertEquals(0, session.getnParticipants());
        //TODO: add a participant to the session
    }

    @Test
    void addPresentation() {

    }

    @Test
    void removePresentation() {
    }

    @Test
    void getPresentation() {
    }

    @Test
    void getPresentations() {
    }

    @Test
    void listPresentations() {
    }

    @Test
    void isStarted() {
    }

    @Test
    void getAllPresenters() {
    }

    @Test
    void getNumberOfPresentations() {
    }

    @Test
    void addParticipant() {
    }

    @Test
    void delParticipant() {
    }

    @Test
    void getParticipants() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testToString() {

    }
}