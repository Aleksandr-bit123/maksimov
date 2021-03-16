package com.diploma.maksimov.db.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "public", name = "t_good")
public class GoodEntity {
    @Id
    private Long id;
    private String name;
    private Double cost;
    private Double volume;
    private String info;

    @OneToMany (fetch=FetchType.EAGER, mappedBy = "good", cascade = CascadeType.MERGE)
//    @JsonManagedReference
    private List<OrderEntity> orders;

    public GoodEntity() {
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

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }
}
