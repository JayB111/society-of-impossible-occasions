package se.lexicon.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class DatabaseConnection {

    //Database connection address
    private static final String URL =
            "jdbc:mysql://localhost:3306/society_events";
    //Establishes a connection to the database
    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        //Reads the database credentials from the properties file
        try (InputStream input = DatabaseConnection.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new SQLException("Database configuration file not found.");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new SQLException("Could not read database configuration.", e);
        }
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        //Connects Java to MySQL
        return DriverManager.getConnection(URL, username, password);
    }
}