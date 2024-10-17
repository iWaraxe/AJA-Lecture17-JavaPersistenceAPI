// File: Ex03StatementExample.java
// Package: com.coherentsolutions.advanced.java.section00

package com.coherentsolutions.advanced.java.section00;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class demonstrates how to execute a SQL statement using Statement.
 */
public class Ex03StatementExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String user = "bestuser";
        String password = "bestuser";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            // Create table SQL query
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Students ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "name VARCHAR(50), "
                    + "age INT)";
            statement.executeUpdate(createTableSQL);
            System.out.println("Table 'Students' created.");

            // Insert data SQL query
            String insertSQL = "INSERT INTO Students (name, age) VALUES ('John Doe', 22)";
            statement.executeUpdate(insertSQL);
            System.out.println("Data inserted into 'Students' table.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
