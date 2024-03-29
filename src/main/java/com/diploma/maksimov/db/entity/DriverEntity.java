package com.diploma.maksimov.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "public", name = "t_driver")
public class DriverEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long driverId;

    @OneToMany(fetch = FetchType.LAZY)// не все вытаскиваем
    @JoinColumn(name = "ownerId")//подтягиваем колонку owner_id из таблицы t_car и джойним id водителя
    private List<CarEntity> carEntities;

    private String drivingLicense;
    private String info;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "driverId")
    private List<GoalEntity> goals;


    @OneToOne(optional = true, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @MapsId
    private EmployeeEntity employee;

    public DriverEntity() {
        //здесь должен быть пустой конструктор
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public List<CarEntity> getCarEntities() {
        return carEntities;
    }

    public void setCarEntities(List<CarEntity> carEntities) {
        this.carEntities = carEntities;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public List<GoalEntity> getGoals() {
        return goals;
    }

    public void setGoals(List<GoalEntity> goals) {
        this.goals = goals;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }
}
