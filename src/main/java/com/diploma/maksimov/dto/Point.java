package com.diploma.maksimov.dto;

public class Point {
    public enum PointType {client, contragent}

    private Long id;
    private String address;
    private String phone;
    private PointType pointType;

    public Point() {
    }

    public Point(Long id, String address, String phone, PointType pointType) {
        this.id = id;
        this.address = address;
        this.phone = phone;
        this.pointType = pointType;
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

    public PointType getPointType() {
        return pointType;
    }

    public void setPointType(PointType pointType) {
        this.pointType = pointType;
    }
}
