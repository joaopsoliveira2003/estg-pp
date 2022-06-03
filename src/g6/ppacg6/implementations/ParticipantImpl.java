package g6.ppacg6.implementations;

import estg.ipp.pt.tp02_conferencesystem.exceptions.ParticipantException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import g6.ppacg6.auxiliary.StringValidations;
import g6.ppacg6.enumerations.ParticipantTypeEnum;

public abstract class ParticipantImpl implements Participant {
    // TODO - check the class
    private int id;
    private static int CID = 0;

    private String name;
    private String bio;
    private ParticipantTypeEnum participantType;

    public ParticipantImpl(String name, String bio, ParticipantTypeEnum participantType) {
        this.id = ++CID;
        this.name = name;
        this.bio = bio;
        this.participantType = participantType;
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

    public ParticipantTypeEnum getParticipantType() {
        return this.participantType;
    }

    public void setName(String name) throws ParticipantException {
        try {
            if (StringValidations.isValidString(name, 100) ) this.name = name;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            throw new ParticipantException(e.getMessage());
        }
    }

    public void setBio(String bio) throws ParticipantException {
        try {
            if (StringValidations.isValidString(name, 100) ) this.bio = bio;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            throw new ParticipantException(e.getMessage());
        }
    }

    public void setParticipantType(ParticipantTypeEnum participantType) throws ParticipantException {
        if ( participantType == null ) throw new ParticipantException("Participant Type cannot be null");
        this.participantType = participantType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (this == obj) return true;

        if ( !(obj instanceof ParticipantImpl) ) return false;

        final ParticipantImpl other = (ParticipantImpl) obj;

        return ( this.id == other.getId() );
    }


    @Override
    public String toString() {
        return "ParticipantImpl{" + "id=" + id + ", name='" + name + '\'' + ", bio='" + bio + '\'' + ", participantType=" + participantType + '}';
    }
}
