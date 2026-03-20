package com.proc.main;

import com.proc.db.CarDB;
import com.proc.model.Car;
import com.proc.model.CarStat;

import java.util.List;

public class CarMain {

    public static void main(String[] args) {

        CarDB carDB = CarDB.getInstance();  // Singleton

        // --- Display 1: Car info with Owner name, Year of Purchase, Milage ---
        System.out.println("==============================================");
        System.out.println("       CAR INFO WITH OWNER & DETAILS         ");
        System.out.println("==============================================");
        System.out.printf("%-5s %-15s %-12s %-20s %-10s %-8s %-12s%n",
                "ID", "Reg No", "Brand", "Owner", "Year", "Milage", "State");
        System.out.println("----------------------------------------------" +
                           "----------------------------------------------");

        List<Car> cars = carDB.getCarWithOwnerAndDetails();
        if (cars.isEmpty()) {
            System.out.println("No records found.");
        } else {
            for (Car car : cars) {
                System.out.printf("%-5d %-15s %-12s %-20s %-10d %-8.1f %-12s%n",
                        car.getId(),
                        car.getRegistrationNumber(),
                        car.getBrand(),
                        car.getOwnerName(),
                        car.getYearOfPurchase(),
                        car.getMilage(),
                        car.getRegistrationState());
            }
        }

        System.out.println();

        // --- Display 2: Car Stats by Brand ---
        System.out.println("==============================================");
        System.out.println("           CAR STATS BY BRAND                ");
        System.out.println("==============================================");
        System.out.printf("%-25s %-12s%n", "Brand", "No of Cars");
        System.out.println("----------------------------------------------");

        List<CarStat> stats = carDB.getCarStatsByBrand();
        if (stats.isEmpty()) {
            System.out.println("No stats available.");
        } else {
            for (CarStat stat : stats) {
                System.out.printf("%-25s %-12d%n",
                        stat.getBrand(),
                        stat.getNoOfCars());
            }
        }

        System.out.println("==============================================");
    }
}