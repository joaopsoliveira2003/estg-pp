/*
 * Nome: Rui Alexandre Borba Vitorino
 * Número: 8190479
 * Turma: LSIRC12T1
 *
 * Nome: João Pedro Silva Oliveira
 * Número: 8210291
 * Turma: LSIRC11T2
 */

package g6.ppacg6.main;

import estg.ipp.pt.tp02_conferencesystem.exceptions.SessionException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Conference;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Presentation;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Room;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Session;
import estg.ipp.pt.tp02_conferencesystem.io.interfaces.Statistics;
import g6.ppacg6.classes.*;
import g6.ppacg6.enumerations.CourseEnum;
import g6.ppacg6.enumerations.DegreeEnum;
import g6.ppacg6.enumerations.EquipmentEnum;
import g6.ppacg6.enumerations.FieldEnum;
import g6.ppacg6.exceptions.EquipmentException;
import g6.ppacg6.implementations.ConferenceImpl;
import g6.ppacg6.implementations.PresentationImpl;
import g6.ppacg6.implementations.RoomImpl;
import g6.ppacg6.implementations.SessionImpl;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;


public class PPACG6 {

    public static void main(String[] args) throws EquipmentException {
        Participant participant1 = new Student("S1", "Bio", CourseEnum.LSIRC, 1);
        Participant participant2 = new Student("S2", "Bio", CourseEnum.LSIG, 2);
        Participant participant3 = new Student("S3", "Bio", CourseEnum.LEI, 1);

        Participant presenter1 = new Presenter("P1", "Bio", DegreeEnum.DOUTORAMENTO, FieldEnum.NETWORKING);
        Participant presenter2 = new Presenter("P2", "Bio", DegreeEnum.DOUTORAMENTO, FieldEnum.COMPUTER_SCIENCE);
        Participant presenter3 = new Presenter("P3", "Bio", DegreeEnum.MESTRADO, FieldEnum.CHEMISTRY);

        Theme cybersecurity = new Theme("Cybersecurity");
        Theme softwaredelevoplment = new Theme("Software Development");
        Theme hardware = new Theme("Hardware");
        Topic networking = new Topic("Networking");
        Topic java = new Topic("Java");
        Topic python = new Topic("Python");

        Paper paper1 = new Paper("P1", cybersecurity, networking);
        Paper paper2 = new Paper("P2", softwaredelevoplment, java);
        Paper paper3 = new Paper("P3", hardware, python);
        Paper paper4 = new Paper("P4", softwaredelevoplment, networking);
        Paper paper5 = new Paper("P5", cybersecurity, networking);

        Equipment laptop = new Equipment(1, EquipmentEnum.COMPUTER);
        Equipment projector = new Equipment(2, EquipmentEnum.PROJECTOR);
        Equipment laser_pointer = new Equipment(3, EquipmentEnum.LASER_POINTER);
        Equipment mobile_phone = new Equipment(4, EquipmentEnum.MOBILE_PHONE);

        Room room1 = new RoomImpl("Room1", 50);
        Room room2 = new RoomImpl("Room2", 30);
        Room room3 = new RoomImpl("Room3", 40);

        Session session1 = new SessionImpl("Session1", cybersecurity, LocalDateTime.of(2022, Month.MARCH, 1, 1, 1, 10),
                LocalDateTime.of(2022, Month.MARCH, 1, 1, 1, 30), room1);
        Session session2 = new SessionImpl("Session2", softwaredelevoplment, LocalDateTime.of(2022, Month.MARCH, 1, 2, 1, 10),
                LocalDateTime.of(2022, Month.MARCH, 1, 2, 1, 30), room1);
        Session session3 = new SessionImpl("Session2", softwaredelevoplment, LocalDateTime.of(2022, Month.MARCH, 1, 2, 1, 10),
                LocalDateTime.of(2022, Month.MARCH, 1, 3, 1, 30), room2);

        Presentation presentation1 = new PresentationImpl("Presentation1",
                LocalDateTime.of(2022, Month.MARCH, 1, 1, 1, 10),
                LocalDateTime.of(2022, Month.MARCH, 1, 1, 1, 30), presenter1);

        Presentation presentation2 = new PresentationImpl("Presentation2",
                LocalDateTime.of(2022, Month.MARCH, 1, 1, 1, 10),
                LocalDateTime.of(2022, Month.MARCH, 1, 1, 1, 30), presenter2);

        Presentation presentation3 = new PresentationImpl("Presentation3",
                LocalDateTime.of(2022, Month.MARCH, 1, 1, 1, 10),
                LocalDateTime.of(2022, Month.MARCH, 1, 1, 1, 30), presenter3);

        Conference conference1 = new ConferenceImpl("Conf1", LocalDateTime.of(2020, 1, 1, 10, 0), FieldEnum.COMPUTER_SCIENCE.toString());
        Conference conference2 = new ConferenceImpl("Conf2", LocalDateTime.of(2020, 1, 1, 10, 0), FieldEnum.NETWORKING.toString());
        Conference conference3 = new ConferenceImpl("Conf3", LocalDateTime.of(2020, 1, 1, 10, 0), FieldEnum.CHEMISTRY.toString());

        System.out.println("------------- Add Equipments ----------------");
        System.out.println(((RoomImpl)room1).addEquipment(laptop));
        System.out.println(((RoomImpl)room1).addEquipment(projector));
        ((RoomImpl)room1).setEquipmentStatus(0, false); // Will throw error

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


        System.out.println("------------- Paper to Presenter ----------------");
        try {
            System.out.println( ((Presenter)participant1).addPaper(paper1) );
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            System.out.println( ((Presenter)presenter1).addPaper(paper2) );
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            System.out.println( ((Presenter)presenter1).addPaper(paper3) );
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            System.out.println( ((Presenter)presenter1).delPaper(paper1) );
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println(presenter1);


        System.out.println("------------- Add Participants ----------------");
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

        System.out.println("------------- Add Sessions ----------------");
        try {
            System.out.println( ((ConferenceImpl)conference1).addSession(session1)  );
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            System.out.println( ((ConferenceImpl)conference1).addSession(session2)  );
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            System.out.println( ((ConferenceImpl)conference1).addSession(session3)  );
        } catch (Exception ex) {
            System.out.println(ex);
        }

        System.out.println("------------- Get coisas ----------------");
        for ( Session s : conference1.getSessions() ) {
            if (s == null) break;
            System.out.println(s.toString());
        }

        System.out.println("\n------------- Get Rooms ----------------");
        for ( Room r : conference1.getRooms()) {
            if (r == null) break;
            System.out.println(r.toString());
        }

        System.out.println("\n------------- Get Room Sessions ----------------");
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

        System.out.println("------------- Remove Session ----------------");
        /*
        try {
            conference1.removeSession(2);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            conference1.removeSession(0);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        for ( Session s : conference1.getSessions() ) {
            if (s == null) break;
            System.out.println(s.toString());
        }
        */

        System.out.println("\n------------- Get Number of Sessions by Room ----------------");
        for (Statistics s : conference1.getNumberOfSessionsByRoom()) {
            System.out.println(s.getDescription() + " " + s.getValue());
        }


    }
}
