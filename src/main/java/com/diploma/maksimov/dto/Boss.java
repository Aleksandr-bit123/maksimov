package com.diploma.maksimov.dto;

import com.diploma.maksimov.db.entity.EmployeeEntity;

public class Boss {
    private Long id;
    private String info;
    private EmployeeEntity employee;

    public Boss() {
    }

    public Boss(Long id, String info) {
        this.id = id;
        this.info = info;
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
