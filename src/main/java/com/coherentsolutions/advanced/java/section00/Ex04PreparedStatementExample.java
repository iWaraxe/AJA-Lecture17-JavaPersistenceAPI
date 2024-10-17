// File: Ex04PreparedStatementExample.java
// Package: com.coherentsolutions.advanced.java.section00

package com.coherentsolutions.advanced.java.section00;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class demonstrates how to execute parameterized queries using PreparedStatement.
 */
public class Ex04PreparedStatementExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String user = "bestuser";
        String password = "bestuser";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Students (name, age) VALUES (?, ?)")) {

            // Setting parameters for PreparedStatement
            preparedStatement.setString(1, "Jane Smith");
            preparedStatement.setInt(2, 23);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Inserted " + rowsAffected + " row(s) into 'Students' table.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
