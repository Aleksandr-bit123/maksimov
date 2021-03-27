package com.diploma.maksimov.dto;

public class Car {
    private Long id;
    private Long ownerId;
    private String brand;
    private String stateRegistrationMark;
    private Double cubicCapacity;
    private Double fuelConsumption;
    private String info;

    public Car() {
    }

    public Car(Long id, Long ownerId, String brand, String stateRegistrationMark, Double cubicCapacity, Double fuelConsumption, String info) {
        this.id = id;
        this.ownerId = ownerId;
        this.brand = brand;
        this.stateRegistrationMark = stateRegistrationMark;
        this.cubicCapacity = cubicCapacity;
        this.fuelConsumption = fuelConsumption;
        this.info = info;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getStateRegistrationMark() {
        return stateRegistrationMark;
    }

    public void setStateRegistrationMark(String stateRegistrationMark) {
        this.stateRegistrationMark = stateRegistrationMark;
    }

    public Double getCubicCapacity() {
        return cubicCapacity;
    }

    public void setCubicCapacity(Double cubicCapacity) {
        this.cubicCapacity = cubicCapacity;
    }

    public Double getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(Double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
