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

public class Theme {

    private int id = 0;
    private static int CID = 0;
    
    private String theme;
    
    private Topic[] topics;
    private int nTopics = 0;
    private static final int MAX_TOPICS = 10;

    /**
     * Constructor for a Theme
     * @param theme 
     */
    public Theme(String theme) {
        this.id = ++CID;
        this.theme = theme;
        this.nTopics = 0;
        this.topics = new Topic[MAX_TOPICS];
    }
    

    /**
     * Get the theme
     * @return String
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Set the theme
     * @param theme 
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * Get the number of topics of the Theme
     * @return 
     */
    public int getnTopics() {
        return nTopics;
    }

    
    /**
     * Find a given Topic in the array topics[]
     * @param topic
     * @return int
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
     * Add a Topic to the array topics[]
     * @param topic
     * @return boolean
     */
    public boolean addTopic(Topic topic) {
        if (topic == null) throw new NullPointerException("The topic to add cant be null.");
        
        int pos = findTopic(topic);
        
        if (nTopics == topics.length) return false;
        
        if (pos != -1) return false;
        
        topics[nTopics++] = topic;
        return true;
    }
    
    /**
     * Add an array of Topic[] to the array topics[]
     * @param topics
     * @return 
     */
    public int addTopic(Topic[] topics) {
        int x = 0;
        
        for ( Topic topic : topics ) {
            if ( topic == null ) break;
            if (addTopic(topic)) {
                x++;
            }
        }
        return x;
    }
    
    /**
     * Remove a Topic from the array topics[]
     * @param topic
     * @return boolean
     * @throws NullPointerException - if the given Topic is null
     */
    public boolean delTopic(Topic topic) throws NullPointerException {
        if (nTopics == 0) return false;
        
        if (topic == null) throw new NullPointerException("The topic to add cant be null.");
        
        int pos = findTopic(topic);
        
        if (pos == -1) return false;
        
        for ( int x = pos; x < nTopics - 1; x++) {
            topics[x] = topics[x + 1];
        }
        
        topics[--nTopics] = null;
        return true;
    }
    
    /**
     * Remove an array Topic[] from the array topics[]
     * @param topics
     * @return int - number of topics successfully removed
     */
    public int delTopic(Topic[] topics) {
        int x = 0;
        
        for ( Topic topic : topics ) {
            if ( topic == null ) break;
            if (delTopic(topic)) {
                x++;
            }
        }
        return x;
    }

    /**
     * List all the topics of the array topics[]
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
     * Compare two Theme(s), by ID and theme
     * If the @obj is not from the same Class, then return false
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        
        if (obj == null) return false;
        
        if (getClass() != obj.getClass()) return false;
        
        final Theme other = (Theme) obj;
        
        if (this.id == other.id) return true;
        
        return (this.theme.equals(other));
    }


    /**
     * List all the properties of the Theme
     * @return String
     */
    @Override
    public String toString() {
        return "Theme{" + theme + ", id=" + id + ", topics=[" + listTopics() + "], nTopics=" + nTopics + '}';
    }
   
}
