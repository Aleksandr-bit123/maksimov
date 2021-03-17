package com.diploma.maksimov.db.entity;

import com.diploma.maksimov.dto.GoodTurnover;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class GoodTurnoverEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long goalId;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goodId")
    private GoodEntity good;
    private Byte quantity;
    private GoodTurnover.PaymentMethod paymentMethod;
    private String info;

    public GoodTurnoverEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public GoodEntity getGood() {
        return good;
    }

    public void setGood(GoodEntity good) {
        this.good = good;
    }

    public Byte getQuantity() {
        return quantity;
    }

    public void setQuantity(Byte quantity) {
        this.quantity = quantity;
    }

    public GoodTurnover.PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(GoodTurnover.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
