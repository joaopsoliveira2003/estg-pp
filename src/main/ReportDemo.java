/*
 * Nome: Rui Alexandre Borba Vitorino
 * Número: 8190479
 * Turma: LSIRC12T1
 *
 * Nome: João Pedro Silva Oliveira
 * Número: 8210291
 * Turma: LSIRC11T2
 */
package main;


import classes.*;
import exceptions.*;
import exceptions.PresentationException;
import enumerations.*;
import implementations.*;
import estg.ipp.pt.tp02_conferencesystem.enumerations.*;
import estg.ipp.pt.tp02_conferencesystem.exceptions.*;
import estg.ipp.pt.tp02_conferencesystem.interfaces.*;
import java.time.LocalDateTime;



public class ReportDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Participant participant1 = new Student("PS1", "Bio",
                ParticipantTypeEnum.VISITOR, CourseEnum.LEI, 1);
        Participant participant2 = new Student("PS2", "Bio",
                ParticipantTypeEnum.VISITOR, CourseEnum.LEI, 2);
        Participant participant3 = new Student("PS3", "Bio",
                ParticipantTypeEnum.VISITOR, CourseEnum.LSIG, 2);
        Participant participant4 = new Student("PS4", "Bio",
                ParticipantTypeEnum.VISITOR, CourseEnum.LSIG, 3);
        Participant participant5 = new Student("PS5", "Bio",
                ParticipantTypeEnum.VISITOR, CourseEnum.LSIRC, 1);
        
        Participant participant6 = new Professor("PP6", "Bio",
                ParticipantTypeEnum.VISITOR, DegreeEnum.LICENCIATURA,
                FieldEnum.NETWORKING);
        Participant participant7 = new Professor("PP7", "Bio",
                ParticipantTypeEnum.VISITOR, DegreeEnum.LICENCIATURA,
                FieldEnum.NETWORKING);
        
        Participant participant8 = new Professor("PP8", "Bio",
                ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO,
                FieldEnum.COMPUTER_SCIENCE);
        Participant participant9 = new Professor("PP9", "Bio",
                ParticipantTypeEnum.SPEAKER, DegreeEnum.POS_DOUTORAMENTO,
                FieldEnum.PROGRAMMING);
        Participant participant10 = new Professor("PP10", "Bio",
                ParticipantTypeEnum.SPEAKER, DegreeEnum.MESTRADO,
                FieldEnum.NETWORKING);
        
        
        Room room1 = new RoomImpl("P7", 30);
        Room room2 = new RoomImpl("AUD1", 120);
        
        Equipment equipment1 = new Equipment(1, EquipmentEnum.COMPUTER);
        Equipment equipment2 = new Equipment(2, EquipmentEnum.PROJECTOR);
        
        System.out.println("\n### --- Adding Equipments");
        try {
            System.out.println( ((RoomImpl)room1).addEquipment(equipment1));
        } catch (EquipmentException ex) {
            System.out.println(ex.getMessage());
        }
        
        Theme theme1 = new Theme("Programming");
        Theme theme2 = new Theme("Cybersecurity");
        Topic topic1 = new Topic("OOP");
        Topic topic2 = new Topic("Exploting with Java");
        
        System.out.println("\n### --- Adding Themes and Topics ");
        try {
            System.out.println(theme1.addTopic(topic1));
            System.out.println(theme1.addTopic(topic2));
            System.out.println(theme2.addTopic(topic2));
        } catch (TopicException ex) {
            System.out.println(ex.getMessage());
        }
        
        Session session1 = new SessionImpl("JAVA", theme1, 
                LocalDateTime.of(2022, 6, 7, 10, 0),
                LocalDateTime.of(2022, 6, 7, 12, 0),
                room1);
        Session session2 = new SessionImpl("Cybersecurity", theme2, 
                LocalDateTime.of(2022, 6, 7, 13, 0),
                LocalDateTime.of(2022, 6, 7, 15, 0),
                room2);
        
        Presentation presentation1 = new PresentationImpl("Why Learn JAVA",
                LocalDateTime.of(2022, 6, 1, 10, 0), // Will cause an error
                LocalDateTime.of(2022, 6, 7, 10, 30),
                participant8);
        
        Presentation presentation2 = new PresentationImpl("JAVA for Security Professionals",
                LocalDateTime.of(2022, 6, 7, 10, 40),
                LocalDateTime.of(2022, 6, 7, 11, 20),
                participant10);
        
        System.out.println("\n### --- Adding Required Equipments to Presentation2");
        try {
            System.out.println(((PresentationImpl)presentation2).addRequiredEquipment(equipment2));
        } catch (PresentationException ex) {
            System.out.println(ex.getMessage());
        }
        
        System.out.println("\n### --- Adding Presentations to Sessions");
        try {
            System.out.println(session1.addPresentation(presentation1));
        } catch (SessionException ex) {
            System.out.println(ex.getMessage());
        }
        
        try { // Will throw an error, missing equipments in room1
            System.out.println(session1.addPresentation(presentation2));
        } catch (SessionException ex) {
            System.out.println(ex.getMessage());
        }
        
        System.out.println("\n### --- Fixing the error");
        try {
            System.out.println( ((RoomImpl)room1).addEquipment(equipment2));
        } catch (EquipmentException ex) {
            System.out.println(ex.getMessage());
        }
        
        System.out.println("\n### --- Adding Presentation2 to Session - no error equipments");
        try {
            System.out.println(session1.addPresentation(presentation2));
        } catch (SessionException ex) {
            System.out.println(ex.getMessage());
        }
        
        
        System.out.println("\n### --- Will get an error ");
        try { // Will throw an error
            System.out.println(session2.addPresentation(presentation2));
        } catch (SessionException ex) {
            System.out.println(ex.getMessage());
        }
        
        Session session2v2 = new SessionImpl("Cybersecurity v2", theme2, 
                LocalDateTime.of(2022, 6, 7, 13, 0),
                LocalDateTime.of(2022, 6, 7, 15, 0),
                room2);
        
        Presentation presentation2v2 = new PresentationImpl("JAVA for Security Professionals",
                LocalDateTime.of(2022, 6, 7, 13, 30),
                LocalDateTime.of(2022, 6, 7, 14, 30),
                participant10);
        
        System.out.println("\n### --- \"error\" fixed ");
        try {
            System.out.println(session2v2.addPresentation(presentation2v2));
        } catch (SessionException ex) {
            System.out.println(ex.getMessage());
        }
      

        try {
            ((PresentationImpl)presentation1).setStartTime(LocalDateTime.of(2022, 6, 7, 10, 0));
        } catch (PresentationException ex) {
            System.out.println(ex.getMessage());
        }
        
        try {
            System.out.println(session1.addPresentation(presentation1));
        } catch (SessionException ex) {
            System.out.println(ex.getMessage());
        }
        
        Conference conferenceESTG = new ConferenceImpl("Conference ESTG 22",
                LocalDateTime.of(2022, 6, 1, 9, 0), "ESTG");
                
        System.out.println("\n### --- Adding Sessions to Conference ");
        try {
            System.out.println(conferenceESTG.addSession(session1));
        } catch (ConferenceException ex) {
            System.out.println(ex.getMessage());
        }
        
        try {
            System.out.println(conferenceESTG.addSession(session2v2));
        } catch (ConferenceException ex) {
            System.out.println(ex.getMessage());
        }
        
        System.out.println("\n### --- Changed State ");
        conferenceESTG.changeState();
        try {
            for ( Session s : conferenceESTG.getRoomSessions(1, 
                    LocalDateTime.of(2022, 6, 7, 9, 0),
                    LocalDateTime.of(2022, 6, 7, 13, 0)) ) {
                if ( s == null ) break;
                System.out.println(s);
            }
        } catch (ConferenceException ex) {
            System.out.println(ex.getMessage());
        }
        
        
        Participant[] participants = {participant1, participant2, participant3,
            participant4, participant5, participant6, participant7, participant8,
            participant9, participant10};
        
        System.out.println("\n### --- Checking In ");
        for ( int x = 0; x < participants.length; x++ ) {
            try {
                conferenceESTG.checkIn(participants[x]);
            } catch (ConferenceException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        System.out.println("\n### --- Get Participant, will get an error ");
        try {
            System.out.println(conferenceESTG.getParticipant(-42));
        } catch (ConferenceException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            System.out.println(conferenceESTG.getParticipant(5));
        } catch (ConferenceException ex) {
            System.out.println(ex.getMessage());
        }
        
        System.out.println("\n### --- Get Schedule ");
        System.out.println(conferenceESTG.getSchedule());
        
        
        System.out.println("\n### --- Listing Participants ");
        for ( Participant p : conferenceESTG.getParticipants() ) {
            if ( p == null ) break;
            System.out.println(p);
        }
        
        
        System.out.println("\n### --- Generating Certificates ");
        ((ConferenceImpl)conferenceESTG).changeStateManual(ConferenceState.FINISHED);
        try {
            conferenceESTG.generateParticipantCertificates("ParticipantCertificatesDemo");
        } catch (ConferenceException ex) {
            System.out.println(ex.getMessage());
        } 
        
        try {
            conferenceESTG.generateSpeakerCertificates("SpeakerCertificatesDemo");
        } catch (ConferenceException ex) {
            System.out.println(ex.getMessage());
        } 
    }
    
}
