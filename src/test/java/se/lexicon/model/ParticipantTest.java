package se.lexicon.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParticipantTest {
    // ests the creation of an individual participant
    @Test
    void testCreateIndividualParticipant() {
        Participant participant = new IndividualParticipant(
                1, "Anna Rose", "anna@example.com");

        assertEquals(1, participant.getId());
        assertEquals("Anna Rose", participant.getName());
        assertEquals("anna@example.com", participant.getEmail());
        assertEquals("INDIVIDUAL", participant.getParticipantType());
    }
    //Tests the creation of an organisation participant
    @Test
    void testCreateOrganisationParticipant() {
        OrganisationParticipant participant = new OrganisationParticipant(
                5, "Victoria Thorn", "victoria@example.com",
                "Black Rose Events");
        assertEquals(5, participant.getId());
        assertEquals("Victoria Thorn", participant.getName());
        assertEquals("victoria@example.com", participant.getEmail());
        assertEquals("Black Rose Events", participant.getOrganisationName());
        assertEquals("ORGANISATION", participant.getParticipantType());
    }
    //Tests that an empty participant name is rejected
    @Test
    void testInvalidName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new IndividualParticipant(1, "", "anna@example.com");
        });
    }
    //Tests that an invalid email address is rejected
    @Test
    void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            new IndividualParticipant(1, "Anna Rose", "invalidemail");
        });
    }
    //Tests that an empty organisation name is rejected
    @Test
    void testInvalidOrganisationName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OrganisationParticipant(
                    5, "Victoria Thorn", "victoria@example.com", "");
        });
    }
    //Tests that unnecessary spaces are removed
    @Test
    void testTrimParticipantDetails() {
        Participant participant = new IndividualParticipant(
                1, "  Anna Rose  ", "  anna@example.com  ");

        assertEquals("Anna Rose", participant.getName());
        assertEquals("anna@example.com", participant.getEmail());
    }
}