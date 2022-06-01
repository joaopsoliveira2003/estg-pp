package g6.ppacg6.interfaces;


import estg.ipp.pt.tp02_conferencesystem.exceptions.ConferenceException;
import estg.ipp.pt.tp02_conferencesystem.io.interfaces.Exporter;
import g6.ppacg6.implementations.ConferenceImpl;

public interface ConferenceManagement extends Exporter {
    public boolean addConference(ConferenceImpl conference) throws ConferenceException;

    public boolean delConference(ConferenceImpl conference) throws ConferenceException;
}
