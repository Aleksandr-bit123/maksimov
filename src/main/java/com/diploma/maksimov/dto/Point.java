package com.diploma.maksimov.dto;

public class Point {
    private Long id;
    private String address;
    private String phone;

    public Point() {
    }

    public Point(Long id, String address, String phone) {
        this.id = id;
        this.address = address;
        this.phone = phone;
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

}
