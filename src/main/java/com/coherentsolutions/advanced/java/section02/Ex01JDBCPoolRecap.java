package com.coherentsolutions.advanced.java.section02;

import com.coherentsolutions.advanced.java.util.DatabasePoolConfig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Ex01JDBCRecap.java
 * This class demonstrates JDBC usage with HikariCP connection pooling for optimized database access.
 */
public class Ex01JDBCPoolRecap {

    public static void main(String[] args) {
        // 1. Get a connection from the pool
        try (Connection conn = DatabasePoolConfig.getDataSource().getConnection()) {
            System.out.println("Connected to the database using connection pool!");

            // 2. Create a Statement and execute a query
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM users";
            ResultSet rs = stmt.executeQuery(query);

            // 3. Process the ResultSet
            while (rs.next()) {
                System.out.println("User ID: " + rs.getInt("id"));
                System.out.println("Username: " + rs.getString("username"));
            }

            // Connections and resources are automatically closed by try-with-resources
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
