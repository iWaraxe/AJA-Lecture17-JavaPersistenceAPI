// File: Ex01LoadJDBCDriver.java
// Package: com.coherentsolutions.advanced.java.section00

package com.coherentsolutions.advanced.java.section00;

/**
 * This class demonstrates how to load the JDBC Driver.
 */
public class Ex01LoadJDBCDriver {
    public static void main(String[] args) {
        try {
            // Loading the MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver Loaded Successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load JDBC Driver.");
            e.printStackTrace();
        }
    }
}
