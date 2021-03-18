package com.diploma.maksimov.dto;

public class Logist {
    private Long logistId;
    private String info;
    private Employee employee;

    public Logist() {
    }

    public Logist(Long logistId, String info, Employee employee) {
        this.logistId = logistId;
        this.info = info;
        this.employee = employee;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
