package com.coherentsolutions.advanced.java.section01;

import com.coherentsolutions.advanced.java.util.DatabaseConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * Ex01JDBCRecap.java
 * This class demonstrates basic JDBC usage: connecting to a database, executing a query, and closing resources
 * while reading the database credentials from an external properties file.
 */
public class Ex01JDBCRecap {

    public static void main(String[] args) {
        // Get properties from DatabaseConfig utility class
        String url = DatabaseConfig.getDbUrl();
        String username = DatabaseConfig.getDbUsername();
        String password = DatabaseConfig.getDbPassword();



        // 1. Establishing a Connection
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to the database!");

            // 2. Creating a Statement and Executing a Query
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM users";
            ResultSet rs = stmt.executeQuery(query);

            // 3. Processing the ResultSet
            while (rs.next()) {
                System.out.println("User ID: " + rs.getInt("id"));
                System.out.println("Username: " + rs.getString("username"));
            }

            // Resources (Connection, Statement, ResultSet) are automatically closed with try-with-resources
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
