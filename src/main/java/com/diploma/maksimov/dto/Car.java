package com.diploma.maksimov.dto;

public class Car {
    private Long id;
    private Long owner_id;
    private String brand;
    private String state_registration_mark;
    private Double cubic_capacity;
    private Double fuel_consumption;
    private String info;

    public Car() {
    }

    public Car(Long id, Long owner_id, String brand, String state_registration_mark, Double cubic_capacity, Double fuel_consumption, String info) {

        this.id = id;
        this.owner_id = owner_id;
        this.brand = brand;
        this.state_registration_mark = state_registration_mark;
        this.cubic_capacity = cubic_capacity;
        this.fuel_consumption = fuel_consumption;
        this.info = info;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Long owner_id) {
        this.owner_id = owner_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getState_registration_mark() {
        return state_registration_mark;
    }

    public void setState_registration_mark(String state_registration_mark) {
        this.state_registration_mark = state_registration_mark;
    }

    public Double getCubic_capacity() {
        return cubic_capacity;
    }

    public void setCubic_capacity(Double cubic_capacity) {
        this.cubic_capacity = cubic_capacity;
    }

    public Double getFuel_consumption() {
        return fuel_consumption;
    }

    public void setFuel_consumption(Double fuel_consumption) {
        this.fuel_consumption = fuel_consumption;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}