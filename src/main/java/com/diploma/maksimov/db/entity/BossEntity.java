package com.diploma.maksimov.db.entity;

import javax.persistence.*;

@Entity
@Table(schema = "public", name = "t_boss")
public class BossEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bossId;
    private String info;

    public BossEntity() {
    }

    @OneToOne(optional = true, cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @MapsId
    private EmployeeEntity employee;

    public Long getBossId() {
        return bossId;
    }

    public void setBossId(Long bossId) {
        this.bossId = bossId;
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
