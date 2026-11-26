package se.lexicon.model;

//Inherits common properties from Participant
public class IndividualParticipant extends Participant {
    public IndividualParticipant(int id, String name, String email) {
        super(id, name, email);
    }
    //Defines the type of this participant
    @Override
    public String getParticipantType() {
        return "INDIVIDUAL";
    }
}