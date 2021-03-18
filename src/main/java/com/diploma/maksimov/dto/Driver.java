package com.diploma.maksimov.dto;

import java.util.List;

public class Driver {
    private Long DriverId;
    private List<Car> carEntities;
    private String driving_license;
    private String info;
    private Employee employee;

    public Driver() {
    }

    public Driver(Long driverId, List<Car> carEntities, String driving_license, String info, Employee employee) {
        DriverId = driverId;
        this.carEntities = carEntities;
        this.driving_license = driving_license;
        this.info = info;
        this.employee = employee;
    }

    public Long getDriverId() {
        return DriverId;
    }

    public void setDriverId(Long driverId) {
        DriverId = driverId;
    }

    public List<Car> getCarEntities() {
        return carEntities;
    }

    public void setCarEntities(List<Car> carEntities) {
        this.carEntities = carEntities;
    }

    public String getDriving_license() {
        return driving_license;
    }

    public void setDriving_license(String driving_license) {
        this.driving_license = driving_license;
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
