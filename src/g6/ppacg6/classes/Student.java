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

import estg.ipp.pt.tp02_conferencesystem.exceptions.ParticipantException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import g6.ppacg6.enumerations.CourseEnum;
import g6.ppacg6.enumerations.ParticipantTypeEnum;
import g6.ppacg6.implementations.ParticipantImpl;

import java.util.Objects;

/** Class responsible for the student */
public class Student extends ParticipantImpl {

    /** The student's course */
    private CourseEnum course;

    /** The student's year */
    private int courseYear;

    /**
     * Constructor of the student
     * @param name name of the student
     * @param bio bio of the student
     * @param participantType type of participant
     * @param course course of the student
     * @param courseYear year of the student
     */
    public Student(String name, String bio, ParticipantTypeEnum participantType, CourseEnum course, int courseYear) {
        super(name, bio, participantType);
        this.course = course;
        this.courseYear = courseYear;
    }

    /**
     * Gets the Course of the Student
     * @return CourseEnum
     */
    public CourseEnum getCourse() {
        return course;
    }

    /**
     * Gets the courseYear of the Student
     * @return int
     */
    public int getCourseYear() {
        return courseYear;
    }

    /**
     * Sets the course of the Student
     * @param course int
     * @throws ParticipantException when the course is null
     */
    public void setCourse(CourseEnum course) throws ParticipantException {
        try {
            if ( course == null ) throw new ParticipantException();
        } catch (ParticipantException e) {
            throw new ParticipantException("Course cannot be null");
        }
        this.course = course;
    }

    /**
     * Sets the courseYear of the Student
     * @param courseYear int
     * @throws ParticipantException when the courseYear is less than 1 or greater or equal than 20
     */
    public void setCourseYear(int courseYear) throws ParticipantException {
        if ( courseYear <= 1 || courseYear >= 20) throw new ParticipantException("Course year must be between 1 and 50");
        this.courseYear = courseYear;
    }


    /**
     * Compares two students, by all fields
     * First compare the two objects using the super class's equals method
     * If that method returns true, then they are the same object, otherwise,
     * we continue with the rest of the comparison of specific fields
     * @param obj - the object to be compared with
     * @return boolean, true if the objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if ( super.equals(obj) ) return true;

        if ( obj.getClass() != this.getClass() ) return false;

        final Student other = (Student) obj;

        return ( this.course.equals(other.getCourse())
                && this.courseYear == other.getCourseYear() );
    }

    /**
     * Lists all the fields of the Student
     * @return String
     */
    @Override
    public String toString() {
        return super.toString() + "Student{" + "course=" + course + ", courseYear=" + courseYear + '}';
    }
}