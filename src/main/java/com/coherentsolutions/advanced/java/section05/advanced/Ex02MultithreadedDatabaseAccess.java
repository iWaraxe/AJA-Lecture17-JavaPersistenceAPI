package com.coherentsolutions.advanced.java.section05.advanced;

import com.coherentsolutions.advanced.java.util.DatabasePoolConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Ex02MultithreadedDatabaseAccess.java
 * Demonstrates managing reliable database operations in a multithreaded environment.
 */
public class Ex02MultithreadedDatabaseAccess {

    public static void main(String[] args) {
        // Create a thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // Submit multiple tasks to simulate concurrent database access
        for (int i = 1; i <= 5; i++) {
            int userId = i;
            executorService.submit(() -> performTransaction(userId, 50.00));
        }

        // Shutdown the executor service
        executorService.shutdown();
    }

    private static void performTransaction(int userId, double amount) {
        String updateBalanceQuery = "UPDATE accounts SET balance = balance + ? WHERE user_id = ?";

        // Each thread gets its own connection from the pool
        try (Connection conn = DatabasePoolConfig.getDataSource().getConnection()) {
            // Ensure the isolation level is set to prevent concurrent modification issues
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            try (PreparedStatement pstmt = conn.prepareStatement(updateBalanceQuery)) {
                conn.setAutoCommit(false);

                // Update the account balance
                pstmt.setDouble(1, amount);  // Add amount
                pstmt.setInt(2, userId);     // For the specific user
                pstmt.executeUpdate();

                // Commit the transaction
                conn.commit();
                System.out.println("Transaction committed successfully for user: " + userId);

            } catch (SQLException e) {
                // Rollback in case of an error
                conn.rollback();
                System.out.println("Transaction rolled back for user: " + userId);
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
