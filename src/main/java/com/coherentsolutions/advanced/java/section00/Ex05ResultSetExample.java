// File: Ex05ResultSetExample.java
// Package: com.coherentsolutions.advanced.java.section00

package com.coherentsolutions.advanced.java.section00;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class demonstrates how to use ResultSet to fetch data from a database.
 */
public class Ex05ResultSetExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String user = "bestuser";
        String password = "bestuser";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            // Execute a query to fetch data
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Students");

            // Process the ResultSet
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");

                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// Summary
// --------------------
// - JDBC is an essential API for interacting with relational databases in Java.
// - The four pillars of JDBC are: Driver, Connection, Statement (or PreparedStatement), and ResultSet.
// - Use DriverManager to establish a connection and Statement/PreparedStatement to execute queries.
// - ResultSet allows you to retrieve data from the database and iterate over it.
// - Always close your connections, statements, and result sets to avoid resource leaks.

// Up Next: We’ll explore advanced JDBC features, including batch processing, transaction management, and connection pooling!

