package se.lexicon.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
public class EventTest {

    //Tests the creation of a valid event
    @Test
    void testCreateEvent() {
        LocalDateTime start = LocalDateTime.of(2026, 12, 19, 18, 0);
        LocalDateTime end = LocalDateTime.of(2026, 12, 19, 21, 0);
        Event event = new Event(
                1,
                "A Funeral for Your Bad Decisions",
                "Some things deserve a beautiful ending.",
                start,
                end,
                "The Rose Memorial Hall",
                50);
        assertEquals(1, event.getId());
        assertEquals("A Funeral for Your Bad Decisions", event.getTitle());
        assertEquals("Some things deserve a beautiful ending.", event.getDescription());
        assertEquals(start, event.getStartTime());
        assertEquals(end, event.getEndTime());
        assertEquals("The Rose Memorial Hall", event.getLocation());
        assertEquals(50, event.getCapacity());
    }
    //Tests that zero event capacity is rejected
    @Test
    void testInvalidCapacity() {
        LocalDateTime start = LocalDateTime.of(2026, 12, 19, 18, 0);
        LocalDateTime end = start.plusHours(3);
        assertThrows(IllegalArgumentException.class, () -> {
            new Event(1, "Test Event", "Test description",
                    start, end, "Test Location", 0);
        });
    }
    // Tests that an event cannot end before it starts
    @Test
    void testInvalidEventDates() {
        LocalDateTime start = LocalDateTime.of(2026, 12, 19, 18, 0);
        LocalDateTime end = start.minusHours(1);
        assertThrows(IllegalArgumentException.class, () -> {
            new Event(1, "Test Event", "Test description",
                    start, end, "Test Location", 50);
        });
    }
    //Tests that an empty event title is rejected
    @Test
    void testInvalidEventTitle() {
        LocalDateTime start = LocalDateTime.of(2026, 12, 19, 18, 0);
        LocalDateTime end = start.plusHours(3);
        assertThrows(IllegalArgumentException.class, () -> {
            new Event(1, "", "Test description",
                    start, end, "Test Location", 50);
        });
    }
    //Tests that an empty event location is rejected
    @Test
    void testInvalidLocation() {
        LocalDateTime start = LocalDateTime.of(2026, 12, 19, 18, 0);
        LocalDateTime end = start.plusHours(3);
        assertThrows(IllegalArgumentException.class, () -> {
            new Event(1, "Test Event", "Test description",
                    start, end, "", 50);
        });
    }
    //Tests that a missing description becomes an empty string
    @Test
    void testOptionalDescription() {
        LocalDateTime start = LocalDateTime.of(2026, 12, 19, 18, 0);
        LocalDateTime end = start.plusHours(3);
        Event event = new Event(1, "Test Event", null,
                start, end, "Test Location", 50);
        assertEquals("", event.getDescription());
    }
}