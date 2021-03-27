package com.diploma.maksimov.db.entity;

import javax.persistence.*;

@Entity
@Table(schema = "public", name = "t_car")
public class CarEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long ownerId;
    private String brand;
    private String stateRegistrationMark;
    private Double cubicCapacity;
    private Double fuelConsumption;
    private String info;

    public CarEntity() {
        //здесь должен быть пустой конструктор
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
