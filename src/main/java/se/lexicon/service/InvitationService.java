package se.lexicon.service;
import se.lexicon.dao.InvitationDAO;
import se.lexicon.dao.ParticipantDAO;
import se.lexicon.dao.EventDAO;
import se.lexicon.model.Invitation;
import se.lexicon.model.InvitationStatus;
import se.lexicon.model.Participant;
import se.lexicon.model.Event;
import java.util.List;
import java.util.Optional;

public class InvitationService {
    private final InvitationDAO invitationDAO;
    private final ParticipantDAO participantDAO;
    private final EventDAO eventDAO;
    //Receives the three DAOs through the constructor
    public InvitationService(InvitationDAO invitationDAO,
                             ParticipantDAO participantDAO,
                             EventDAO eventDAO) {
        this.invitationDAO = invitationDAO;
        this.participantDAO = participantDAO;
        this.eventDAO = eventDAO;
    }
    //Invites a participant to an event
    public Invitation inviteParticipant(int participantId, int eventId) {
        Participant participant = participantDAO.findById(participantId)
                .orElseThrow(() -> new IllegalArgumentException("Participant not found."));
        Event event = eventDAO.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found."));
        if (invitationDAO.exists(participantId, eventId)) {
            throw new IllegalArgumentException("Participant has already been invited.");
        }
        Invitation invitation = new Invitation(
                0, event, participant, InvitationStatus.PENDING);
        return invitationDAO.save(invitation);
    }
    //Updates the response to an invitation
    public boolean respondToInvitation(int invitationId, InvitationStatus status) {
        if (status != InvitationStatus.ACCEPTED
                && status != InvitationStatus.DECLINED) {
            throw new IllegalArgumentException(
                    "Response must be ACCEPTED or DECLINED.");
        }
        return invitationDAO.updateStatus(invitationId, status);
    }
    //Retrieves all invitations
    public List<Invitation> findAll() {
        return invitationDAO.findAll();
    }
    //Retrieves all invitations for a specific event
    public List<Invitation> findByEventId(int eventId) {
        return invitationDAO.findByEventId(eventId);
    }
    //Retrieves all invitations for a specific participant
    public List<Invitation> findByParticipantId(int participantId) {
        return invitationDAO.findByParticipantId(participantId);
    }
    //Retrieves participants who have accepted their invitations
    public List<Participant> getAttendanceList(int eventId) {
        return invitationDAO.findByEventId(eventId)
                .stream()
                .filter(invitation -> invitation.getStatus() == InvitationStatus.ACCEPTED)
                .map(Invitation::getParticipant)
                .toList();
    }
    //Calculates the number of available places at an event
    public int getAvailablePlaces(int eventId) {
        Event event = eventDAO.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found."));
        int acceptedCount = invitationDAO.countAcceptedByEventId(eventId);
        return event.getCapacity() - acceptedCount;
    }
    //Finds an invitation by its ID
    public Optional<Invitation> findById(int id) {
        return invitationDAO.findById(id);
    }
    //Deletes an invitation by its ID
    public boolean deleteInvitation(int id) {
        return invitationDAO.delete(id);
    }
}