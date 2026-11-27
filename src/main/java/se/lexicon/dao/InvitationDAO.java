package se.lexicon.dao;

import se.lexicon.model.Invitation;
import se.lexicon.model.InvitationStatus;
import java.util.List;
import java.util.Optional;

// Defines the database operations for invitations
public interface InvitationDAO {
    //Saves a new invitation
    Invitation save(Invitation invitation);
    //Finds an invitation by ID
    Optional<Invitation> findById(int id);
    //Retrieves all invitations
    List<Invitation> findAll();
    //Retrieves invitations for a specific event
    List<Invitation> findByEventId(int eventId);
    //Retrieves invitations for a specific participant
    List<Invitation> findByParticipantId(int participantId);
    //Checks whether a participant has already been invited
    boolean exists(int participantId, int eventId);
    //Counts accepted invitations for an event
    int countAcceptedByEventId(int eventId);
    //Updates an invitation's response status
    boolean updateStatus(int id, InvitationStatus status);
    //Deletes an invitation
    boolean delete(int id);
}