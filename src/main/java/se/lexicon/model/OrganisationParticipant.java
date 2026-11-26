package se.lexicon.model;

//Represents a participant belonging to an organization
public class OrganisationParticipant extends Participant {
    private String organisationName;
    //Constructor reuses the parent class constructor
    public OrganisationParticipant(int id, String name,
                                   String email, String organisationName)
    {
        super(id, name, email);
        setOrganisationName(organisationName);
    }
    //Validates the organization name
    public void setOrganisationName(String organisationName) {
        if (organisationName == null || organisationName.isBlank()) {
            throw new IllegalArgumentException(
                    "Organisation name cannot be empty.");
        }
        this.organisationName = organisationName.trim();
    }
    public String getOrganisationName() {
        return organisationName;
    }
    //Defines the type of this participant
    @Override
    public String getParticipantType() {
        return "ORGANISATION";
    }
    //Includes the organisation name in the participant information
    @Override
    public String toString() {
        return super.toString() +
                ", Organisation: " + organisationName;
    }
}