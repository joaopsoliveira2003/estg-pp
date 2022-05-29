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

public class Paper {
    
    private String title;
    private Theme theme;
    private Topic topic;
    
    private int id = 0;
    private static int CID = 0;

    /**
     * Constructor for a Research Paper/("trabalho do Orador")
     * @param title
     * @param theme
     * @param topic 
     */
    public Paper(String title, Theme theme, Topic topic) {
        this.id = ++CID;
        this.title = title;
        this.theme = theme;
        this.topic = topic;
    }

    
    /**
     * Get the ID of the Paper
     * @return int
     */
    public int getId() {
        return id;
    }
    
    /**
     * Get the title of the Paper
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the Paper
     * @param title 
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the Theme of the Paper
     * @return Theme
     */
    public Theme getTheme() {
        return theme;
    }

    /**
     * Set the Theme of the Paper
     * @param theme 
     */
    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    /**
     * Get the Topic of the Paper
     * @return Topic
     */
    public Topic getTopic() {
        return topic;
    }

    /**
     * Set the Topic of the Paper
     * @param topic 
     */
    public void setTopic(Topic topic) {
        this.topic = topic;
    }


    /**
     * Compare two Papers, by ID and Title
     * @param obj - the other Paper to compare
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        
        if (obj == null) return false;
        
        if (getClass() != obj.getClass()) return false;
        
        final Paper other = (Paper) obj;
        if (this.id == other.id) return false;
        
        return ( this.title.equals(other) );
    }

    /**
     * List all the properties of the Paper
     * @return String
     */
    @Override
    public String toString() {
        return "Paper{" + "title=" + title + ", theme=" + theme + ", topic=" + topic + ", id=" + id + '}';
    }

}

