package se.lexicon.model;

//Represents an invitation connecting a participant to an event
public class Invitation {
    private int id;
    private Event event;
    private Participant participant;
    private InvitationStatus status;

    //Constructor initializes the invitation properties
    public Invitation(int id, Event event, Participant participant,
                      InvitationStatus status) {
        this.id = id;
        setEvent(event);
        setParticipant(participant);
        setStatus(status);
    }
    //Ensures the invitation has a valid event
    public void setEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException(
                    "Event cannot be null.");
        }
        this.event = event;
    }
    //Ensures the invitation has a valid participant
    public void setParticipant(Participant participant) {
        if (participant == null) {
            throw new IllegalArgumentException(
                    "Participant cannot be null.");
        }
        this.participant = participant;
    }
    //Ensures the invitation has a valid status
    public void setStatus(InvitationStatus status) {
        if (status == null) {
            throw new IllegalArgumentException(
                    "Invitation status cannot be null.");
        }
        this.status = status;
    }
    //Getters provide access to the private invitation fields
    public int getId() {
        return id;
    }
    public Event getEvent() {
        return event;
    }
    public Participant getParticipant() {
        return participant;
    }
    public InvitationStatus getStatus() {
        return status;
    }
    //Returns invitation information in a readable format
    @Override
    public String toString() {
        return "ID: " + id +
                ", Event: " + event.getTitle() +
                ", Participant: " + participant.getName() +
                ", Status: " + status;
    }
}