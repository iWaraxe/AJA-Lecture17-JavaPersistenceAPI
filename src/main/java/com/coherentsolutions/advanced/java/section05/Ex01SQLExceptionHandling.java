package com.coherentsolutions.advanced.java.section05;

import com.coherentsolutions.advanced.java.util.DatabasePoolConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ex01SQLExceptionHandling.java
 * Demonstrates handling SQLExceptions, logging, and performing a rollback in case of an error.
 */
public class Ex01SQLExceptionHandling {

    private static final Logger LOGGER = Logger.getLogger(Ex01SQLExceptionHandling.class.getName());

    public static void main(String[] args) {
        String updateBalanceQuery = "UPDATE accounts SET balance = balance - ? WHERE user_id = ?";

        // Get a connection from the pool
        try (Connection conn = DatabasePoolConfig.getDataSource().getConnection()) {
            // Disable auto-commit to handle transactions manually
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(updateBalanceQuery)) {
                // Prepare the update statement
                pstmt.setDouble(1, 100.00); // Deduct 100
                pstmt.setInt(2, 1);         // For user_id 1
                pstmt.executeUpdate();

                // Simulate an error (e.g., an invalid operation)
                if (true) {
                    throw new SQLException("Simulated error occurred during the transaction.");
                }

                // Commit if all went well
                conn.commit();
                System.out.println("Transaction committed successfully.");

            } catch (SQLException e) {
                // Rollback the transaction in case of failure
                if (conn != null) {
                    try {
                        System.out.println("Transaction is being rolled back due to an error.");
                        conn.rollback();
                    } catch (SQLException rollbackEx) {
                        LOGGER.log(Level.SEVERE, "Failed to rollback transaction.", rollbackEx);
                    }
                }

                // Log the detailed error for diagnostics
                LOGGER.log(Level.SEVERE, "SQLException: " + e.getMessage(), e);
                LOGGER.log(Level.SEVERE, "SQLState: " + e.getSQLState(), e);
                LOGGER.log(Level.SEVERE, "VendorError: " + e.getErrorCode(), e);
            }

        } catch (SQLException e) {
            // Handle connection-related issues
            LOGGER.log(Level.SEVERE, "Connection failed.", e);
        }
    }
}
