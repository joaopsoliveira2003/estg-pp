/*
 * Nome: Rui Alexandre Borba Vitorino
 * Número: 8190479
 * Turma: LSIRC12T1
 *
 * Nome: João Pedro Silva Oliveira
 * Número: 8210291
 * Turma: LSIRC11T2
 */

package g6.ppacg6.implementations;

import estg.ipp.pt.tp02_conferencesystem.enumerations.PresentationState;

import estg.ipp.pt.tp02_conferencesystem.exceptions.ParticipantException;
import estg.ipp.pt.tp02_conferencesystem.exceptions.PresentationException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Presentation;

import g6.ppacg6.auxiliary.StringValidations;
import g6.ppacg6.classes.Equipment;
import g6.ppacg6.enumerations.ParticipantTypeEnum;
import g6.ppacg6.exceptions.EquipmentException;
        
import java.time.Duration;
import java.time.LocalDateTime;

        
public class PresentationImpl implements Presentation {
    // TODO - triple check the class
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
    
    /**
     * Constructor for a Presentation
     * @param title - title of the Presentation
     * @param startTime - start time of the Presentation
     * @param endTime - end time of the Presentation
     * presentationState - state of the Presentation (default NOT_PRESENTED)
     * @param presenter - presenter of the Presentation
     */
    public PresentationImpl(String title, LocalDateTime startTime, LocalDateTime endTime, Participant presenter) {
        this.id = ++CID;
        this.title = title;
        this.presentationState = PresentationState.NOT_PRESENTED;
        this.startTime = startTime;
        this.endTime = endTime;
        this.presenter = presenter;
        this.nRequiredEquipments = 0;
        this.requiredEquipments = new Equipment[MAX_EQUIPMENTS];
    }


    /**
     * Get the ID of the Presentation
     * @return int - id of the Presentation
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Get the Title of the Presentation
     * @return String - title of the Presentation
     */
    @Override
    public String getTitle() {
        return this.title;
    }

    /**
     * Set the title of the Presentation, the validations are made using the StringValidations Class
     * @param title - String
     * @throws StringIndexOutOfBoundsException - when the String is "too big" or empty
     * @throws NullPointerException - when the String is null
     */
    public void setTitle(String title) throws StringIndexOutOfBoundsException, NullPointerException {
        try {
            if ( StringValidations.isValidString(title, 250) ) this.title = title;
        } catch ( StringIndexOutOfBoundsException e ) {
            throw new StringIndexOutOfBoundsException("The title is too big");
        } catch ( NullPointerException e ) {
            throw new NullPointerException("The title can't be null");
        }
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Get the duration of the Presentation, in minutes
     * @return int
     */
    @Override
    public int getDuration() {
        return (int) Duration.between(this.startTime, this.endTime).toMinutes();
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
     * Change the State of the Presentation to PresentationState.PRESENTED
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

    /**
     * Set the Presenter if it's a valid one, this is, not null and is of the Class Presenter
     * @param presenter - Presenter
     * @throws ParticipantException - when the Presenter is null or not of the Class Presenter
     */
    public void setPresenter(Participant presenter) throws ParticipantException {
        try {
            if (presenter == null) throw new NullPointerException("The Presenter can't be null");
        } catch ( NullPointerException ex ) {
            throw new ParticipantException(ex.getMessage());
        }

        try {
            if (! ((ParticipantImpl)presenter).getParticipantType().equals(
                    ParticipantTypeEnum.SPEAKER) ) throw new ParticipantException();
        } catch ( ClassCastException e ) {
            throw new ParticipantException("The Presenter is not a Participant");
        } catch ( ParticipantException e) {
            throw new ParticipantException("The Presenter is not a Speaker");
        }
        this.presenter = presenter;
    }

    /**
     * Get the number of required Equipments for the Presentation
     * @return int
     */
    public int getnRequiredEquipments() {
        return nRequiredEquipments;
    }

    /**
     * Find a given Equipment in the array this.requiredEquipments
     * We set the (pos)ition to -1 since the first position of the array is 0
     * Then we set a variable (x) to 0, this will be the position of the object, if found
     * Finally, we loop through the array and if, using the @overrided equals method, returns true,
     * the given object was found, setting and returing the position variable
     * to the position of the object in the array
     * @param equip - Equipment
     * @return pos
     */
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

    /**
     * If possible, adds the given Equipment to the array this.requiredEquipments,
     * making it a requirement to the Presentation to be presented.
     * Some validations are made, that will throw the EquipmentException, namely:
     * - If the parameter equip (Equipment) is null
     * - If there is no more space in the array
     * - If the parameter equip (Equipment) is already in the array
     *      (checking the result of the method findRequiredEquipment)
     * If all validations are passed, the parameter/object equip (Equipment) is added to the last
     * position of the array, and the static counter nRequiredEquipments is incremented,
     * returnign true as final result
     * @param equip - Equipment Class Object
     * @return boolean
     * @throws EquipmentException
     */
    public boolean addRequiredEquipment(Equipment equip) throws EquipmentException {
        if ( equip == null ) throw new EquipmentException("Can't add a null equipment");
        
        if ( nRequiredEquipments == this.requiredEquipments.length ) return false;
        
        int pos = findRequiredEquipment(equip);
        
        if ( pos != -1 ) throw new EquipmentException("The equipment is already a required one");
        
        this.requiredEquipments[nRequiredEquipments++] = equip;
        return true;
    }

    /**
     * Remove, if all validations are passed, a given Equipment, of the array this.requiredEquipments
     * The EquipmentException will be thrown if:
     * - The parameter equip (Equipment) is null
     * - There is no Equipment(s) in the array
     * - Couldn't find the Equipment in the array
     *      (using the method findRequiredEquipment)
     * If the given object (equip) is
     * @param equip
     * @return
     * @throws EquipmentException
     */
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


