package se.lexicon.model;
import java.time.LocalDateTime;

//Represents an event organized by the society
public class Event {
    private int id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private int capacity;

    //Constructor initializes the event properties
    public Event(int id, String title, String description,
                 LocalDateTime startTime, LocalDateTime endTime,
                 String location, int capacity) {
        this.id = id;
        setTitle(title);
        setDescription(description);
        setEventTimes(startTime, endTime);
        setLocation(location);
        setCapacity(capacity);
    }
    //Validates the event title
    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Event title cannot be empty.");
        }
        this.title = title.trim();
    }
    //Sets the optional event description
    public void setDescription(String description) {
        this.description = description == null ? "" : description.trim();
    }
    //Validates the event start and end times
    public void setEventTimes(LocalDateTime startTime,
                              LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException(
                    "Event dates cannot be empty.");
        }
        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException(
                    "End time must be after start time.");
        }
        this.startTime = startTime;
        this.endTime = endTime;
    }
    //Validates the event location
    public void setLocation(String location) {
        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException(
                    "Event location cannot be empty.");
        }
        this.location = location.trim();
    }
    //Validates the event capacity
    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Event capacity must be greater than zero.");
        }
        this.capacity = capacity;
    }
    // Getters provide access to the private event fields
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public String getLocation() {
        return location;
    }
    public int getCapacity() {
        return capacity;
    }
    //Returns event information in a readable format
    @Override
    public String toString() {
        return "ID: " + id +
                ", Title: " + title +
                ", Description: " + description +
                ", Start: " + startTime +
                ", End: " + endTime +
                ", Location: " + location +
                ", Capacity: " + capacity;
    }
}