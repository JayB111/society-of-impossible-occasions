package se.lexicon.dao;

import se.lexicon.model.Event;
import java.util.List;
import java.util.Optional;

//Defines the database operations for events
public interface EventDAO {
    //Saves a new event
    Event save(Event event);
    //Finds an event by ID
    Optional<Event> findById(int id);
    //Retrieves all events
    List<Event> findAll();
    //Retrieves upcoming events
    List<Event> findUpcomingEvents();
    //Updates an existing event
    boolean update(Event event);
    //Deletes an event by ID
    boolean delete(int id);
}