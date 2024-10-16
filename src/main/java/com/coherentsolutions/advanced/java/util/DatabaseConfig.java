package com.coherentsolutions.advanced.java.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * DatabaseConfig.java
 * Utility class to load database configuration from a properties file.
 */
public class DatabaseConfig {

    private static Properties properties = new Properties();

    static {
        // Static block to load properties only once when the class is loaded
        try (FileInputStream input = new FileInputStream("src/main/resources/db.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
        }
    }

    // Method to get the DB URL
    public static String getDbUrl() {
        return properties.getProperty("db.url");
    }

    // Method to get the DB username
    public static String getDbUsername() {
        return properties.getProperty("db.username");
    }

    // Method to get the DB password
    public static String getDbPassword() {
        return properties.getProperty("db.password");
    }
}
