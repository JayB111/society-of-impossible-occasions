package se.lexicon.service;
import se.lexicon.dao.EventDAO;
import se.lexicon.model.Event;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class EventService {
    private final EventDAO eventDAO;
    //Receives the DAO through the constructor
    public EventService(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }
    //Creates and saves a new event
    public Event createEvent(String title, String description,
                             LocalDateTime startTime, LocalDateTime endTime,
                             String location, int capacity) {
        Event event = new Event(0, title, description,
                startTime, endTime, location, capacity);
        return eventDAO.save(event);
    }
    //Retrieves all registered events
    public List<Event> findAll() {
        return eventDAO.findAll();
    }
    //Retrieves upcoming events
    public List<Event> findUpcomingEvents() {
        return eventDAO.findUpcomingEvents();
    }
    //Finds an event by its ID
    public Optional<Event> findById(int id) {
        return eventDAO.findById(id);
    }
    //Updates an existing event
    public boolean updateEvent(Event event) {
        return eventDAO.update(event);
    }
    //Deletes an event by its ID
    public boolean deleteEvent(int id) {
        return eventDAO.delete(id);
    }
}