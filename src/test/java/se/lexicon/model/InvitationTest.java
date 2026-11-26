package se.lexicon.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class InvitationTest {
    //Tests the creation of a valid invitation
    @Test
    void testCreateInvitation() {
        LocalDateTime start = LocalDateTime.of(2026, 12, 19, 18, 0);
        LocalDateTime end = start.plusHours(3);
        Event event = new Event(1,
                "A Funeral for Your Bad Decisions",
                "Some things deserve a beautiful ending.",
                start, end, "The Rose Memorial Hall", 50);
        Participant participant = new IndividualParticipant(
                1, "Anna Rose", "anna@example.com");
        Invitation invitation = new Invitation(
                1, event, participant, InvitationStatus.PENDING);
        assertEquals(1, invitation.getId());
        assertEquals(event, invitation.getEvent());
        assertEquals(participant, invitation.getParticipant());
        assertEquals(InvitationStatus.PENDING, invitation.getStatus());
    }
    //Tests that an invitation without an event is rejected
    @Test
    void testMissingEvent() {
        Participant participant = new IndividualParticipant(
                1, "Anna Rose", "anna@example.com");
        assertThrows(IllegalArgumentException.class, () -> {
            new Invitation(1, null, participant,
                    InvitationStatus.PENDING);
        });
    }
    //Tests that an invitation without a participant is rejected
    @Test
    void testMissingParticipant() {
        LocalDateTime start = LocalDateTime.of(2026, 12, 19, 18, 0);
        LocalDateTime end = start.plusHours(3);
        Event event = new Event(1, "Test Event", "Test description",
                start, end, "Test Location", 50);
        assertThrows(IllegalArgumentException.class, () -> {
            new Invitation(1, event, null,
                    InvitationStatus.PENDING);
        });
    }
    //Tests that an invitation without a status is rejected
    @Test
    void testMissingStatus() {
        LocalDateTime start = LocalDateTime.of(2026, 12, 19, 18, 0);
        LocalDateTime end = start.plusHours(3);
        Event event = new Event(1, "Test Event", "Test description",
                start, end, "Test Location", 50);
        Participant participant = new IndividualParticipant(
                1, "Anna Rose", "anna@example.com");
        assertThrows(IllegalArgumentException.class, () -> {
            new Invitation(1, event, participant, null);
        });
    }
    //Tests that an invitation status can be updated
    @Test
    void testUpdateInvitationStatus() {
        LocalDateTime start = LocalDateTime.of(2026, 12, 19, 18, 0);
        LocalDateTime end = start.plusHours(3);
        Event event = new Event(1, "Test Event", "Test description",
                start, end, "Test Location", 50);
        Participant participant = new IndividualParticipant(
                1, "Anna Rose", "anna@example.com");
        Invitation invitation = new Invitation(
                1, event, participant, InvitationStatus.PENDING);
        invitation.setStatus(InvitationStatus.ACCEPTED);
        assertEquals(InvitationStatus.ACCEPTED, invitation.getStatus());
        invitation.setStatus(InvitationStatus.DECLINED);
        assertEquals(InvitationStatus.DECLINED, invitation.getStatus());
    }
}