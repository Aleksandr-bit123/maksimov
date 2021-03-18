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
    @JoinColumn(name = "owner_id")//подтягиваем колонку owner_id из таблицы t_car и джойним id водителя
    private List<CarEntity> carEntities;

    private String driving_license;
    private String info;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "driverId")
    private List<GoalEntity> goal;


    @OneToOne(optional = true, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @MapsId
    private EmployeeEntity employee;

    public DriverEntity() {
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

    public String getDriving_license() {
        return driving_license;
    }

    public void setDriving_license(String driving_license) {
        this.driving_license = driving_license;
    }

    public List<GoalEntity> getGoal() {
        return goal;
    }

    public void setGoal(List<GoalEntity> goal) {
        this.goal = goal;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }
}
