// File: Ex02EstablishConnection.java
// Package: com.coherentsolutions.advanced.java.section00

package com.coherentsolutions.advanced.java.section00;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class demonstrates how to establish a connection to a MySQL database.
 */
public class Ex02EstablishConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String user = "bestuser";
        String password = "bestuser";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Connected to the database successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Connection failed.");
            e.printStackTrace();
        }
    }
}
