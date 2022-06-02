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

import estg.ipp.pt.tp02_conferencesystem.dashboards.Dashboard;
import estg.ipp.pt.tp02_conferencesystem.enumerations.ConferenceState;
import estg.ipp.pt.tp02_conferencesystem.exceptions.ConferenceException;
import estg.ipp.pt.tp02_conferencesystem.exceptions.SessionException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.*;
import g6.ppacg6.classes.*;
import g6.ppacg6.enumerations.*;
import g6.ppacg6.implementations.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.Month;


public class PPACG6 {

    public static void main(String[] args) {
        ParticipantImpl student1 = new Student("S1", "Bio", ParticipantTypeEnum.SPEAKER, CourseEnum.LSIRC, 1);
        ParticipantImpl student2 = new Student("S2", "Bio", ParticipantTypeEnum.SPEAKER, CourseEnum.LEI, 2);
        ParticipantImpl student3 = new Student("S3", "Bio", ParticipantTypeEnum.SPEAKER, CourseEnum.LSIRC, 3);

        ParticipantImpl speaker1 = new Professor("P1", "Bio", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.COMPUTER_SCIENCE);
        ParticipantImpl speaker2 = new Professor("P2", "Bio", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.COMPUTER_SCIENCE);
        ParticipantImpl speaker3 = new Professor("P3", "Bio", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.COMPUTER_SCIENCE);

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

        Session session1 = new SessionImpl("Session1", cybersecurity, LocalDateTime.of(2020, Month.MARCH, 1, 11, 0, 0),
                LocalDateTime.of(2021, Month.MARCH, 1, 12, 0, 0), room1);
        Session session2 = new SessionImpl("Session2", softwaredelevoplment, LocalDateTime.of(2020, Month.MARCH, 1, 13, 0, 0),
                LocalDateTime.of(2021, Month.MARCH, 1, 13, 30, 0), room2);
        Session session3 = new SessionImpl("Session3", softwaredelevoplment, LocalDateTime.of(2020, Month.MARCH, 1, 13, 35, 0),
                LocalDateTime.of(2021, Month.MARCH, 1, 14, 0, 0), room2);

        Presentation presentation1 = new PresentationImpl("Presentation1",
                LocalDateTime.of(2021, Month.MARCH, 1, 1, 1, 10),
                LocalDateTime.of(2021, Month.MARCH, 1, 1, 1, 30), speaker1);

        Presentation presentation2 = new PresentationImpl("Presentation2",
                LocalDateTime.of(2021, Month.MARCH, 1, 1, 1, 10),
                LocalDateTime.of(2021, Month.MARCH, 1, 1, 1, 30), speaker2);

        Presentation presentation3 = new PresentationImpl("Presentation3",
                LocalDateTime.of(2021, Month.MARCH, 1, 1, 1, 10),
                LocalDateTime.of(2021, Month.MARCH, 1, 1, 1, 30), student1);

        Conference conference1 = new ConferenceImpl("Conf1", LocalDateTime.of(2021, 1, 1, 10, 0), FieldEnum.COMPUTER_SCIENCE.toString());
        Conference conference2 = new ConferenceImpl("Conf2", LocalDateTime.of(2021, 1, 1, 10, 0), FieldEnum.NETWORKING.toString());
        Conference conference3 = new ConferenceImpl("Conf3", LocalDateTime.of(2021, 1, 1, 10, 0), FieldEnum.CHEMISTRY.toString());

        System.out.println("------------- Add Equipments ----------------");
        try {
            System.out.println(((RoomImpl) room1).addEquipment(laptop));
            System.out.println(((RoomImpl) room1).addEquipment(projector));
        } catch ( Exception e) {
            System.out.println(e.getMessage());
        }

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
            System.out.println( ((Professor)speaker1).addPaper(paper1) );
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


        System.out.println("------------- Add Participants ----------------");
        ((ConferenceImpl) conference1).changeStateManual(ConferenceState.IN_PROGRESS);
        try {
            conference1.checkIn(student1);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            conference1.checkIn(student1);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            conference1.checkIn(student2);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            conference1.checkIn(speaker1);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            conference1.checkIn(speaker2);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        try {
            ((SessionImpl) session1).delParticipant(student1);
        } catch (SessionException e) {
            throw new RuntimeException(e);
        }
        try {
            ((SessionImpl) session1).delParticipant(student2);
        } catch (SessionException e) {
            throw new RuntimeException(e);
        }
        try {
            ((SessionImpl) session2).delParticipant(student1);
        } catch (SessionException e) {
            throw new RuntimeException(e);
        }

        System.out.println("------------- Get coisas ----------------");
        for ( Session s : conference1.getSessions() ) {
            if (s == null) break;
            System.out.println(s.toString());
        }

        /*System.out.println("\n------------- Get Rooms ----------------");
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
        /*
        System.out.println("------------- Get Number of Sessions by Room ----------------");
        for (Statistics s : conference1.getNumberOfSessionsByRoom()) {
            System.out.println(s.getDescription() + " " + s.getValue());
        }
        */
        /*
        System.out.println("------------- Get Number of Participants by Session ----------------");
        for (Statistics s : conference1.getNumberOfParticipantsBySession()) {
            System.out.println(s.getDescription() + " " + s.getValue());
        }

        System.out.println("------------- Get Schedule ----------------");
        System.out.println(conference1.getSchedule());

        ConferenceManagementImpl cm = new ConferenceManagementImpl();

        try {
            System.out.println(cm.addConference((ConferenceImpl) conference1));
        } catch ( Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("------------- Get Speaker Participants ----------------");
        try {
            for (Participant p : conference1.getSpeakerParticipants()) {
                System.out.println(p.toString());
            }
        } catch ( Exception e) {
            System.out.println(e.getMessage());
        }
        */
        System.out.println("------------- Export File ----------------");
        conference1.changeState();
        try {
            conference1.generateSpeakerCertificates("test.txt");
            //System.out.println(((ConferenceImpl) conference1).export());
        } catch ( Exception e) {
            System.out.println(e.getMessage());
        }


        /*
        System.out.println("------------- Test ----------------");
        //System.out.println(JsonGenerator.generateOutlabeledPie(new String[]{"asd", "dsa"}, new String[]{"1", "2"}));
        try {
            Dashboard.render(new String[]{JsonGenerator.generateNumberofSessionsbyRoom(conference1.getNumberOfSessionsByRoom()), JsonGenerator.generateNumberofParticipantsbySession(conference1.getNumberOfParticipantsBySession())});
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //

        conference1.changeState();


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
        */
    }
}
