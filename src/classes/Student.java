/*
 * Nome: Rui Alexandre Borba Vitorino
 * Número: 8190479
 * Turma: LSIRC12T1
 *
 * Nome: João Pedro Silva Oliveira
 * Número: 8210291
 * Turma: LSIRC11T2
 */
package classes;

import estg.ipp.pt.tp02_conferencesystem.exceptions.ParticipantException;
import enumerations.*;
import implementations.*;


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
     * Compares two student's, by all it's related properties
     * @param obj the student to compare with
     * False when:
     * Object is null, or not an object of the Paper Class
     * True when:
     * The ID is the same (checked on super equals method, ParticipantImpl)
     * It's the same object, or has the same properties
     * @return boolean 
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