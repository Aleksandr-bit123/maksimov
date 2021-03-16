package com.diploma.maksimov.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(schema = "public", name = "t_boss")
public class BossEntity {
    @Id
    private Long id;
    private String info;
    @OneToOne
//    @MapsId
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private EmployeeEntity employee;

    public BossEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }
}
