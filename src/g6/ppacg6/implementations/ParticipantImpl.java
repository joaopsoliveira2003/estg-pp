package g6.ppacg6.implementations;

import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import g6.ppacg6.enumerations.ParticipantTypeEnum;

public abstract class ParticipantImpl implements Participant {

    private int id;
    private int CID = 0;

    private String name;
    private String bio;
    private ParticipantTypeEnum participantType;

    public ParticipantImpl(String name, String bio, ParticipantTypeEnum participantType) {
        this.id = ++CID;
        this.name = name;
        this.bio = bio;
        this.participantType = participantType;
    }

    public int getCID() {
        return this.CID;
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

    //sets

    // equals

    @Override
    public String toString() {
        return "ParticipantImpl{" + "id=" + id + ", name='" + name + '\'' + ", bio='" + bio + '\'' + ", participantType=" + participantType + '}';
    }
}
