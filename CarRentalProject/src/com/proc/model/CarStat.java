package com.proc.model;

public class CarStat {

    private String brand;
    private int noOfCars;

    public CarStat() {}

    public CarStat(String brand, int noOfCars) {
        this.brand = brand;
        this.noOfCars = noOfCars;
    }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public int getNoOfCars() { return noOfCars; }
    public void setNoOfCars(int noOfCars) { this.noOfCars = noOfCars; }

    @Override
    public String toString() {
        return "CarStat{brand='" + brand + "', noOfCars=" + noOfCars + "}";
    }
}