package com.proc.model;

public class CarDetails {

    private int id;
    private int yearOfPurchase;
    private double milage;

    public CarDetails() {}

    public CarDetails(int id, int yearOfPurchase, double milage) {
        this.id = id;
        this.yearOfPurchase = yearOfPurchase;
        this.milage = milage;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getYearOfPurchase() { return yearOfPurchase; }
    public void setYearOfPurchase(int yearOfPurchase) { this.yearOfPurchase = yearOfPurchase; }

    public double getMilage() { return milage; }
    public void setMilage(double milage) { this.milage = milage; }

    @Override
    public String toString() {
        return "CarDetails{id=" + id + ", yearOfPurchase=" + yearOfPurchase + ", milage=" + milage + "}";
    }
}