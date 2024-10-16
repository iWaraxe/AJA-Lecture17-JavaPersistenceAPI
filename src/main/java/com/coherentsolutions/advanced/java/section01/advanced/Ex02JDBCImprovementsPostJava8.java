package com.coherentsolutions.advanced.java.section01.advanced;

import com.coherentsolutions.advanced.java.util.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Ex02JDBCImprovementsPostJava8.java
 * This class demonstrates JDBC enhancements introduced after Java 8, including the use of try-with-resources
 * and PreparedStatement.
 */
public class Ex02JDBCImprovementsPostJava8 {

    public static void main(String[] args) {
        // Get properties from DatabaseConfig utility class
        String url = DatabaseConfig.getDbUrl();
        String username = DatabaseConfig.getDbUsername();
        String password = DatabaseConfig.getDbPassword();


        // Using try-with-resources to handle resource management automatically
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM users WHERE email = ?";

            // Using PreparedStatement to prevent SQL injection
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, "john@example.com");

                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    System.out.println("User ID: " + rs.getInt("id"));
                    System.out.println("Username: " + rs.getString("username"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
