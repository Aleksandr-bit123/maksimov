package com.diploma.maksimov.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "public", name = "t_logist")
public class LogistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long logistId;
    private String info;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "logistId")
    private List<GoalEntity> goals;

    @OneToOne(optional = true, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @MapsId
    private EmployeeEntity employee;

    public LogistEntity() {
        //здесь должен быть пустой конструктор
    }

    public Long getLogistId() {
        return logistId;
    }

    public void setLogistId(Long logistId) {
        this.logistId = logistId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
