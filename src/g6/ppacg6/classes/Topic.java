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

import g6.ppacg6.exceptions.TopicException;

/** Class that represents a topic */
public class Topic {

    /** The Topic's name. */
    private String topic;

    /** The Topic's ID. */
    private int id = 0;

    /** The class's ID Counter */
    private static int CID = 0;

    /**
     * Constructor for the Topic
     * @param topic - Name of the Topic
     * @apiNote the <b>id</b> is based on the CID variable
     */
    public Topic(String topic) {
        this.id = ++CID;
        this.topic = topic;
    }

    /**
     * Gets the ID of the Topic
     * @return int
     */
    public int getId() {
        return id;
    }
    
    /**
     * Gets the topic
     * @return String
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Sets the topic
     * @param topic - String
     */
    public void setTopic(String topic) throws TopicException {
        if ( topic == null ) throw new TopicException("Topic cannot be null");
        this.topic = topic;
    }


    /**
     * Compares two Topic(s), by ID and topic name
     * @param obj - Topic
     * @return boolean true if equals, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        
        if (obj == null) return false;
        
        if (getClass() != obj.getClass()) return false;
        
        final Topic other = (Topic) obj;
        
        if (this.id == other.id) return true;
        
        return (this.topic.equals(other.topic));
    }


    /**
     * Lists all the properties of the Topic
     * @return String
     */
    @Override
    public String toString() {
        return "Topic{" + topic + ", id=" + id + '}';
    }
}
