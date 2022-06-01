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

import g6.ppacg6.enumerations.DegreeEnum;
import g6.ppacg6.enumerations.FieldEnum;
import g6.ppacg6.enumerations.ParticipantTypeEnum;
import g6.ppacg6.exceptions.PaperException;
import g6.ppacg6.implementations.ParticipantImpl;

/** Class responsible for the professor */
public class Professor extends ParticipantImpl {

    /** The professor's papers */
    private Paper[] papers;

    /** The number of papers */
    private int nPapers;

    /** The maximum of papers */
    private static int MAX_PAPERS = 10;

    /** The professor's degree */
    private DegreeEnum degree;

    /** The professor's level of expertise */
    private FieldEnum expertIn;

    /**
     * Constructor of the professor
     * @param name - name of the professor
     * @param bio - bio of the professor
     * @param degree - degree of the professor
     * @param expertIn - level of expertise of the professor
     * @apiNote <b>nPapers</b> is set to 0 by default and the <b>papers</b> array is set to the max ammount of papers
     */
    public Professor(String name, String bio, ParticipantTypeEnum participantType, DegreeEnum degree, FieldEnum expertIn) {
        super(name, bio, participantType);
        this.nPapers = 0;
        this.papers = new Paper[MAX_PAPERS];
        this.degree = degree;
        this.expertIn = expertIn;
    }
    
    /**
     * Gets the number of Papers
     * @return int
     */
    public int getnPapers() {
        return nPapers;
    }

    /**
     * Gets the Degree
     * @return DegreeEnum
     */
    public DegreeEnum getDegree() {
        return degree;
    }

    /**
     * Sets the Degree
     * @param degree - DegreeEnum
     */
    public void setDegree(DegreeEnum degree) {
        this.degree = degree;
    }

    /**
     * Gets the level of expertise
     * @return FieldEnum
     */
    public FieldEnum getExpertIn() {
        return expertIn;
    }

    /**
     * Sets the level of expertise
     * @param expertIn - FieldEnum
     */
    public void setExpertIn(FieldEnum expertIn) {
        this.expertIn = expertIn;
    }
    
    /**
     * Lists all the papers
     * @return String
     */
    public String listPapers() {
        String str = "";
        if (nPapers == 0) {
            str += "No Papers";
        } else {
            for (Paper paper : this.papers) {
                if (paper == null) break;
                str += paper.toString() + " ";
            }
        }
        return str;
    }
    
    /**
     * Finds a specific paper
     * @param paper - Paper to find
     * @return int
     */
    private int findPaper(Paper paper) {
        int pos = -1, x = 0;
        
        while( pos == -1 && x < nPapers) {
            if ( papers[x].equals(paper) ) {
                pos = x;
            }
            x++;
        }
        return pos;
    }
    
    /**
     * Adds a paper to the array papers[] of the professor
     * @param paper paper to add
     * @return boolean
     */
    public boolean addPaper(Paper paper) throws PaperException {
        if (paper == null) throw new PaperException("The paper to add can't be null.");
        
        int pos = findPaper(paper);
        
        if (nPapers == papers.length) return false;
        
        if (pos != -1) return false;
        
        papers[nPapers++] = paper;
        return true;
    }
    
    /**
     * Adds an array of Paper[] to the paper's array
     * @param papers array of papers to add
     * @return int - number of papers successfully added
     */
    public int addPaper(Paper[] papers) {
        int x = 0;
        
        for ( Paper paper : papers ) {
            try {
                if (addPaper(paper)) {
                    x++;
                }
            } catch (PaperException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return x;
    }
    
    /**
     * Removes a given paper from the paper's array
     * @param paper Paper to remove
     * @return boolean
     * @throws NullPointerException if the given paper is null
     */
    public boolean delPaper(Paper paper) throws NullPointerException {
        if (nPapers == 0) return false;
        
        if (paper == null) throw new NullPointerException("The topic to add can't be null.");
        
        int pos = findPaper(paper);
        
        if (pos == -1) return false;
        
        for ( int x = pos; x < nPapers - 1; x++) {
            papers[x] = papers[x + 1];
        }
        
        papers[--nPapers] = null;
        return true;
    }
    
    /**
     * Removes a given array of papers[] from the paper's array
     * @param papers - array of Papers to remove
     * @return int - the number of successfully removed papers
     */
    public int delPaper(Paper[] papers) {
        int x = 0;
        
        for ( Paper paper : papers ) {
            if ( paper == null ) break;
            if (delPaper(paper)) {
                x++;
            }
        }
        return x;
    }

    /**
     * Gets a specific paper based on the index
     * @param index - index of the paper
     * @return the Paper
     */
    public Paper getPaper(int index) {
        return papers[index];
    }

    /**
     * Gets the properties of the professor
     * @return string
     */
    @Override
    public String toString() {
        return super.toString() + "Professor{" +
                "papers=[" + listPapers() +
                "], nPapers=" + nPapers +
                ", degree=" + degree +
                ", expertIn=" + expertIn +
                '}';
    }
}
