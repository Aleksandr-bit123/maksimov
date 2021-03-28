package com.diploma.maksimov.dto;

public class Boss {
    private Long bossId;
    private String info;
    private Employee employee;

    public Boss() {
    }

    public Boss(Long bossId, String info, Employee employee) {
        this.bossId = bossId;
        this.info = info;
        this.employee = employee;
    }

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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
