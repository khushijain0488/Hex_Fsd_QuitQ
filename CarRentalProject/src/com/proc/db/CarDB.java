package com.proc.db;

import com.proc.model.Car;
import com.proc.model.CarStat;
import com.proc.utility.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDB {

    private static CarDB instance;
    private final Connection connection;

    // Private constructor - Singleton pattern
    private CarDB() {
        this.connection = DBConnection.getInstance().getConnection();
    }

    // Thread-safe Singleton getInstance
    public static synchronized CarDB getInstance() {
        if (instance == null) {
            instance = new CarDB();
        }
        return instance;
    }

    // Insert Car
    public boolean insertCar(Car car) {
        String sql = "INSERT INTO CAR (registration_number, chasis_number, registration_state, " +
                     "brand, model, variant, owner_id, car_details_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, car.getRegistrationNumber());
            ps.setString(2, car.getChassisNumber());
            ps.setString(3, car.getRegistrationState());
            ps.setString(4, car.getBrand());
            ps.setString(5, car.getModel());
            ps.setString(6, car.getVariant());
            ps.setInt(7, car.getOwnerId());
            ps.setInt(8, car.getCarDetailsId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("insertCar error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Display Car info with Owner name, year of purchase, and milage
     * (JOIN across CAR, OWNER, CAR_DETAILS)
     */
    public List<Car> getCarWithOwnerAndDetails() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT c.id, c.registration_number, c.chasis_number, c.registration_state, " +
                     "c.brand, c.model, c.variant, " +
                     "o.name AS owner_name, " +
                     "cd.year_of_purchase, cd.milage " +
                     "FROM CAR c " +
                     "JOIN OWNER o ON c.owner_id = o.id " +
                     "JOIN CAR_DETAILS cd ON c.car_details_id = cd.id";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt("id"));
                car.setRegistrationNumber(rs.getString("registration_number"));
                car.setChassisNumber(rs.getString("chasis_number"));
                car.setRegistrationState(rs.getString("registration_state"));
                car.setBrand(rs.getString("brand"));
                car.setModel(rs.getString("model"));
                car.setVariant(rs.getString("variant"));
                car.setOwnerName(rs.getString("owner_name"));
                car.setYearOfPurchase(rs.getInt("year_of_purchase"));
                car.setMilage(rs.getDouble("milage"));
                cars.add(car);
            }
        } catch (SQLException e) {
            System.err.println("getCarWithOwnerAndDetails error: " + e.getMessage());
        }
        return cars;
    }

    /**
     * Display Car stats: brand and number of cars per brand
     */
    public List<CarStat> getCarStatsByBrand() {
        List<CarStat> stats = new ArrayList<>();
        String sql = "SELECT brand, COUNT(*) AS no_of_cars FROM CAR GROUP BY brand ORDER BY no_of_cars DESC";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                CarStat stat = new CarStat();
                stat.setBrand(rs.getString("brand"));
                stat.setNoOfCars(rs.getInt("no_of_cars"));
                stats.add(stat);
            }
        } catch (SQLException e) {
            System.err.println("getCarStatsByBrand error: " + e.getMessage());
        }
        return stats;
    }
}