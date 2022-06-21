/*
 * Nome: Rui Alexandre Borba Vitorino
 * Número: 8190479
 * Turma: LSIRC12T1
 *
 * Nome: João Pedro Silva Oliveira
 * Número: 8210291
 * Turma: LSIRC11T2
 */

package implementations;

import estg.ipp.pt.tp02_conferencesystem.exceptions.ParticipantException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import auxiliary.*;
import enumerations.*;

/** Class responsible for the Participant (abstract) */
public abstract class ParticipantImpl implements Participant {
    
    /** The Unique ID of a Participant */
    private int id;
    
    /** Class ID Counter */
    private static int CID = 0;

    /** Name of the Participant */
    private String name;
    
    /** BIography of the Participant */
    private String bio;
    
    /** Type of the Participant , Enumeration */
    private ParticipantTypeEnum participantType;

    /**
     * Constructor for a Participant
     * @param name name of the Participant
     * @param bio biography of the Participant
     * @param participantType type of the Participant
     */
    public ParticipantImpl(String name, String bio, ParticipantTypeEnum participantType) {
        this.id = ++CID;
        this.name = name;
        this.bio = bio;
        this.participantType = participantType;
    }

    /**
     * Get the ID of the Participant
     * @return int
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Get the name of the Participant
     * @return String
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Get the bio of the Participant
     * @return String
     */
    @Override
    public String getBio() {
        return this.bio;
    }

    /**
     * Get the Type of of the Participant
     * @return ParticipantEnum
     */
    public ParticipantTypeEnum getParticipantType() {
        return this.participantType;
    }

    /**
     * Set the name of the Participant, if valid
     * Validations are made using the StringValidations Auxiliary Class
     * @param name of the Participant to set
     * @throws ParticipantException when the given name is not valid
     */
    public void setName(String name) throws ParticipantException {
        try {
            if (StringValidations.isValidString(name, 100) ) this.name = name;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            throw new ParticipantException(e.getMessage());
        }
    }

    /**
     * Set the biography of the Participant, if valid
     * Validations are made using the StringValidations Auxiliary Class
     * @param bio f the Participant to set
     * @throws  ParticipantException when the given name is not valid
     */
    public void setBio(String bio) throws ParticipantException {
        try {
            if (StringValidations.isValidString(name, 100) ) this.bio = bio;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            throw new ParticipantException(e.getMessage());
        }
    }

    /**
     * Set the ParticipantType of the Participant, if not null
     * @param participantType the type of Participant
     * @throws ParticipantException when the type is null
     */
    public void setParticipantType(ParticipantTypeEnum participantType) throws ParticipantException {
        if ( participantType == null ) throw new ParticipantException("Participant Type cannot be null");
        this.participantType = participantType;
    }

    /**
     * Compares two participant's, by ID
     * @param obj the participant to compare with
     * False when:
     * Object is null, or not an object of the Participant Interface
     * True when:
     * It's the same object, or has the same properties
     * @return boolean 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (this == obj) return true;

        if ( !(obj instanceof Participant) ) return false;

        final Participant other = (Participant) obj;

        return ( this.id == other.getId() );
    }

     /**
     * List all the properties of the Participant
     * @return String
     */
    @Override
    public String toString() {
        return "ParticipantImpl{" + "id=" + id + ", name=" + name + ", bio=" + bio + ", participantType=" + participantType + '}';
    }

    
}
