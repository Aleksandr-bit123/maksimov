package com.diploma.maksimov.dto;

public class Logist {
    private Long id;
    private String info;

    public Logist() {
    }

    public Logist(Long id, String info) {
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
}
