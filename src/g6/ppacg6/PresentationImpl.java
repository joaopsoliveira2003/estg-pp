/*
* Nome: Rui Alexandre Borba Vitorino
* Numero: 8190479
* Turma: LSIRC12T1
*
* Nome: Joao Pedro Silva Oliveira
* Numero: 8210291
* Turma: LSIRC12T2
*/

package g6.ppacg6;

import estg.ipp.pt.tp02_conferencesystem.enumerations.PresentationState;

import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Presentation;

import g6.ppacg6.exceptions.EquipmentException;
        
import java.time.Duration;
import java.time.LocalDateTime;

        
public class PresentationImpl implements Presentation {
    private int id = 0;
    private static int CID = 0;

    private String title;
    
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    private PresentationState presentationState;
    
    private Equipment[] requiredEquipments;
    private int nRequiredEquipments = 0;
    private static int MAX_EQUIPMENTS = 10;
    
    private Participant presenter;
    // fazer array de apresentadores???
    
    /**
     * Constructor for a Presentation
     * @param title
     * @param startTime
     * @param endTime
     * presentationState
     * @param presenter 
     */
    public PresentationImpl(String title, LocalDateTime startTime, LocalDateTime endTime, Participant presenter) {
        this.id = ++CID;
        this.title = title;
        this.presentationState = PresentationState.NOT_PRESENTED;
        this.startTime = startTime;
        this.endTime = endTime;
        this.presenter = presenter; // fazer check?
        this.nRequiredEquipments = 0;
        this.requiredEquipments = new Equipment[MAX_EQUIPMENTS];
    }
    
    
    /**
     * Get the ID of the Presentation
     * @return int
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Get the title of the Presentation
     * @return String
     */
    @Override
    public String getTitle() {
        return this.title;
    }

    /**
     * Set the title of the Presentation
     * @param title
     * @throws StringIndexOutOfBoundsException - when the String is "too big" or empty
     * @throws NullPointerException - when the String is null
     */
    public void setTitle(String title) throws
            StringIndexOutOfBoundsException, NullPointerException {
        if (title == null) throw new NullPointerException("Cant set the title as null.");
        if (title.length() >= 50) {
            throw new StringIndexOutOfBoundsException("Title exceeded the limit of 50 characters.");
        } else if (title.length() <= 0) {
            throw new StringIndexOutOfBoundsException("Title cant be empty.");
        } else {
            this.title = title;
        }    
    }
    
    /**
     * Get the duration of the Presentation, in minutes
     * @return int
     */
    @Override
    public int getDuration() {
        Duration startTimeD = Duration.between(this.startTime, LocalDateTime.now());
        Duration endTimeD = Duration.between(this.endTime, LocalDateTime.now());
        return (int) (startTimeD.toMinutes() - endTimeD.toMinutes());
    }

    /**
     * Get the PresentationState of the Presentation
     * @return PresentationState
     */
    @Override
    public PresentationState getPresentationState() {
        return this.presentationState;
    }

    /**
     * 
     */
    @Override
    public void setPresented() {
        this.presentationState = PresentationState.PRESENTED;
    }

    /**
     * Get the Presenter of the Presentation
     * @return Presenter
     */
    @Override
    public Participant getPresenter() {
        return this.presenter;
    }

    public int getnRequiredEquipments() {
        return nRequiredEquipments;
    }

    
    private int findRequiredEquipment(Equipment equip) {
        int pos = -1, x = 0;
        
        while ( pos == -1 && x < nRequiredEquipments ) {
            if ( this.requiredEquipments[x].equals(equip) ) {
                pos = x;
            }
            x++;
        }
        return pos;
    }

    public boolean addRequiredEquipment(Equipment equip) throws EquipmentException {
        if ( equip == null ) throw new EquipmentException("Can't add a null equipment");
        
        if ( nRequiredEquipments == this.requiredEquipments.length ) return false;
        
        int pos = findRequiredEquipment(equip);
        
        if ( pos != -1 ) throw new EquipmentException("The equipment is already added");
        
        this.requiredEquipments[nRequiredEquipments++] = equip;
        return true;
    }
    
    public boolean delRequiredEquipment(Equipment equip) throws EquipmentException {
        if ( equip == null ) throw new EquipmentException("Can't add a null equipment");
        
        if ( nRequiredEquipments == 0 ) throw new EquipmentException("There are no Equipments to remove");
        
        int pos = findRequiredEquipment(equip);
        
        if ( pos == -1 ) throw new EquipmentException("Couldn't find the Equipmento to remove");
        
        for ( int x = pos; x < nRequiredEquipments - 1; x++ ) {
            this.requiredEquipments[x] = this.requiredEquipments[x + 1];
        }
        
        this.requiredEquipments[--nRequiredEquipments] = null;
        return true;
    }
    
    public Equipment[] getRequiredEquipments() {
        return this.requiredEquipments;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        
        if (obj == null) return false;
        
        if (getClass() != obj.getClass()) return false;
        
        final PresentationImpl other = (PresentationImpl) obj;
        if ( this.id == other.id ) return true;
    
        return ( this.startTime.equals(other.startTime) );
    }

    
    /**
     * List all the properties of the Presentation
     * @return String
     */
    @Override
    public String toString() {
        return "PresentationImpl{" + "id=" + id + ", title=" + title + ", startTime=" + startTime + ", endTime=" + endTime + ", presentationState=" + presentationState + ", presenter=[" + presenter.toString() + "]}";
    }
     
}


