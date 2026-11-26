package se.lexicon.model;

public abstract class Participant {
    //Private participant fields
    private int id;
    private String name;
    private String email;

    //Validate the participant's name
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name.trim();
    }
    //Validate the participant's email
    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address.");
        }
        this.email = email.trim();
    }
//Getters for other classes to read private fields without accessing them directly
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    //Each subclass defines its own participant type
    public abstract String getParticipantType();
    //Constructor reuses setters to validate input
    protected Participant(int id, String name, String email) {
        this.id = id;
        setName(name);
        setEmail(email);
    }
    //Returns participant information in a readable format
    @Override
    public String toString() {
        return "ID: " + id +
                ", Name: " + name +
                ", Email: " + email +
                ", Type: " + getParticipantType();
    }
}
