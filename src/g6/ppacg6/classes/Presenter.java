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

import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import g6.ppacg6.enumerations.DegreeEnum;
import g6.ppacg6.enumerations.FieldEnum;

public class Presenter implements Participant {

    private int id = 0;
    private static int CID = 0;
    
    private String name;
    private String bio;
    
    private Paper[] papers;
    private int nPapers;
    private static int MAX_PAPERS = 10;
    
    private DegreeEnum degree;
    private FieldEnum expertIn;

    /**
     * Constructor of the Presenter
     * @param name - name of the Presenter
     * @param bio - bio of the Presenter
     * @param degree - degree of the Presenter
     * @param expertIn - level of expertise of the Presenter
     */
    public Presenter(String name, String bio, DegreeEnum degree, FieldEnum expertIn) {
        this.id = ++CID;
        this.name = name;
        this.bio = bio;
        this.nPapers = 0;
        this.papers = new Paper[MAX_PAPERS];
        this.degree = degree;
        this.expertIn = expertIn;
    }

    
    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getBio() {
        return this.bio;
    }
    
    /**
     * Get the number of Paper(s) of the Presenter
     * @return int
     */
    public int getnPapers() {
        return nPapers;
    }

    /**
     * Get the Degree of the Presenter
     * @return DegreeEnum
     */
    public DegreeEnum getDegree() {
        return degree;
    }

    /**
     * Set the Degree of the Presenter
     * @param degree 
     */
    public void setDegree(DegreeEnum degree) {
        this.degree = degree;
    }

    /**
     * Get the Field of expertise of the Presenter
     * @return FieldEnum
     */
    public FieldEnum getExpertIn() {
        return expertIn;
    }

    /**
     * Set the Field of expertise of the Presenter
     * @param expertIn 
     */
    public void setExpertIn(FieldEnum expertIn) {
        this.expertIn = expertIn;
    }
    
    /**
     * List all the Paper(s) of the Presenter
     * @return String
     */
    public String listPapers() {
        String str = "";
        if (nPapers == 0) {
            str += "No Papers";
            return str;
        } else {
            for (Paper paper : this.papers) {
                if (paper == null) break;
                str += paper.toString() + " ";
            }
            return str;
        }
    }
    
    /**
     * Find a given Paper of the Presenter
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
     * Add a Paper to the array papers[] of the Presenter
     * @param paper - Paper to add
     * @return boolean
     */
    public boolean addPaper(Paper paper) {
        if (paper == null) throw new NullPointerException("The paper to add cant be null.");
        
        int pos = findPaper(paper);
        
        if (nPapers == papers.length) return false;
        
        if (pos != -1) return false;
        
        papers[nPapers++] = paper;
        return true;
    }
    
    /**
     * Add an array of Paper[] to the array papers[] of the Presenter
     * @param papers - array of Paper to add
     * @return int - number of papers successfully added
     */
    public int addPaper(Paper[] papers) {
        int x = 0;
        
        for ( Paper paper : papers ) {
            if ( paper == null ) break;
            if (addPaper(paper)) {
                x++;
            }
        }
        return x;
    }
    
    /**
     * Remove a given Paper from the array papers[] of the Presenter
     * @param paper - Paper to remove
     * @return boolean
     * @throws NullPointerException - If the given paper is null
     */
    public boolean delPaper(Paper paper) throws NullPointerException {
        if (nPapers == 0) return false;
        
        if (paper == null) throw new NullPointerException("The topic to add cant be null.");
        
        int pos = findPaper(paper);
        
        if (pos == -1) return false;
        
        for ( int x = pos; x < nPapers - 1; x++) {
            papers[x] = papers[x + 1];
        }
        
        papers[--nPapers] = null;
        return true;
    }
    
    /**
     * Remove a given array of papers[] of the array papers[] of the Presenter
     * @param papers - array of Paper to remove
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


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        
        if (obj == null) return false;
        
        if (getClass() != obj.getClass()) return false;
        
        final Presenter other = (Presenter) obj;
        
        if ( this.id == other.id ) return true;
        
        return this.degree == other.degree;
    }

    /**
     * List all the properties of the Presenter
     * @return String
     */
    @Override
    public String toString() {
        return "Presenter{" + "id=" + id + ", name=" + name + ", bio=" + bio + ", papers=[" + listPapers() + "], nPapers=" + nPapers + ", degree=" + degree + ", expertIn=" + expertIn + '}';
    }
  
}
