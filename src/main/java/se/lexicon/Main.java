package se.lexicon;

import se.lexicon.dao.ParticipantDAO;
import se.lexicon.dao.ParticipantDAOImpl;

public class Main {
    public static void main(String[] args) {
        ParticipantDAO participantDAO = new ParticipantDAOImpl();
        //Tests retrieving an individual participant
        System.out.println("Individual participant:");
        participantDAO.findById(1).ifPresent(System.out::println);
        //Tests retrieving an organisation participant
        System.out.println("\nOrganisation participant:");
        participantDAO.findById(5).ifPresent(System.out::println);
        //Tests retrieving all participants
        System.out.println("\nAll participants:");
        participantDAO.findAll().forEach(System.out::println);
        //Tests a nonexistent participant
        System.out.println("\nMissing participant: "
                + participantDAO.findById(999999).isEmpty());
    }
}