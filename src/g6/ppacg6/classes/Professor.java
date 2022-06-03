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

import estg.ipp.pt.tp02_conferencesystem.exceptions.ParticipantException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Session;
import g6.ppacg6.enumerations.DegreeEnum;
import g6.ppacg6.enumerations.FieldEnum;
import g6.ppacg6.enumerations.ParticipantTypeEnum;
import g6.ppacg6.exceptions.PaperException;
import g6.ppacg6.implementations.ParticipantImpl;



/** Class responsible for the professor */
public class Professor extends ParticipantImpl {

    /** The number of papers */
    private int nPapers;

    /** The professor's papers */
    private Paper[] papers;

    /** The initial amount of papers for the array */
    private static int INITIAL_PAPERS = 10;

    /** The professor's degree */
    private DegreeEnum degree;

    /** The professor's level of expertise */
    private FieldEnum expertIn;

    /**
     * Constructor of the professor
     * @param name name of the professor
     * @param bio bio of the professor
     * @param degree degree of the professor
     * @param expertIn level of expertise of the professor
     * @apiNote <b>nPapers</b> is set to 0 by default and the <b>papers</b> array is set to the initial amount of papers
     */
    public Professor(String name, String bio, ParticipantTypeEnum participantType, DegreeEnum degree, FieldEnum expertIn) {
        super(name, bio, participantType);
        this.nPapers = 0;
        this.papers = new Paper[INITIAL_PAPERS];
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
     * Gets a specific paper based on the index
     * @param index index of the paper
     * @return Paper
     */
    public Paper getPaper(int index) throws ParticipantException {
        try {
            if (this.papers[index] == null) throw new NullPointerException();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParticipantException("The index is out of bounds.");
        } catch (NullPointerException e) {
            throw new ParticipantException("Couldn't find the Paper");
        }
        return this.papers[index];
    }

    /**
     * Gets the Degree
     * @return DegreeEnum
     */
    public DegreeEnum getDegree() {
        return degree;
    }

    /**
     * Set's the Degree
     * @param degree DegreeEnum
     * @throws ParticipantException when the degree is null
     */
    public void setDegree(DegreeEnum degree) throws ParticipantException {
        try {
            if ( degree == null ) throw new NullPointerException();
        } catch (NullPointerException ex) {
            throw new ParticipantException("The Course can't be null.");
        }
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
     * Set's the level of expertise
     * @param expertIn FieldEnum
     * @throws ParticipantException when the field is null
     */
    public void setExpertIn(FieldEnum expertIn) throws ParticipantException {
        try {
            if ( expertIn == null ) throw new NullPointerException();
        } catch (NullPointerException ex) {
            throw new ParticipantException("The expertIn Field can't be null.");
        }
        this.expertIn = expertIn;
    }
    
    /**
     * Lists all the papers
     * @return String
     */
    public String listPapers() {
        if ( nPapers == 0 ) return "No Papers";

        String str = "";
        for (Paper paper : this.papers) {
            if (paper == null) break;
            str += paper.toString() + " ";
        }
        return str;
    }
    
    /**
     * Finds a specific paper in the array of papers
     * @param paper Paper to find
     * @return position
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

    private void increasePapersArr() throws OutOfMemoryError {
        Paper[] tmpPapers = new Paper[nPapers * 2];

        try {
            for ( int x = 0; x < nPapers; x++ ) {
                tmpPapers[x] = this.papers[x];
            }
        } catch (OutOfMemoryError e) {
            throw new OutOfMemoryError();
        }

        this.papers = tmpPapers;
    }

    /**
     * Adds a paper to the array papers[]
     * @param paper paper paper to add
     * @return boolean - true if added succesfully, false otherwise
     * @throws PaperException when the paper is null or already exists
     */
    public boolean addPaper(Paper paper) throws PaperException {
        if ( paper == null ) throw new PaperException("The paper to add can't be null.");
        
        int pos = findPaper(paper);
        
        try {
            if ( nPapers == papers.length ) increasePapersArr();
        } catch (OutOfMemoryError e) {
            throw new PaperException("There is no more memory available to accommodate more papers.");
        }
        
        if ( pos != -1 ) throw new PaperException("The paper is already in the array.");
        
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
    public boolean delPaper(Paper paper) throws ParticipantException {
        if ( nPapers == 0 ) return false;
        
        if ( paper == null ) throw new ParticipantException("The paper to add can't be null.");
        
        int pos = findPaper(paper);
        
        if ( pos == -1 ) throw new ParticipantException("The paper to remove doesn't exist.");
        
        for ( int x = pos; x < nPapers - 1; x++) {
            papers[x] = papers[x + 1];
        }
        
        papers[--nPapers] = null;
        return true;
    }
    
    /**
     * Removes a given array of papers[] from the paper's array
     * @param papers array of Papers to remove
     * @return int - the number of successfully removed papers
     */
    public int delPaper(Paper[] papers) throws ParticipantException {
        int x = 0;

        try {
            for ( Paper paper : papers ) {
                if ( paper == null ) throw new NullPointerException();
                if (delPaper(paper)) {
                    x++;
                }
            }
        } catch (NullPointerException ex) {
            throw new ParticipantException("The paper to remove can't be null.");
        }

        return x;
    }

    /**
     * Compares two professors, by all fields
     * First compare the two objects using the super class's equals method
     * If that method returns true, then they are the same object, otherwise,
     * we continue with the rest of the comparison of specific fields
     * @param obj the equipment to compare
     * @return true if the professors are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if ( super.equals(obj) ) return true;

        if ( obj.getClass() != this.getClass() ) return false;

        final Professor other = (Professor) obj;

        return ( this.nPapers == other.nPapers &&
                this.degree.equals(other.getDegree() ) &&
                this.expertIn.equals(other.getExpertIn()) );
    }

    /**
     * List all the professor's properties
     * @return String
     */
    @Override
    public String toString() {
        return super.toString() + "Professor{" + "nPapers=" + nPapers + ", papers=[" + listPapers() + "], " +
                "degree=" + degree + ", expertIn=" + expertIn + '}';
    }
}
