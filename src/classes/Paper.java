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

import auxiliary.*;
import exceptions.*;

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

    /** The class's ID Counter */
    private static int CID = 0;

    /**
     * Constructor for the paper
     * @param title title of the paper
     * @param theme theme of the paper
     * @param topic topic of the paper
     * The <b>id</b> is based on the CID variable
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
     * Sets the title of the paper,
     * Uses the Util Class StringValidations to make String related validations
     * @param title String
     * @throws PaperException if the string validations failed
     */
    public void setTitle(String title) throws PaperException {
        try {
            if (StringValidations.isValidString(title, 250)) this.title = title;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            throw new PaperException(e.getMessage());
        }
    }

    /**
     * Get the theme of the paper
     * @return theme
     */
    public Theme getTheme() {
        return theme;
    }

    /**
     * Set the theme of the paper, if not null
     * @param theme Theme
     * @throws PaperException if theme is null
     */
    public void setTheme(Theme theme) throws PaperException {
        try {
            if (! (theme == null) ) this.theme = theme;
        } catch (NullPointerException e) {
            throw new PaperException(e.getMessage());
        }
    }

    /**
     * Gets the topic of the Paper
     * @return topic
     */
    public Topic getTopic() {
        return topic;
    }

    /**
     * Sets the topic of the paper, if not null
     * @param topic Topic
     * @throws PaperException if topic is null
     */
    public void setTopic(Topic topic) throws PaperException {
        try {
            if (! (topic == null) ) this.topic = topic;
        } catch (NullPointerException e) {
            throw new PaperException(e.getMessage());
        }
    }
    
    /**
     * Compares two papers, by ID and title
     * @param obj the paper to compare with
     * False when:
     * Object is null, or not an object of the Paper Class
     * True when:
     * It's the same object, or has the same ID and type of the EquipmentEnum
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
     * List all the properties of the Paper
     * @return String
     */
    @Override
    public String toString() {
        return "Paper{" + "title='" + title + '\'' + ", theme=" + theme + ", topic=" + topic + ", id=" + id + '}';
    }
}

