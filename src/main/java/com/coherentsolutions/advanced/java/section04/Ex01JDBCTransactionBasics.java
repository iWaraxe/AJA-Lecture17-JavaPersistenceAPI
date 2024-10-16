package com.coherentsolutions.advanced.java.section04;

import com.coherentsolutions.advanced.java.util.DatabasePoolConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Ex01JDBCTransactionBasics.java
 * Demonstrates basic transaction management in JDBC using commit and rollback.
 */
public class Ex01JDBCTransactionBasics {

    public static void main(String[] args) {
        String updateAccountQuery1 = "UPDATE accounts SET balance = balance - 100 WHERE user_id = 1";
        String updateAccountQuery2 = "UPDATE accounts SET balance = balance + 100 WHERE user_id = 2";

        // Get a connection from the connection pool
        try (Connection conn = DatabasePoolConfig.getDataSource().getConnection()) {

            // Disable auto-commit mode for transaction management
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt1 = conn.prepareStatement(updateAccountQuery1);
                 PreparedStatement pstmt2 = conn.prepareStatement(updateAccountQuery2)) {

                // Execute first update (debit)
                pstmt1.executeUpdate();

                // Simulate an error
                // int error = 1 / 0;

                // Execute second update (credit)
                pstmt2.executeUpdate();

                // Commit the transaction if everything is successful
                conn.commit();
                System.out.println("Transaction committed successfully.");

            } catch (SQLException e) {
                // Rollback the transaction if there is any error
                conn.rollback();
                System.out.println("Transaction rolled back due to an error.");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
