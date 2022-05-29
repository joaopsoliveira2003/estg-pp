/*
* Nome: Rui Alexandre Borba Vitorino
* Numero: 8190479
* Turma: LSIRC12T1
*
* Nome: Joao Pedro Silva Oliveira
* Numero: 8210291
* Turma: LSIRC12T2
*/

package g6.ppacg6.main;

import estg.ipp.pt.tp02_conferencesystem.exceptions.SessionException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Conference;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Presentation;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Room;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Session;
import g6.ppacg6.ConferenceImpl;
import g6.ppacg6.Equipment;
import g6.ppacg6.PresentationImpl;
import g6.ppacg6.Presenter;
import g6.ppacg6.RoomImpl;
import g6.ppacg6.SessionImpl;
import g6.ppacg6.Student;
import g6.ppacg6.Theme;
import g6.ppacg6.Topic;
import g6.ppacg6.enumerations.CourseEnum;
import g6.ppacg6.enumerations.DegreeEnum;
import g6.ppacg6.enumerations.EquipmentEnum;
import g6.ppacg6.enumerations.FieldEnum;
import java.time.LocalDateTime;
import java.time.Month;


public class PPACG6 {

    public static void main(String[] args) {
        Participant participant1 = new Student("S1", "Bio", CourseEnum.LSIRC, 1);
        Participant participant2 = new Student("S2", "Bio", CourseEnum.LSIRC, 1);
        Participant participant3 = new Student("S3", "Bio", CourseEnum.LSIRC, 1);
        Participant presenter1 = new Presenter("P1", "Bio", DegreeEnum.DOUTORAMENTO, 
                FieldEnum.NETWORKING);
        
        Theme cybersecurity = new Theme("Cybersecurity");
        Topic networking = new Topic("Networking");
        
        Equipment laptop = new Equipment(1, EquipmentEnum.COMPUTER);
        Room room1 = new RoomImpl("Room1", 50);
        System.out.println(((RoomImpl)room1).addEquipment(laptop));
        ((RoomImpl)room1).setEquipmentStatus(0, false); // Will throw error
        
        Session session1 = new SessionImpl("Session1", cybersecurity, LocalDateTime.of(2022, Month.MARCH, 1, 1, 1, 10), 
                LocalDateTime.of(2022, Month.MARCH, 1, 1, 1, 30), room1);
        
        Presentation presentation1 = new PresentationImpl("Presentation1", 
                LocalDateTime.of(2022, Month.MARCH, 1, 1, 1, 10), 
                LocalDateTime.of(2022, Month.MARCH, 1, 1, 1, 30), presenter1);
        
        try {
            System.out.println( ((PresentationImpl)presentation1).addRequiredEquipment(laptop) );
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        try {
            System.out.println( "> " + ((SessionImpl)session1).addPresentation(presentation1) );
        } catch (SessionException ex) {
            System.out.println(ex);
        }
        
        Conference conference1 = new ConferenceImpl("Conf1", LocalDateTime.of(2020, 1, 1, 10, 0), FieldEnum.COMPUTER_SCIENCE.toString());
        
        try {
            conference1.checkIn(presenter1);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            conference1.checkIn(presenter1);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            conference1.checkIn(participant1);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            conference1.checkIn(participant2);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        try {
            System.out.println( ((ConferenceImpl)conference1).addSession(session1)  );   
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        for ( Participant p : conference1.getParticipants()) {
            if (p == null) break;
            System.out.println(p.toString());
        }
        
        for ( Session s : conference1.getSessions() ) {
            if (s == null) break;
            System.out.println(s.toString());
        }

        for ( Room r : conference1.getRooms()) {
            if (r == null) break;
            System.out.println(r.toString());
        }
        
        try {
            for ( Session rs : conference1.getRoomSessions(1, 
                    LocalDateTime.of(2022, Month.MARCH, 1, 1, 1, 0), 
                    LocalDateTime.of(2022, Month.MARCH, 1, 1, 10, 0))) {
                if (rs == null) break;
                System.out.println(rs.toString());
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
