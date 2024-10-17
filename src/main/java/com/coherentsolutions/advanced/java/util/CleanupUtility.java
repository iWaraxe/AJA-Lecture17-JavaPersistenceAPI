package com.coherentsolutions.advanced.java.util;
// File: CleanupUtility.java

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class provides a utility method to clean up the Students table from the database.
 */
public class CleanupUtility {

    /**
     * This method drops the 'Students' table if it exists in the database.
     */
    public static void clearStudentsTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS Students";

        // Try-with-resources to automatically close connection and statement
        try (Connection connection = DriverManager.getConnection(
                DatabaseConfig.getDbUrl(),
                DatabaseConfig.getDbUsername(),
                DatabaseConfig.getDbPassword());
             Statement statement = connection.createStatement()) {

            // Execute the SQL statement to drop the table
            statement.executeUpdate(dropTableSQL);
            System.out.println("Table 'Students' dropped successfully.");

        } catch (SQLException e) {
            System.err.println("Failed to drop the 'Students' table.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Call the method to clear the Students table
        clearStudentsTable();
    }
}
