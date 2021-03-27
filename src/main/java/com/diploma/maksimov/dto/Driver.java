package com.diploma.maksimov.dto;

import java.util.List;

public class Driver {
    private Long driverId;
    private List<Car> carEntities;
    private String drivingLicense;
    private String info;
    private Employee employee;

    public Driver() {
    }

    public Driver(Long driverId, List<Car> carEntities, String drivingLicense, String info, Employee employee) {
        this.driverId = driverId;
        this.carEntities = carEntities;
        this.drivingLicense = drivingLicense;
        this.info = info;
        this.employee = employee;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public List<Car> getCarEntities() {
        return carEntities;
    }

    public void setCarEntities(List<Car> carEntities) {
        this.carEntities = carEntities;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
