package g6.ppacg6.main;

import estg.ipp.pt.tp02_conferencesystem.dashboards.Dashboard;
import estg.ipp.pt.tp02_conferencesystem.enumerations.ConferenceState;
import estg.ipp.pt.tp02_conferencesystem.exceptions.ParticipantException;
import estg.ipp.pt.tp02_conferencesystem.exceptions.SessionException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.*;
import g6.ppacg6.classes.*;
import g6.ppacg6.enumerations.*;
import g6.ppacg6.implementations.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;

public class TestAtoZ {

    public static void main(String[] args) {

        ParticipantImpl professor1 = new Professor("P1", "Bio", ParticipantTypeEnum.SPEAKER,
                DegreeEnum.DOUTORAMENTO, FieldEnum.COMPUTER_SCIENCE);
        ParticipantImpl professor2 = new Professor("P2", "Bio",
                ParticipantTypeEnum.SPEAKER,
                DegreeEnum.LICENCIATURA, FieldEnum.COMPUTER_SCIENCE);
        ParticipantImpl student1 = new Student("S1", "Bio", ParticipantTypeEnum.SPEAKER,
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

        Presentation presentation1 = new PresentationImpl("Pres1",
                LocalDateTime.of(2022, 10, 10, 12, 00),
                LocalDateTime.of(2022, 10, 10, 12, 15), professor1);

        Presentation presentation2 = new PresentationImpl("Pres2",
                LocalDateTime.of(2022, 10, 10, 12, 20),
                LocalDateTime.of(2022, 10, 10, 12, 50), student1);

        Presentation presentation3 = new PresentationImpl("Pres3",
                LocalDateTime.of(2022, 10, 10, 13, 00),
                LocalDateTime.of(2022, 10, 10, 13, 30), professor2);

        Presentation presentation4 = new PresentationImpl("Pres4",
                LocalDateTime.of(2022, 10, 10, 15, 10),
                LocalDateTime.of(2022, 10, 10, 15, 30), professor2);


        Session session2 = new SessionImpl("Session1", theme1,
                LocalDateTime.of(2022, 10, 10, 11, 00),
                LocalDateTime.of(2022, 10, 10, 14, 00), r1);

        Session session1 = new SessionImpl("Session2", theme1,
                LocalDateTime.of(2022, 10, 10, 15, 00),
                LocalDateTime.of(2022, 10, 10, 16, 00), r2);



        try {
            System.out.println(session1.addPresentation(presentation1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(session1.addPresentation(presentation2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(session1.addPresentation(presentation3));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(session2.addPresentation(presentation4));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Conference conference1 = new ConferenceImpl("Conference1", LocalDateTime.now(),
                "Cybersecurity");


        System.out.println("1  -----------------------");
        try {
            System.out.println(((ConferenceImpl)conference1).addSession(session1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("2  -----------------------");
        try {
            System.out.println(((ConferenceImpl)conference1).addSession(session2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ((ConferenceImpl)conference1).changeStateManual(ConferenceState.IN_PROGRESS);
        try {
            conference1.checkIn(professor1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            conference1.checkIn(professor2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            conference1.checkIn(student1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("-------------------------");
        System.out.println(conference1.getSchedule());
    }
}
