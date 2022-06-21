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

import estg.ipp.pt.tp02_conferencesystem.enumerations.ConferenceState;
import estg.ipp.pt.tp02_conferencesystem.exceptions.ConferenceException;
import estg.ipp.pt.tp02_conferencesystem.exceptions.SessionException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Presentation;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Room;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Session;
import classes.*;
import enumerations.*;
import exceptions.EquipmentException;
import exceptions.PresentationException;
import implementations.ConferenceImpl;
import implementations.IOStatisticsImpl;
import implementations.PresentationImpl;
import implementations.RoomImpl;
import implementations.SessionImpl;

import java.io.IOException;
import java.time.LocalDateTime;


public class ConferenceDemo {
    public static void main(String[] args) {
        Participant teacher1 = new Professor("João Ramos", "Professor PP", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.COMPUTER_SCIENCE);
        Participant teacher2 = new Professor("Ricardo Santos", "Professor PP", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.PROGRAMMING);
        Participant teacher3 = new Professor("Pedro Magalhães", "Professor ESTG", ParticipantTypeEnum.SPEAKER, DegreeEnum.MESTRADO, FieldEnum.SECURITY);
        Participant teacher4 = new Professor("Susana Sousa", "Professor ESTG", ParticipantTypeEnum.SPEAKER, DegreeEnum.LICENCIATURA, FieldEnum.SECURITY);
        Participant teacher5 = new Professor("Pedro Vieira", "Professor ESTG", ParticipantTypeEnum.VISITOR, DegreeEnum.CTESP, FieldEnum.SECURITY);
        Participant teacher6 = new Professor("Marco Paulo", "Professor ESTG", ParticipantTypeEnum.SPEAKER, DegreeEnum.POS_DOUTORAMENTO, FieldEnum.SECURITY);

        Participant student1 = new Student("Rui Vitorino", "Estudante de Segurança Informática", ParticipantTypeEnum.VISITOR, CourseEnum.LSIRC, 2);
        Participant student2 = new Student("João Oliveira", "Estudante de Segurança Informática", ParticipantTypeEnum.VISITOR, CourseEnum.LSIRC, 1);
        Participant student3 = new Student("Miguel Santos", "Estudante de Engenharia Informática", ParticipantTypeEnum.SPEAKER, CourseEnum.LEI, 3);
        Participant student4 = new Student("João Silva", "Estudante de Informação para a Gestão", ParticipantTypeEnum.VISITOR, CourseEnum.LSIG, 1);
        Participant student5 = new Student("José Castelo Branco", "Estudante de Engenharia Informática", ParticipantTypeEnum.VISITOR, CourseEnum.LEI, 2);
        Participant student6 = new Student("Verónica Alves", "Estudante de Engenharia Informática", ParticipantTypeEnum.SPEAKER, CourseEnum.LEI, 3);

        Equipment equipment1 = new Equipment(1, EquipmentEnum.PROJECTOR);
        Equipment equipment2 = new Equipment(2, EquipmentEnum.COMPUTER);
        Equipment equipment3 = new Equipment(3, EquipmentEnum.LASER_POINTER);
        Equipment equipment4 = new Equipment(4, EquipmentEnum.MOBILE_PHONE);
        Equipment equipment5 = new Equipment(5, EquipmentEnum.COMPUTER);
        Equipment equipment6 = new Equipment(6, EquipmentEnum.PROJECTOR);

        Room room1 = new RoomImpl("P7", 36);
        Room room2 = new RoomImpl("P12", 42);
        Room room3 = new RoomImpl("P10", 49);
        Room room4 = new RoomImpl("P9", 36);
        Room room5 = new RoomImpl("P8", 42);
        Room room6 = new RoomImpl("P11", 49);


        System.out.println("*** add equipment to room ***");
        try {
            ((RoomImpl)room1).addEquipment(equipment1);
            ((RoomImpl)room2).addEquipment(equipment2);
            ((RoomImpl)room3).addEquipment(equipment3);
            ((RoomImpl)room3).addEquipment(equipment4);
            ((RoomImpl)room4).addEquipment(equipment5);
            ((RoomImpl)room4).addEquipment(equipment6);

            ((RoomImpl)room1).addEquipment(equipment1);
        } catch (EquipmentException ex) {
            System.out.println(ex.getMessage());
        }

        Theme theme1 = new Theme("Introduction to CyberSecurity");
        Theme theme2 = new Theme("Blue team and Responsabilities");

        Theme theme3 = new Theme("Programming Good Practices");
        Theme theme4 = new Theme("Assembly Language");

        Theme theme5 = new Theme("Artificial Intelligence");
        Theme theme6 = new Theme("Artificial Intelligence Applied to CyberSecurity");


        Topic topic1 = new Topic("Defending Networks");
        Topic topic2 = new Topic("Atacking Devices");

        Topic topic3 = new Topic("OOP");
        Topic topic4 = new Topic("Abstraction");

        Topic topic5 = new Topic("Artificial Intelligence Applied to CyberSecurity");
        Topic topic6 = new Topic("Artificial Intelligence Applied to CyberSecurity");


        Paper paper1 = new Paper("Paper 1", theme1, topic1);
        Paper paper2 = new Paper("Paper 2", theme2, topic2);
        Paper paper3 = new Paper("Paper 3", theme3, topic3);
        Paper paper4 = new Paper("Paper 4", theme4, topic4);
        Paper paper5 = new Paper("Paper 5", theme5, topic5);
        Paper paper6 = new Paper("Paper 6", theme6, topic6);

        Presentation presentation1 = new PresentationImpl("Presentation 1", LocalDateTime.of(2020, 3, 1, 10, 0), LocalDateTime.of(2020, 1, 1, 11, 0), teacher1);
        Presentation presentation2 = new PresentationImpl("Presentation 2", LocalDateTime.of(2020, 3, 1, 11, 0), LocalDateTime.of(2020, 1, 1, 12, 0), teacher2);
        Presentation presentation3 = new PresentationImpl("Presentation 3", LocalDateTime.of(2020, 3, 1, 12, 0), LocalDateTime.of(2020, 1, 1, 13, 0), teacher3);

        Session session1 = new SessionImpl("CyberSecurity Today", theme1, LocalDateTime.of(2020, 3, 1, 8, 0), LocalDateTime.of(2022, 3, 1, 12, 0), room1);
        Session session2 = new SessionImpl("Advanced Programming", theme2, LocalDateTime.of(2020, 3, 1, 9, 0), LocalDateTime.of(2022, 3, 1, 10, 0), room2);
        Session session3 = new SessionImpl("Artificial Intelligence Applied to Business Sciences", theme3, LocalDateTime.of(2022, 3, 1, 10, 0), LocalDateTime.of(2022, 3, 1, 11, 0), room3);

        System.out.println("*** add presentation to sessions ***");
        try {
            ((SessionImpl)session1).addPresentation(presentation1);
            ((SessionImpl)session2).addPresentation(presentation2);
            ((SessionImpl)session3).addPresentation(presentation3);

            ((SessionImpl)session1).addPresentation(presentation3);
        } catch (SessionException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("*** change start time ***");
        try {
            ((PresentationImpl)presentation3).setStartTime(LocalDateTime.of(2020, 3, 1, 10, 0));
        } catch (PresentationException ex) {
            System.out.println(ex.getMessage());        
        }
        
        System.out.println("*** change end time ***");
        try {
            ((PresentationImpl)presentation3).setEndTime(LocalDateTime.of(2020, 3, 1, 11, 0));
            System.out.println("done");
        } catch (PresentationException ex) {
            System.out.println(ex.getMessage());        
        }
        
        System.out.println("*** try to add presentation again with diferent start time ***");
        try {
            ((SessionImpl)session1).addPresentation(presentation3);
            System.out.println("done");
        } catch (SessionException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("*** add participant to session ***");
        try {
            ((SessionImpl) session1).addParticipant(student1);
            ((SessionImpl) session1).addParticipant(student2);
            ((SessionImpl) session1).addParticipant(student3);

            ((SessionImpl) session2).addParticipant(student1);
            ((SessionImpl) session2).addParticipant(student2);

            ((SessionImpl) session3).addParticipant(student1);

            
            ((SessionImpl) session1).addParticipant(student1);
        } catch (SessionException ex) {
            System.out.println(ex.getMessage());
        }

        ConferenceImpl conference1 = new ConferenceImpl("Conferências na ESTG XVI", LocalDateTime.of(2020, 3, 1, 0, 0, 0), "Programming and CyberSecurity");

        System.out.println("*** add session to conference ***");
        try {
            conference1.addSession(session1);
            conference1.addSession(session2);
            conference1.addSession(session3);
            
            conference1.addSession(session1);
        } catch (ConferenceException ex) {
            System.out.println(ex.getMessage());
        }
        
        ((ConferenceImpl)conference1).changeStateManual(ConferenceState.IN_PROGRESS);

        System.out.println("*** check in participant to conference ***");
        try {
            conference1.checkIn(student1);
            conference1.checkIn(student2);
            conference1.checkIn(student3);
            
            conference1.checkIn(student1);
            System.out.println(conference1.getnParticipants());
        } catch (ConferenceException ex) {
            System.out.println(ex.getMessage());
        }


        ((ConferenceImpl)conference1).changeStateManual(ConferenceState.FINISHED);

        try {
            ((ConferenceImpl) conference1).export();
        } catch (IOException e) {
            System.out.println(e);
        }
        
        IOStatisticsImpl.main(new String[]{"numberOfParticipantsBySession.json", "numberOfSessionsByRoom.json"});
    }
}
