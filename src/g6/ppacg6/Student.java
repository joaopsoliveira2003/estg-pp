/*
 * Nome: Rui Alexandre Borba Vitorino
 * Número: 8190479
 * Turma: LSIRC12T1
 *
 * Nome: João Pedro Silva Oliveira
 * Número: 8210291
 * Turma: LSIRC11T2
 */
package g6.ppacg6;

import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import g6.ppacg6.enumerations.CourseEnum;
import java.util.Objects;

public class Student implements Participant {   
    
    private int id = 0;
    private static int CID = 0;
    
    private String name;
    private String bio;
    
    private CourseEnum course;
    private int courseYear;

    public Student(String name, String bio, CourseEnum course, int courseYear) {
        this.id = ++CID;
        this.name = name;
        this.bio = bio;
        this.course = course;
        this.courseYear = courseYear;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getBio() {
        return this.bio;
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
     * @param course 
     */
    public void setCourse(CourseEnum course) {
        this.course = course;
    }

    /**
     * Set the courseYear of the Student
     * @param courseYear 
     */
    public void setCourseYear(int courseYear) {
        this.courseYear = courseYear;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) return true;
        
        if ( obj == null ) return false;
        
        if ( getClass() != obj.getClass() ) return false;
        
        final Student other = (Student) obj;
        
        if ( this.id == other.id ) return true;

        return Objects.equals(this.name, other.name);
    }

    /**
     * List all the properties of the Student
     * @return String
     */
    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", name=" + name + ", bio=" + bio + ", course=" + course + ", courseYear=" + courseYear + '}';
    }

    
    

}