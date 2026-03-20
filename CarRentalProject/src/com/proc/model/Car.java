package com.proc.model;

public class Car {

    private int id;
    private String registrationNumber;
    private String chassisNumber;
    private String registrationState;
    private String brand;
    private String model;
    private String variant;
    private int ownerId;
    private int carDetailsId;

    // For joined display
    private String ownerName;
    private int yearOfPurchase;
    private double milage;

    public Car() {}

    public Car(int id, String registrationNumber, String chassisNumber,
               String registrationState, String brand, String model,
               String variant, int ownerId, int carDetailsId) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.chassisNumber = chassisNumber;
        this.registrationState = registrationState;
        this.brand = brand;
        this.model = model;
        this.variant = variant;
        this.ownerId = ownerId;
        this.carDetailsId = carDetailsId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public String getChassisNumber() { return chassisNumber; }
    public void setChassisNumber(String chassisNumber) { this.chassisNumber = chassisNumber; }

    public String getRegistrationState() { return registrationState; }
    public void setRegistrationState(String registrationState) { this.registrationState = registrationState; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getVariant() { return variant; }
    public void setVariant(String variant) { this.variant = variant; }

    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }

    public int getCarDetailsId() { return carDetailsId; }
    public void setCarDetailsId(int carDetailsId) { this.carDetailsId = carDetailsId; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public int getYearOfPurchase() { return yearOfPurchase; }
    public void setYearOfPurchase(int yearOfPurchase) { this.yearOfPurchase = yearOfPurchase; }

    public double getMilage() { return milage; }
    public void setMilage(double milage) { this.milage = milage; }

    @Override
    public String toString() {
        return "Car{id=" + id +
               ", regNo='" + registrationNumber + "'" +
               ", brand='" + brand + "'" +
               ", model='" + model + "'" +
               ", variant='" + variant + "'" +
               ", owner='" + ownerName + "'" +
               ", yearOfPurchase=" + yearOfPurchase +
               ", milage=" + milage + "}";
    }
}