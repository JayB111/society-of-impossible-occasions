package se.lexicon.service;
import se.lexicon.dao.ParticipantDAO;
import se.lexicon.model.Participant;
import se.lexicon.model.IndividualParticipant;
import se.lexicon.model.OrganisationParticipant;
import java.util.List;
import java.util.Optional;

public class ParticipantService {
    private final ParticipantDAO participantDAO;
    //Receives the DAO through the constructor
    public ParticipantService(ParticipantDAO participantDAO) {
        this.participantDAO = participantDAO;
    }
    //Registers a new individual participant
    public Participant registerIndividual(String name, String email) {
        Participant participant = new IndividualParticipant(0, name, email);
        return participantDAO.save(participant);
    }
    //Registers a new organisation participant
    public Participant registerOrganisation(String name, String email, String organisationName) {
        Participant participant = new OrganisationParticipant(0, name, email, organisationName);
        return participantDAO.save(participant);
    }
    //Retrieves all registered participants
    public List<Participant> findAll() {
        return participantDAO.findAll();
    }
    //Finds a participant by their ID
    public Optional<Participant> findById(int id) {
        return participantDAO.findById(id);
    }
    //Updates an existing participant
    public boolean updateParticipant(Participant participant) {
        return participantDAO.update(participant);
    }
    //Deletes a participant by their ID
    public boolean deleteParticipant(int id) {
        return participantDAO.delete(id);
    }
}