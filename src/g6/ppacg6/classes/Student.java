/*
 * Nome: Rui Alexandre Borba Vitorino
 * Número: 8190479
 * Turma: LSIRC12T1
 *
 * Nome: João Pedro Silva Oliveira
 * Número: 8210291
 * Turma: LSIRC11T2
 */
package g6.ppacg6.classes;

import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import g6.ppacg6.enumerations.CourseEnum;
import g6.ppacg6.enumerations.ParticipantTypeEnum;
import g6.ppacg6.implementations.ParticipantImpl;

import java.util.Objects;

public class Student extends ParticipantImpl {
    
    private CourseEnum course;
    private int courseYear;

    public Student(String name, String bio, ParticipantTypeEnum participantType, CourseEnum course, int courseYear) {
        super(name, bio, participantType);
        this.course = course;
        this.courseYear = courseYear;
    }


    /**
     * Get the Course of the Student
     * @return CourseEnum
     */
    public CourseEnum getCourse() {
        return course;
    }

    /**
     * Get the courseYear of the Student
     * @return int
     */
    public int getCourseYear() {
        return courseYear;
    }

    /**
     * Set the course of the Student
     * @param course - CourseEnum
     */
    public void setCourse(CourseEnum course) {
        this.course = course;
    }

    /**
     * Set the courseYear of the Student
     * @param courseYear - int
     */
    public void setCourseYear(int courseYear) {
        this.courseYear = courseYear;
    }


    //equals

    //toString

}