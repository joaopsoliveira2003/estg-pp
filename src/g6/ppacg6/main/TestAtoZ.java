package g6.ppacg6.main;

import estg.ipp.pt.tp02_conferencesystem.enumerations.ConferenceState;
import estg.ipp.pt.tp02_conferencesystem.exceptions.ParticipantException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.*;
import g6.ppacg6.classes.*;
import g6.ppacg6.enumerations.*;
import g6.ppacg6.implementations.*;

import java.time.LocalDateTime;

public class TestAtoZ {

    public static void main(String[] args) {

        ParticipantImpl professor1 = new Professor("P1", "Bio", ParticipantTypeEnum.SPEAKER,
                DegreeEnum.DOUTORAMENTO, FieldEnum.COMPUTER_SCIENCE);
        ParticipantImpl professor2 = new Professor("P2", "Bio",
                ParticipantTypeEnum.SPEAKER,
                DegreeEnum.LICENCIATURA, FieldEnum.COMPUTER_SCIENCE);
        ParticipantImpl student1 = new Student("S1", "Bio", ParticipantTypeEnum.VISITOR,
                CourseEnum.LSIRC, 1);

        Theme theme1 = new Theme("Cybersecurity");
        Theme theme2 = new Theme("Cybersecuriy");
        Topic topic1 = new Topic("Malware");
        Topic topic2 = new Topic("Ransomware");

        Paper p1 = new Paper("Paper1", theme1, topic1);
        Paper p2 = new Paper("Paper2", theme1, topic1);

        Equipment e1 = new Equipment(1, EquipmentEnum.COMPUTER);
        Equipment e2 = new Equipment(2, EquipmentEnum.LASER_POINTER);
        Equipment e3 = new Equipment(3, EquipmentEnum.MOBILE_PHONE);

        Room r1 = new RoomImpl("R1", 50);
        Room r2 = new RoomImpl("R2", 50);
        Room r3 = new RoomImpl("R3", 50);

        try {
            ((RoomImpl)r1).addEquipment(e1);
            ((RoomImpl)r1).addEquipment(e2);
            ((RoomImpl)r2).addEquipment(e2);
            ((RoomImpl)r3).addEquipment(e3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Presentation presentation1 = new PresentationImpl("Pres1", LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10), professor1);

        Presentation presentation2 = new PresentationImpl("Pres2", LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30), professor1);

        Session session1 = new SessionImpl("Session1", theme1, LocalDateTime.now(),
                LocalDateTime.now().plusHours(1), r1);
        Session session2 = new SessionImpl("Session2", theme1, LocalDateTime.now(),
                LocalDateTime.now().plusHours(1), r1);
        Session session3 = new SessionImpl("Session3", theme1, LocalDateTime.now(),
                LocalDateTime.now().plusHours(1), r1);


        try {
            ((SessionImpl)session2).addPresentation(presentation1);
            ((SessionImpl)session1).addPresentation(presentation2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Conference conference1 = new ConferenceImpl("Conference1", LocalDateTime.now(),
                "Cybersecurity");

        //((ConferenceImpl)conference1).changeStateManual(ConferenceState.FINISHED);

        try {
            System.out.println(((ConferenceImpl)conference1).addSession(session1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(((ConferenceImpl)conference1).addSession(session2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
