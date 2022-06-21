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


import classes.Equipment;
import classes.Paper;
import classes.Professor;
import classes.Student;
import classes.Theme;
import classes.Topic;
import enumerations.CourseEnum;
import enumerations.DegreeEnum;
import enumerations.EquipmentEnum;
import enumerations.FieldEnum;
import enumerations.ParticipantTypeEnum;
import estg.ipp.pt.tp02_conferencesystem.exceptions.ParticipantException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Room;
import exceptions.EquipmentException;
import exceptions.ThemeException;
import exceptions.TopicException;
import implementations.RoomImpl;



/**
 *
 * @author RandomPenguin
 */
public class LowerClassDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Participant student1 = new Student("Rui Vitorino", "Estudante", ParticipantTypeEnum.VISITOR,
                CourseEnum.LSIRC, 2);
        Participant student2= new Student("João Oliveira", "Estudante", ParticipantTypeEnum.VISITOR,
                CourseEnum.LSIRC, 1);

        Participant professor1 = new Professor("João Ramos", "Professor PP", ParticipantTypeEnum.SPEAKER,
                DegreeEnum.DOUTORAMENTO, FieldEnum.COMPUTER_SCIENCE);
        
        Participant professor2 = new Professor("Ricardo Santos", "Professor PP", ParticipantTypeEnum.SPEAKER,
                DegreeEnum.DOUTORAMENTO, FieldEnum.PROGRAMMING);
        
        Participant professor3 = new Professor("João Magalhães", "Professor ESTG", ParticipantTypeEnum.VISITOR,
                DegreeEnum.MESTRADO, FieldEnum.SECURITY);
        
        System.out.println(student1.equals(student2)); // False
        
        try { // Will throw error
            ((Student)student1).setCourseYear(-1);
        } catch (ParticipantException ex) {
            System.out.println(ex.getMessage());
        }
        
        try {
            ((Student)student2).setCourseYear(2);
        } catch (ParticipantException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(student1.equals(student2)); // True, same CourseYear and Course
        
        
        
        Room room1 = new RoomImpl("P7", 36);
        Room room2 = new RoomImpl("P12", 42);

        Equipment equipment1 = new Equipment(100, EquipmentEnum.PROJECTOR);
        Equipment equipment2 = new Equipment(200, EquipmentEnum.COMPUTER);

        try { // Will throw an error
            equipment1.setId(-1);
        } catch (EquipmentException ex) {
            System.out.println(ex.getMessage());
        }      
        
        try {
            System.out.println(((RoomImpl)room1).addEquipment(equipment1));
            System.out.println(((RoomImpl)room1).addEquipment(equipment2));
            System.out.println(((RoomImpl)room2).addEquipment(equipment1));
            System.out.println(((RoomImpl)room2).addEquipment(equipment2));
        } catch (Exception e) {
            System.out.println(e);
        }
        
        Equipment[] equipsToDel = {equipment1, equipment2};
        
        try {
            System.out.println(((RoomImpl)room1).delEquipment(equipsToDel));
        } catch (Exception e) {
            System.out.println(e);
        }
        
        System.out.println(room1);
        
        
        
        Theme theme1 = new Theme("CyberSecurity");
        Topic topic1 = new Topic("Defending Networks");
        
        try {
            theme1.setTheme("");
        } catch (ThemeException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            System.out.println(theme1.addTopic(topic1));
        } catch (TopicException ex) {
            System.out.println(ex.getMessage());
        }
        
        System.out.println(theme1);
        
    }
    
}
