package com.coherentsolutions.advanced.java.section01.advanced;

import com.coherentsolutions.advanced.java.util.DatabaseConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Ex03ConnectionPooling.java
 * Demonstrates the concept of Connection Pooling using HikariCP for optimal database connections.
 */
public class Ex03ConnectionPooling {

    public static void main(String[] args) throws SQLException {
        // Configuring HikariCP (Connection Pooling)
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DatabaseConfig.getDbUrl());
        config.setUsername(DatabaseConfig.getDbUsername());
        config.setPassword(DatabaseConfig.getDbPassword());
        config.setMaximumPoolSize(10); // Set max connections in the pool

        // Initializing the DataSource (HikariCP)
        try (HikariDataSource dataSource = new HikariDataSource(config)) {
            // Fetching a connection from the pool
            try (Connection conn = dataSource.getConnection()) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM users");

                while (rs.next()) {
                    System.out.println("User ID: " + rs.getInt("id"));
                    System.out.println("Username: " + rs.getString("username"));
                }
            }
        }
    }
}
