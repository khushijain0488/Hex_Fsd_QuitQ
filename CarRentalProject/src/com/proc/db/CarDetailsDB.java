package com.proc.db;

import com.proc.model.CarDetails;
import com.proc.utility.DBConnection;

import java.sql.*;

public class CarDetailsDB {

    private static CarDetailsDB instance;
    private final Connection connection;

    // Private constructor - Singleton pattern
    private CarDetailsDB() {
        this.connection = DBConnection.getInstance().getConnection();
    }

    // Thread-safe Singleton getInstance
    public static synchronized CarDetailsDB getInstance() {
        if (instance == null) {
            instance = new CarDetailsDB();
        }
        return instance;
    }

    // Insert CarDetails
    public boolean insertCarDetails(CarDetails carDetails) {
        String sql = "INSERT INTO CAR_DETAILS (year_of_purchase, milage) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, carDetails.getYearOfPurchase());
            ps.setDouble(2, carDetails.getMilage());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("insertCarDetails error: " + e.getMessage());
            return false;
        }
    }

    // Get CarDetails by ID
    public CarDetails getCarDetailsById(int id) {
        String sql = "SELECT * FROM CAR_DETAILS WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CarDetails cd = new CarDetails();
                cd.setId(rs.getInt("id"));
                cd.setYearOfPurchase(rs.getInt("year_of_purchase"));
                cd.setMilage(rs.getDouble("milage"));
                return cd;
            }
        } catch (SQLException e) {
            System.err.println("getCarDetailsById error: " + e.getMessage());
        }
        return null;
    }
}