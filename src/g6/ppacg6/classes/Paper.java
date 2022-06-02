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

/** Class responsible for the paper of the professor */
public class Paper {

    /** The paper's title */
    private String title;

    /** The paper's theme */
    private Theme theme;

    /** The paper's topic */
    private Topic topic;

    /** The paper's ID */
    private int id = 0;

    /** The class's ID */
    private static int CID = 0;

    /**
     * Constructor for the paper
     * @param title title of the paper
     * @param theme theme of the paper
     * @param topic topic of the paper
     * @apiNote the <b>id</b> is based on the CID variable
     */
    public Paper(String title, Theme theme, Topic topic) {
        this.id = ++CID;
        this.title = title;
        this.theme = theme;
        this.topic = topic;
    }

    /**
     * Gets the ID of the paper
     * @return int
     */
    public int getId() {
        return id;
    }
    
    /**
     * Gets the title of the paper
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the paper
     * @param title String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the theme of the paper
     * @return theme
     */
    public Theme getTheme() {
        return theme;
    }

    /**
     * Set the theme of the paper
     * @param theme Theme
     */
    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    /**
     * Gets the topic of the Paper
     * @return topic
     */
    public Topic getTopic() {
        return topic;
    }

    /**
     * Sets the topic of the paper
     * @param topic Topic
     */
    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    /**
     * Compares two papers, by ID and title
     * @param obj the other paper to compare
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Paper other = (Paper) obj;
        if (this.id == other.id) return true;
        return ( this.title.equals(other.title) );
    }

    /**
     * Lists all the properties of the paper
     * @return String
     */
    @Override
    public String toString() {
        return String.format("Paper={id=%o, title=%s, theme=%s, topic=%s}", id, title, theme, topic);
    }

}

