package com.coherentsolutions.advanced.java.section03;

import com.coherentsolutions.advanced.java.util.DatabasePoolConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Ex01BasicBatchInsert.java
 * This class demonstrates a basic example of using JDBC batch processing to insert multiple rows efficiently.
 */
public class Ex01BasicBatchInsert {

    public static void main(String[] args) {
        String insertQuery = "INSERT INTO users (username, email) VALUES (?, ?)";

        // Get the connection from DatabaseConfig
        try (Connection conn = DatabasePoolConfig.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            // Disable auto-commit for batch processing
            conn.setAutoCommit(false);

            // Add multiple insert statements to the batch
            for (int i = 1; i <= 5; i++) {
                pstmt.setString(1, "user" + i);
                pstmt.setString(2, "user" + i + "@example.com");
                pstmt.addBatch();  // Add to batch
            }

            // Execute batch and commit transaction
            int[] result = pstmt.executeBatch();
            conn.commit();

            System.out.println("Inserted " + result.length + " records successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
