package com.diploma.maksimov.dto;

public class Boss {
    private Long BossId;
    private String info;
    private Employee employee;

    public Boss() {
    }

    public Boss(Long bossId, String info, Employee employee) {
        BossId = bossId;
        this.info = info;
        this.employee = employee;
    }

    public Long getBossId() {
        return BossId;
    }

    public void setBossId(Long bossId) {
        BossId = bossId;
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
