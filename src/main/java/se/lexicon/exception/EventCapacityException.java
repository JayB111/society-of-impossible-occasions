package se.lexicon.exception;

//Handles attempts to exceed an event's capacity
public class EventCapacityException extends RuntimeException {
    public EventCapacityException(String message) {
        super(message);
    }
}