package com.proc.db;

import com.proc.model.Owner;
import com.proc.utility.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OwnerDB {

    private static OwnerDB instance;
    private final Connection connection;

    // Private constructor - Singleton pattern
    private OwnerDB() {
        this.connection = DBConnection.getInstance().getConnection();
    }

    // Thread-safe Singleton getInstance
    public static synchronized OwnerDB getInstance() {
        if (instance == null) {
            instance = new OwnerDB();
        }
        return instance;
    }

    // Insert Owner
    public boolean insertOwner(Owner owner) {
        String sql = "INSERT INTO OWNER (name, address) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, owner.getName());
            ps.setString(2, owner.getAddress());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("insertOwner error: " + e.getMessage());
            return false;
        }
    }

    // Get all Owners
    public List<Owner> getAllOwners() {
        List<Owner> owners = new ArrayList<>();
        String sql = "SELECT * FROM OWNER";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Owner o = new Owner();
                o.setId(rs.getInt("id"));
                o.setName(rs.getString("name"));
                o.setAddress(rs.getString("address"));
                owners.add(o);
            }
        } catch (SQLException e) {
            System.err.println("getAllOwners error: " + e.getMessage());
        }
        return owners;
    }

    // Get Owner by ID
    public Owner getOwnerById(int id) {
        String sql = "SELECT * FROM OWNER WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Owner o = new Owner();
                o.setId(rs.getInt("id"));
                o.setName(rs.getString("name"));
                o.setAddress(rs.getString("address"));
                return o;
            }
        } catch (SQLException e) {
            System.err.println("getOwnerById error: " + e.getMessage());
        }
        return null;
    }
}