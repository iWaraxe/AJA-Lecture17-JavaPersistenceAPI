package com.coherentsolutions.advanced.java.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.sql.DataSource;

/**
 * DatabaseConfig.java
 * Utility class to configure HikariCP connection pooling for optimized database access.
 */
public class DatabasePoolConfig {

    private static HikariDataSource dataSource;

    static {
        // Load properties from the file
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/db.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
        }

        // HikariCP configuration
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.username"));
        config.setPassword(properties.getProperty("db.password"));

        // Set HikariCP-specific settings
        config.setMaximumPoolSize(10);  // Max 10 connections in the pool
        config.setMinimumIdle(2);       // Minimum idle connections in the pool
        config.setIdleTimeout(30000);   // 30 seconds idle timeout
        config.setMaxLifetime(1800000); // 30 minutes max lifetime for each connection

        // Initialize the HikariDataSource
        dataSource = new HikariDataSource(config);
    }

    // Method to get the DataSource (HikariCP pool)
    public static DataSource getDataSource() {
        return dataSource;
    }
}
