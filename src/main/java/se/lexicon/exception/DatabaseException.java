package se.lexicon.exception;

//Handles errors related to database operations
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}