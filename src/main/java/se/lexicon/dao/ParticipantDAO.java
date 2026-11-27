package se.lexicon.dao;

import se.lexicon.model.Participant;
import java.util.List;
import java.util.Optional;

public interface ParticipantDAO {
    //Saves a new participant
    Participant save(Participant participant);
    //Finds a participant by ID (a participant might or might not be found)
    Optional<Participant> findById(int id);
    //Retrieves all participants (return multiple participants)
    List<Participant> findAll();
    //Updates an existing participant
    boolean update(Participant participant);
    //Deletes a participant by ID
    boolean delete(int id);
}