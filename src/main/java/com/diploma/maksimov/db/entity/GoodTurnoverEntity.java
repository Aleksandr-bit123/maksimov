package com.diploma.maksimov.db.entity;

import com.diploma.maksimov.dto.GoodTurnover;

import javax.persistence.*;

@Entity
@Table(schema = "public", name = "t_good_turnover")
public class GoodTurnoverEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long goalId;
    private Long goodId;
    private Byte quantity;
    private GoodTurnover.PaymentMethod paymentMethod;
    private String info;
    private Byte priority;
    private Boolean turnover;

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

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
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

    public Byte getPriority() {
        return priority;
    }

    public void setPriority(Byte priority) {
        this.priority = priority;
    }

    public Boolean getTurnover() {
        return turnover;
    }

    public void setTurnover(Boolean turnover) {
        this.turnover = turnover;
    }
}
