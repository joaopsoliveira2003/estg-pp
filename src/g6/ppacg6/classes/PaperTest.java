package g6.ppacg6.classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaperTest {

    @Test
    void getId() {
        Paper paper = new Paper("title", new Theme("theme"), new Topic("topic"));
        assertEquals(1, paper.getId());
    }

    @Test
    void getTitle() {
        Paper paper = new Paper("title", new Theme("theme"), new Topic("topic"));
        assertEquals("title", paper.getTitle());
    }

    @Test
    void setTitle() {
        Paper paper = new Paper("title", new Theme("theme"), new Topic("topic"));
        try {
            paper.setTitle(null);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertEquals("String can't be null.", e.getMessage());
        }
        try {
            paper.setTitle("");
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertEquals("String can't be empty or blank.", e.getMessage());
        }
        try {
            paper.setTitle("title");
            assertEquals("title", paper.getTitle());
        } catch (Exception e) {
            fail("Should not have thrown an exception");
        }
    }

    @Test
    void getTheme() {
        Paper paper = new Paper("title", new Theme("theme"), new Topic("topic"));
        assertEquals(new Theme("theme"), paper.getTheme());
    }

    @Test
    void setTheme() {
        Paper paper = new Paper("title", new Theme("theme"), new Topic("topic"));
        try {
            paper.setTheme(null);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertEquals("Theme can't be null.", e.getMessage());
        }
        try {
            paper.setTheme(new Theme(""));
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertEquals("Theme can't be empty or blank.", e.getMessage());
        }
        try {
            paper.setTheme(new Theme("theme"));
            assertEquals(new Theme("theme"), paper.getTheme());
        } catch (Exception e) {
            fail("Should not have thrown an exception");
        }
    }

    @Test
    void getTopic() {
        Paper paper = new Paper("title", new Theme("theme"), new Topic("topic"));
        assertEquals(new Topic("topic"), paper.getTopic());
    }

    @Test
    void setTopic() {
        Paper paper = new Paper("title", new Theme("theme"), new Topic("topic"));
        try {
            paper.setTopic(null);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertEquals("Topic can't be null.", e.getMessage());
        }
        try {
            paper.setTopic(new Topic(""));
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertEquals("Topic can't be empty or blank.", e.getMessage());
        }
        try {
            paper.setTopic(new Topic("topic"));
            assertEquals(new Topic("topic"), paper.getTopic());
        } catch (Exception e) {
            fail("Should not have thrown an exception");
        }
    }

    @Test
    void testEquals() {
        Paper paper1 = new Paper("title", new Theme("theme"), new Topic("topic"));
        assertEquals(paper1, paper1);
        Paper paper2 = new Paper("title", new Theme("theme"), new Topic("topic"));
        assertEquals(paper1, paper2);
    }

    @Test
    void testToString() {
        Paper paper = new Paper("title", new Theme("theme"), new Topic("topic"));
        assertEquals("Paper{title='title', theme=Theme{theme, id=1, topics=[No Topics], nTopics=0}, topic=Topic{topic, id=1}, id=1}", paper.toString());
    }
}