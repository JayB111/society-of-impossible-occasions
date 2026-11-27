package se.lexicon;

import se.lexicon.dao.EventDAO;
import se.lexicon.dao.EventDAOImpl;
import se.lexicon.exception.DatabaseException;

public class Main {
    public static void main(String[] args) {
        EventDAO eventDAO = new EventDAOImpl();
        //Tests deletion of an event with existing invitations
        try {
            boolean deleted = eventDAO.delete(1);
            System.out.println("Event deleted: " + deleted);
        } catch (DatabaseException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause().getMessage());
        }
    }
}