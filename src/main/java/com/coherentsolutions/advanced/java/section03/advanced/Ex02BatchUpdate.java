package com.coherentsolutions.advanced.java.section03.advanced;

import com.coherentsolutions.advanced.java.util.DatabasePoolConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Ex02BatchUpdate.java
 * This class demonstrates batch processing with update statements using JDBC.
 */
public class Ex02BatchUpdate {

    public static void main(String[] args) {
        String updateQuery = "UPDATE users SET email = ? WHERE username = ?";

        // Get connection from DatabaseConfig
        try (Connection conn = DatabasePoolConfig.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            // Disable auto-commit for batch processing
            conn.setAutoCommit(false);

            // Adding updates to the batch
            for (int i = 1; i <= 5; i++) {
                pstmt.setString(1, "newuser" + i + "@example.com");
                pstmt.setString(2, "user" + i);
                pstmt.addBatch();  // Add to batch
            }

            // Execute batch and commit transaction
            int[] result = pstmt.executeBatch();
            conn.commit();

            System.out.println("Updated " + result.length + " records successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
