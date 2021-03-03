package com.diploma.maksimov.db.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "public", name = "t_driver")
public class DriverEntity {
    @Id
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)// не все вытаскиваем
    @JoinColumn(name = "owner_id")//подтягиваем колонку owner_id из таблицы t_car и джойним id водителя
    private List<CarEntity> carEntities;

    private String driving_license;
    private String info;

    public DriverEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
