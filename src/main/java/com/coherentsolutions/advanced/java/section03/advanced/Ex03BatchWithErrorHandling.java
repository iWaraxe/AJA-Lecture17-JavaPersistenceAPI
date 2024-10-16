package com.coherentsolutions.advanced.java.section03.advanced;

import com.coherentsolutions.advanced.java.util.DatabasePoolConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Ex03BatchWithErrorHandling.java
 * This class demonstrates how to handle errors during JDBC batch processing.
 */
public class Ex03BatchWithErrorHandling {

    public static void main(String[] args) {
        String insertQuery = "INSERT INTO users (username, email) VALUES (?, ?)";

        // Get the connection from DatabaseConfig
        try (Connection conn = DatabasePoolConfig.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            // Disable auto-commit for batch processing
            conn.setAutoCommit(false);

            try {
                // Add multiple insert statements to the batch
                for (int i = 1; i <= 5; i++) {
                    pstmt.setString(1, "user" + i);
                    pstmt.setString(2, "user" + i + "@example.com");

                    // Introduce a deliberate error for user3 to simulate failure
                    if (i == 3) {
                        pstmt.setString(2, null);  // This will cause a SQL error (NULL email not allowed)
                    }

                    pstmt.addBatch();  // Add to batch
                }

                // Execute batch
                pstmt.executeBatch();
                conn.commit();  // Commit if everything goes well

            } catch (SQLException e) {
                System.err.println("Batch failed. Rolling back changes.");
                conn.rollback();  // Roll back on error
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
