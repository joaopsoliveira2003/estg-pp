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

import exceptions.*;

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
     * The <b>id</b> is based on the CID variable
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
     * @throws TopicException if topic is null
     */
    public void setTopic(String topic) throws TopicException {
        if ( topic == null ) throw new TopicException("Topic cannot be null");
        this.topic = topic;
    }


    /**
     * Compares two topics's, by ID and topic variable
     * @param obj the topic to compare with
     * False when:
     * Object is null, or not an object of the Professor Class
     * True when:
     * It's the same object, or has the same ID and topic
     * @return boolean 
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
