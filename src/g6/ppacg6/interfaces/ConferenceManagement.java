package g6.ppacg6.interfaces;


import estg.ipp.pt.tp02_conferencesystem.exceptions.ConferenceException;
import estg.ipp.pt.tp02_conferencesystem.io.interfaces.Exporter;
import g6.ppacg6.implementations.ConferenceImpl;

/** Interface responsible for extending the exporter interface and for the management of the conferences*/
public interface ConferenceManagement extends Exporter {

    /**
     * Method responsible for adding a new conference
     * @param conference the conference to be added
     * @return true if the conference was added, false otherwise
     * @throws ConferenceException if the conference already exists, or if is null or in case of any memory error
     */
    public boolean addConference(ConferenceImpl conference) throws ConferenceException;

    /**
     * Method responsible for removing a conference
     * @param conference the conference to be removed
     * @return true if the conference was removed, false otherwise
     * @throws ConferenceException if the conference does not exist or if is null
     */
    public boolean delConference(ConferenceImpl conference) throws ConferenceException;
}
