-- Drop existing tables if they exist (to avoid conflicts when re-running the script)
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS users;

-- Create 'users' table
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample data into 'users' table
INSERT INTO users (username, email)
VALUES
    ('john_doe', 'john@example.com'),
    ('jane_doe', 'jane@example.com'),
    ('alice_smith', 'alice@example.com');

-- Create 'accounts' table
CREATE TABLE accounts (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          user_id INT NOT NULL,
                          balance DECIMAL(10, 2) NOT NULL,
                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert sample data into 'accounts' table
INSERT INTO accounts (user_id, balance)
VALUES
    (1, 1000.00),
    (2, 1500.50),
    (3, 2000.75);

-- Select all data from 'users' to verify
SELECT * FROM users;

-- Select all data from 'accounts' to verify
SELECT * FROM accounts;
