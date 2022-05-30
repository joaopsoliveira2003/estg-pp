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

public class Topic {
    
    private String topic;
    private int id = 0;
    private static int CID = 0;

    /**
     * Constructor for the Topic
     * @param topic - Name of the Topic
     */
    public Topic(String topic) {
        this.id = ++CID;
        this.topic = topic;
    }

    
    /**
     * Get the ID of the Topic
     * @return int
     */
    public int getId() {
        return id;
    }
    
    /**
     * Get the topic
     * @return String
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Set the topic
     * @param topic - String
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }


    /**
     * Compare two Topic(s), by ID and topic
     * @param obj - Topic
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
     * List all the properties of the Topic
     * @return String
     */
    @Override
    public String toString() {
        return "Topic{" + topic + ", id=" + id + '}';
    }


}
