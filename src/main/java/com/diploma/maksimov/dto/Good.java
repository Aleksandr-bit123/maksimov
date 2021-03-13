package com.diploma.maksimov.dto;

import java.util.List;

public class Good {
    private Long id;
    private String name;
    private Double cost;
    private Double volume;
    private String info;
    private List<Order> orders;

    public Good() {
    }

    public Good(Long id, String name, Double cost, Double volume, String info, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.volume = volume;
        this.info = info;
        this.orders = orders;
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

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}


