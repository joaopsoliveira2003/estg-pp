package g6.ppacg6.main;

import estg.ipp.pt.tp02_conferencesystem.dashboards.Dashboard;
import estg.ipp.pt.tp02_conferencesystem.enumerations.ConferenceState;
import estg.ipp.pt.tp02_conferencesystem.exceptions.ConferenceException;
import estg.ipp.pt.tp02_conferencesystem.exceptions.SessionException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.*;
import g6.ppacg6.classes.*;
import g6.ppacg6.enumerations.*;
import g6.ppacg6.exceptions.EquipmentException;
import g6.ppacg6.implementations.ConferenceImpl;
import g6.ppacg6.implementations.PresentationImpl;
import g6.ppacg6.implementations.RoomImpl;
import g6.ppacg6.implementations.SessionImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

public class Demo {

    public static void main(String[] args) {

        Participant student1 = new Student("Rui Vitorino", "Estudante", ParticipantTypeEnum.VISITOR,
                CourseEnum.LSIRC, 2);

        Participant professor1 = new Professor("João Ramos", "Professor PP", ParticipantTypeEnum.SPEAKER,
                DegreeEnum.DOUTORAMENTO, FieldEnum.COMPUTER_SCIENCE);
        Participant professor2 = new Professor("Ricardo Santos", "Professor PP", ParticipantTypeEnum.SPEAKER,
                DegreeEnum.DOUTORAMENTO, FieldEnum.PROGRAMMING);
        Participant professor3 = new Professor("João Magalhães", "Professor ESTG", ParticipantTypeEnum.VISITOR,
                DegreeEnum.MESTRADO, FieldEnum.SECURITY);


        Room room1 = new RoomImpl("P7", 36);
        Room room2 = new RoomImpl("P12", 42);
        Room room3 = new RoomImpl("P10", 49);
        Room room4 = new RoomImpl("P01", 23);

        Equipment equipment1 = new Equipment(100, EquipmentEnum.PROJECTOR);
        Equipment equipment2 = new Equipment(200, EquipmentEnum.COMPUTER);

        Theme theme1 = new Theme("CyberSecurity");
        Theme theme2 = new Theme("Programming");
        Topic topic1 = new Topic("Defending Networks");
        Topic topic2 = new Topic("OOP");

        try {
            ((RoomImpl)room1).addEquipment(equipment1);
            ((RoomImpl)room2).addEquipment(equipment2);
        } catch (Exception e) {
            System.out.println(e);
        }

        Presentation presentation1 = new PresentationImpl("Why JAVA is cool",
                LocalDateTime.of(2022, 7, 7, 10, 00),
                LocalDateTime.of(2022, 7, 7, 10, 30),
                professor2);
        Presentation presentation2 = new PresentationImpl("Why OOP is cool",
                LocalDateTime.of(2022, 7, 7, 10, 20),
                LocalDateTime.of(2022, 7, 7, 10, 40),
                professor1);
        Presentation presentation3 = new PresentationImpl("Why OOP is cool by Students",
                LocalDateTime.of(2022, 7, 7, 10, 10),
                LocalDateTime.of(2022, 7, 7, 10, 40),
                student1);

        // Exception required equipment - DONE

        Session session1 = new SessionImpl("ProgrammingDay", theme2,
                LocalDateTime.of(2022, 7, 7, 10, 0),
                LocalDateTime.of(2022, 7, 7, 11, 0),
                room1);

        try {
            System.out.println(session1.addPresentation(presentation1));
        } catch (SessionException e) {
            System.out.println(e);
        }
        try {
            System.out.println(session1.addPresentation(presentation2));
        } catch (SessionException e) {
            System.out.println(e);
        }
        // Exception duplicated presentation - DONE
        // Exception after, before - DONE
        // Exception time left - DONE

        Conference conference1 = new ConferenceImpl("C22", LocalDateTime.now(), "IT");

        try {
            System.out.println(conference1.addSession(session1));
        } catch (ConferenceException e) {
            System.out.println(e);
        }

        // Overlapping session
        Session session2 = new SessionImpl("ProgrammingDay2", theme2,
                LocalDateTime.of(2022, 7, 7, 10, 0),
                LocalDateTime.of(2022, 7, 7, 12, 0),
                room2);
        try {
            System.out.println(session2.addPresentation(presentation3));
        } catch (SessionException e) {
            System.out.println(e);
        }

        Session session3 = new SessionImpl("ESTG Session", theme2,
                LocalDateTime.of(2022, 7, 7, 14, 0),
                LocalDateTime.of(2022, 7, 7, 18, 0),
                room1);

        Presentation presentation4 = new PresentationImpl("Why choose ESTG",
                LocalDateTime.of(2022, 7, 7, 14, 00),
                LocalDateTime.of(2022, 7, 7, 14, 30),
                professor2);
        Presentation presentation5 = new PresentationImpl("Available Courses",
                LocalDateTime.of(2022, 7, 7, 15, 00),
                LocalDateTime.of(2022, 7, 7, 15, 30),
                professor2);

        try {
            System.out.println(session3.addPresentation(presentation4));
            System.out.println(session3.addPresentation(presentation5));
        } catch (SessionException e) {
            System.out.println(e);
        }

        try {
            System.out.println(conference1.addSession(session3));
        } catch (ConferenceException e) {
            System.out.println(e);
        }

        System.out.println("------------------------------------------------------");
        try {
            System.out.println(conference1.addSession(session2));
        } catch (ConferenceException e) {
            System.out.println(e);
        }


        Participant student2 = new Student("João Oliveira", "Estudante", ParticipantTypeEnum.VISITOR,
                CourseEnum.LSIRC, 1);
        Participant student3 = new Student("John Do", "John", ParticipantTypeEnum.VISITOR,
                CourseEnum.LEI, 5);
        Participant student4 = new Student("John Doe", "John", ParticipantTypeEnum.VISITOR,
                CourseEnum.LEI, 2);
        Participant student5 = new Student("John Doe", "John", ParticipantTypeEnum.VISITOR,
                CourseEnum.LEI, 1);

        System.out.println("------------------------------------------------------");
        ((ConferenceImpl)conference1).changeStateManual(ConferenceState.IN_PROGRESS);
        try {
            conference1.checkIn(professor1);
            conference1.checkIn(professor2);
            conference1.checkIn(student1);
            conference1.checkIn(student2);
            conference1.checkIn(student3);
            conference1.checkIn(student4);
            conference1.checkIn(student5);
        } catch (ConferenceException e) {
            System.out.println(e);
        }

        System.out.println("------------------------------------------------------");
        ((ConferenceImpl)conference1).changeStateManual(ConferenceState.FINISHED);
        // Generate speaker and particpant files - DONE

        try {
            ((ConferenceImpl) conference1).export();
        } catch (IOException e) {
            System.out.println(e);
        }

        String[] jsonFiles = new String[2];
        try {
            FileReader file = new FileReader("numberOfParticipantsBySession.json");
            BufferedReader br = new BufferedReader(file);
            String s;
            while((s = br.readLine()) != null) {
                System.out.println(s);
                jsonFiles[0] = s;
            }
            file.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            FileReader file = new FileReader("numberOfSessionsByRoom.json");
            BufferedReader br = new BufferedReader(file);
            String s;
            while((s = br.readLine()) != null) {
                System.out.println(s);
                jsonFiles[1] = s;
            }
            file.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        Dashboard.render(jsonFiles);
    }
}
