package com.diploma.maksimov.db.entity;

import com.diploma.maksimov.dto.Point;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "public", name = "t_point")
public class PointEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String address;
    private String phone;
    private Point.PointType pointType;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pointId")
    private List<GoalEntity> goals;

    public PointEntity() {
        //здесь должен быть пустой конструктор
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<GoalEntity> getGoals() {
        return goals;
    }

    public void setGoals(List<GoalEntity> goals) {
        this.goals = goals;
    }

    public Point.PointType getPointType() {
        return pointType;
    }

    public void setPointType(Point.PointType pointType) {
        this.pointType = pointType;
    }
}
