package com.diploma.maksimov.dto;

public class Contragent {
    private Long id;
    private String name;
    private String info;
    private Point point;

    public Contragent() {
    }

    public Contragent(Long id, String name, String info, Point point) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.point = point;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
