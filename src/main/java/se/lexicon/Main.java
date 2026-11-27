package se.lexicon;

import se.lexicon.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {
        //SQL query to retrieve event titles
        String sql = "SELECT title FROM events";
        //Connects to the database and executes the query
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            System.out.println("Database connection successful!");
            System.out.println("\nOur upcoming occasions:");
            //Reads each row returned by the database
            while (resultSet.next()) {
                System.out.println(resultSet.getString("title"));
            }
        } catch (SQLException e) {
            System.out.println("Database operation failed.");
            System.out.println(e.getMessage());
        }
    }
}