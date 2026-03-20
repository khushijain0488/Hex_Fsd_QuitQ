package com.proc.main;

import com.proc.db.CarDB;
import com.proc.db.CarDetailsDB;
import com.proc.db.OwnerDB;
import com.proc.model.Car;
import com.proc.model.CarDetails;
import com.proc.model.Owner;

public class App {

    public static void main(String[] args) {

        OwnerDB ownerDB           = OwnerDB.getInstance();
        CarDetailsDB carDetailsDB = CarDetailsDB.getInstance();
        CarDB carDB               = CarDB.getInstance();

        // --- Insert One Owner ---
        Owner owner = new Owner();
        owner.setName("Karan Johari");
        owner.setAddress("45, Vaishali Nagar, Jaipur");
        boolean ownerInserted = ownerDB.insertOwner(owner);
        System.out.println("Owner inserted: " + ownerInserted);

        // --- Insert One CarDetails ---
        CarDetails carDetails = new CarDetails();
        carDetails.setYearOfPurchase(2023);
        carDetails.setMilage(12000.0);
        boolean detailsInserted = carDetailsDB.insertCarDetails(carDetails);
        System.out.println("CarDetails inserted: " + detailsInserted);

        // --- Insert One Car ---
        Car car = new Car();
        car.setRegistrationNumber("MH12XY7777");
        car.setChassisNumber("CHKJ20230001");
        car.setRegistrationState("Maharashtra");
        car.setBrand("Honda");
        car.setModel("City");
        car.setVariant("ZX CVT");
        car.setOwnerId(6);        // Karan Johari
        car.setCarDetailsId(7);   // year 2023, milage 12000
        boolean carInserted = carDB.insertCar(car);
        System.out.println("Car inserted: " + carInserted);

        System.out.println("\n--- Launching CarMain display ---\n");
        CarMain.main(args);
    }
}