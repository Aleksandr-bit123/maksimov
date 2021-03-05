package com.diploma.maksimov.dto;

import java.util.List;

public class Driver {
    private Long id;
    private List<Car> carEntities;
    private String driving_license;
    private String info;

    public Driver() {
    }

    public Driver(Long id, List<Car> carEntities, String driving_license, String info) {
        this.id = id;
        this.carEntities = carEntities;
        this.driving_license = driving_license;
        this.info = info;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
