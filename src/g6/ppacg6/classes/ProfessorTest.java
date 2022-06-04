package g6.ppacg6.classes;

import g6.ppacg6.enumerations.DegreeEnum;
import g6.ppacg6.enumerations.FieldEnum;
import g6.ppacg6.enumerations.ParticipantTypeEnum;
import g6.ppacg6.implementations.ParticipantImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorTest extends ParticipantImpl {

    public ProfessorTest(String name, String bio, ParticipantTypeEnum participantType) {
        super(name, bio, participantType);
    }

    @Test
    void testGetId() {
        Professor professor = new Professor("José", "Bio", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.PROGRAMMING);
        assertEquals(1, professor.getId());
    }

    @Test
    void testGetName() {
        Professor professor = new Professor("José", "Bio", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.PROGRAMMING);
        assertEquals("José", professor.getName());
    }

    @Test
    void testGetBio() {
        Professor professor = new Professor("José", "Bio", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.PROGRAMMING);
        assertEquals("Bio", professor.getBio());
    }

    @Test
    void testGetParticipantType() {
        Professor professor = new Professor("José", "Bio", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.PROGRAMMING);
        assertEquals(ParticipantTypeEnum.SPEAKER, professor.getParticipantType());
    }

    @Test
    void testSetName() {
        Professor professor = new Professor("José", "Bio", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.PROGRAMMING);
        try {
            professor.setName("João");
            assertEquals("João", professor.getName());
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
        try {
            professor.setName(null);
            fail("Exception should be thrown");
        } catch (Exception e) {
            assertEquals("String can't be null.", e.getMessage());
        }
        try {
            professor.setName("");
            fail("Exception should be thrown");
        } catch (Exception e) {
            assertEquals("String can't be empty or blank.", e.getMessage());
        }
    }

    @Test
    void testSetBio() {
        Professor professor = new Professor("José", "Bio", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.PROGRAMMING);
        try {
            professor.setBio("");
            fail("Exception should be thrown");
        } catch (Exception e) {
            assertEquals("String can't be empty or blank.", e.getMessage());
        }
        try {
            professor.setBio(null);
            fail("Exception should be thrown");
        } catch (Exception e) {
            assertEquals("String can't be null.", e.getMessage());
        }
        try {
            professor.setBio("Bio");
            assertEquals("Bio", professor.getBio());
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    void testSetParticipantType() {
        Professor professor = new Professor("José", "Bio", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.PROGRAMMING);
        try {
            professor.setParticipantType(null);
            fail("Exception should be thrown");
        } catch (Exception e) {
            assertEquals("ParticipantType can't be null.", e.getMessage());
        }
        try {
            professor.setParticipantType(ParticipantTypeEnum.SPEAKER);
            assertEquals(ParticipantTypeEnum.SPEAKER, professor.getParticipantType());
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    void testEquals() {
        Professor professor1 = new Professor("José", "Bio", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.PROGRAMMING);
        assertEquals(professor1, professor1);
        Professor professor2 = new Professor("Joao", "Bio", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.PROGRAMMING);
        assertNotEquals(professor1, professor2);
    }

    @Test
    void testToString() {
        Professor professor = new Professor("José", "Bio", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.PROGRAMMING);
        assertEquals("Professor{id=1, name='José', bio='Bio', participantType=SPEAKER, degree=DOUTORAMENTO, field=PROGRAMMING}", professor.toString());
    }

    @Test
    void getnPapers() {
        Professor professor = new Professor("José", "Bio", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.PROGRAMMING);
        assertEquals(0, professor.getnPapers());
    }

    @Test
    void getPaper() {
        Professor professor = new Professor("José", "Bio", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.PROGRAMMING);
        try {
            professor.getPaper(0);
            fail("Exception should be thrown");
        } catch (Exception e) {
            assertEquals("Index out of bounds.", e.getMessage());
        }
    }

    @Test
    void getDegree() {
        Professor professor = new Professor("José", "Bio", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.PROGRAMMING);
        assertEquals(DegreeEnum.DOUTORAMENTO, professor.getDegree());
    }

    @Test
    void setDegree() {
        Professor professor = new Professor("José", "Bio", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.PROGRAMMING);
        try {
            professor.setDegree(null);
            fail("Exception should be thrown");
        } catch (Exception e) {
            assertEquals("Degree can't be null.", e.getMessage());
        }
        try {
            professor.setDegree(DegreeEnum.DOUTORAMENTO);
            assertEquals(DegreeEnum.DOUTORAMENTO, professor.getDegree());
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    void getExpertIn() {
        Professor professor = new Professor("José", "Bio", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.PROGRAMMING);
        assertEquals(FieldEnum.PROGRAMMING, professor.getExpertIn());
    }

    @Test
    void setExpertIn() {
        Professor professor = new Professor("José", "Bio", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.PROGRAMMING);
        try {
            professor.setExpertIn(null);
            fail("Exception should be thrown");
        } catch (Exception e) {
            assertEquals("Field can't be null.", e.getMessage());
        }
        try {
            professor.setExpertIn(FieldEnum.PROGRAMMING);
            assertEquals(FieldEnum.PROGRAMMING, professor.getExpertIn());
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    void listPapers() {
        Professor professor = new Professor("José", "Bio", ParticipantTypeEnum.SPEAKER, DegreeEnum.DOUTORAMENTO, FieldEnum.PROGRAMMING);
        assertEquals("No Papers", professor.listPapers());
    }

    @Test
    void addPaper() {

    }

    @Test
    void testAddPaper() {

    }

    @Test
    void delPaper() {

    }

    @Test
    void testDelPaper() {
    }

    @Test
    void testEquals1() {
    }

    @Test
    void testToString1() {
    }
}