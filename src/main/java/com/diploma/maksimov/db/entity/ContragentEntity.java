package com.diploma.maksimov.db.entity;

import javax.persistence.*;

@Entity
@Table(schema = "public", name = "t_contragent")
public class ContragentEntity {
    @Id
    private Long id;
    private String name;
    private String info;
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @MapsId
    private PointEntity point;

    public ContragentEntity() {
        //здесь должен быть пустой конструктор
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public PointEntity getPoint() {
        return point;
    }

    public void setPoint(PointEntity point) {
        this.point = point;
    }
}
