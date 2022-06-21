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

/** Class that represents a theme */
public class Theme {

    /** The ID of the theme. */
    private int id = 0;

    /** The CID - counter ID of the theme. */
    private static int CID = 0;

    /** The name of the theme. */
    private String theme;

    /** Array of Topics of the Theme. */
    private Topic[] topics;

    /** The number of topics of the theme. */
    private int nTopics = 0;

    /** The Initial Size of the topics array. */
    private static final int INITIAL_TOPICS = 10;


    /**
     * Constructor of the Theme.
     * @param theme the name of the theme
     * The <b>id</b> is based on the CID variable, <b>nTopics</b> is set as 0 and the <b>topics</b> array is set to the initial size
     */
    public Theme(String theme) {
        this.id = ++CID;
        this.theme = theme;
        this.nTopics = 0;
        this.topics = new Topic[INITIAL_TOPICS];
    }
    

    /**
     * Gets the theme
     * @return String
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Sets the theme, if the theme is valid
     * @param theme String
     * @throws ThemeException when the string is not valid
     */
    public void setTheme(String theme) throws ThemeException {
        try {
            if (StringValidations.isValidString(theme, 250) ) this.theme = theme;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            throw new ThemeException(e.getMessage());
        }
    }

    /**
     * Gets the number of topics of the Theme
     * @return nTopics - int
     */
    public int getnTopics() {
        return nTopics;
    }

    
    /**
     * Finds a given Topic in the array topics[]
     * @param topic - Topic to be found
     * @return int - index of the topic in the array topics[]
     */
    private int findTopic(Topic topic) {
        int pos = -1, x = 0;
        
        while( pos == -1 && x < nTopics) {
            if ( topics[x].equals(topic) ) {
                pos = x;
            }
            x++;
        }
        return pos;
    }
    
    
    /**
     * Increase the capacity of the array papers[], twice the current size
     * This is possible creating a temporary array with the new size, and then
     * copying the old array to the new one, replacing it
     * @throws OutOfMemoryError when the programs runs out of memory 
     */
    private void increaseTopicArr() throws OutOfMemoryError {
        Topic[] tmpTopics = new Topic[nTopics * 2];

        try {
            for ( int x = 0; x < nTopics; x++ ) {
                tmpTopics[x] = this.topics[x];
            }
        } catch (OutOfMemoryError e) {
            throw new OutOfMemoryError();
        }

        this.topics = tmpTopics;
    }
    
    
    /**
     * Adds a Topic to the array topics[]
     * @param topic - Topic to be added
     * @return boolean, true if the topic was added, false otherwise
     * @throws TopicException when the topic is null, or is repeated
     */
    public boolean addTopic(Topic topic) throws TopicException {
        if (topic == null) throw new TopicException("The topic to add cant be null.");
        
        int pos = findTopic(topic);
        
        try {
            if ( nTopics == this.topics.length ) increaseTopicArr();
        } catch (OutOfMemoryError e) {
            throw new TopicException("There is no more memory available to accommodate more topics.");
        }
        
        if (pos != -1) throw new TopicException("The topic is already related to the Theme.");
        
        topics[nTopics++] = topic;
        return true;
    }
    
    /**
     * Adds an array of Topic[] to the array topics[]
     * @param topics - array of Topic to be added
     * @return x - number of topics successfully added
     * @throws TopicException when there was an exception caught in adding
     */
    public int addTopic(Topic[] topics) throws TopicException {
        int x = 0;

        for ( Topic topic : topics ) {
            try {
                if (addTopic(topic)) {
                    x++;
                }
            } catch (TopicException e) {
                throw new TopicException(e.getMessage());
            }
        }
        return x;
    }
    
    /**
     * Removes a Topic from the array topics[]
     * @param topic - Topic to be removed
     * @return boolean
     * @throws NullPointerException
     * If the given Topic is null
     * There are no topic to remove
     * Couldn't find the topic to remove
     */
    public boolean delTopic(Topic topic) throws TopicException {
        if (nTopics == 0) throw new TopicException("There are no Topics to remove.");
        
        if (topic == null) throw new TopicException("The Topic to remove cant be null.");
        
        int pos = findTopic(topic);
        
        if (pos == -1) throw new TopicException("Coudln't find the Topic to remove.");
        
        for ( int x = pos; x < nTopics - 1; x++) {
            topics[x] = topics[x + 1];
        }
        
        topics[--nTopics] = null;
        return true;
    }
    
    /**
     * Removes an array Topic[] from the array topics[]
     * @param topics - array of Topic to be removed
     * @return int - number of topics successfully removed
     * @throws TopicException when there was an exception caught in removing
     */
    public int delTopic(Topic[] topics) throws TopicException {
        int x = 0;
        
        for ( Topic topic : topics ) {
            try {
                if ( this.delTopic(topic) ) {
                    x++;
                }
            } catch (TopicException ex) {
                throw new TopicException(ex.getMessage());
            }
        }
        return x;
    }

    /**
     * Lists all the topics of the array topics[]
     * @return String
     */
    public String listTopics() {
        String str = "";
        if (nTopics == 0) {
            str += "No Topics";
            return str;
        } else {
            for (Topic topic : topics) {
                if (topic == null) break;
                str += topic.toString() + " ";
            }
            return str;
        }
    }
 

    /**
     * Compares two theme's, by ID and theme variable
     * @param obj the theme to compare with
     * False when:
     * Object is null, or not an object of the Theme Class
     * True when:
     * It's the same object, or has the same ID and theme
     * @return boolean 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        
        if (obj == null) return false;
        
        if (getClass() != obj.getClass()) return false;
        
        final Theme other = (Theme) obj;
        
        if (this.id == other.id) return true;
        
        return (this.theme.equals(other.theme));
    }


    /**
     * Lists all the properties of the Theme
     * @return String
     */
    @Override
    public String toString() {
        return "Theme{" + theme + ", id=" + id + ", topics=[" + listTopics() + "], nTopics=" + nTopics + '}';
    }
   
}
