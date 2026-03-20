package com.proc.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3307/car_db";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    // Private constructor - Singleton pattern
    private DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully.");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("DB Connection Error: " + e.getMessage());
        }
    }

    // Thread-safe Singleton getInstance
    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}