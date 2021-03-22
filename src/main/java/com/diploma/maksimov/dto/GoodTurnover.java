package com.diploma.maksimov.dto;

public class GoodTurnover {
    public enum PaymentMethod {cash, cashless, paidFor}

    private Long id;
    private Long goalId;
    private Long goodId;
    private Byte quantity;
    private PaymentMethod paymentMethod;
    private String info;
    private Byte priority;
    private Boolean turnover;
    private Long orderId;
    private Long link;
    private Long linkPoint;
    private Long linkGoal;

    public GoodTurnover() {
    }

    public GoodTurnover(Long id, Long goalId, Long goodId, Byte quantity, PaymentMethod paymentMethod, String info, Byte priority, Boolean turnover, Long orderId) {
        this.id = id;
        this.goalId = goalId;
        this.goodId = goodId;
        this.quantity = quantity;
        this.paymentMethod = paymentMethod;
        this.info = info;
        this.priority = priority;
        this.turnover = turnover;
        this.orderId = orderId;

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

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getLink() {
        return link;
    }

    public void setLink(Long link) {
        this.link = link;
    }

    public Long getLinkPoint() {
        return linkPoint;
    }

    public void setLinkPoint(Long linkPoint) {
        this.linkPoint = linkPoint;
    }

    public Long getLinkGoal() {
        return linkGoal;
    }

    public void setLinkGoal(Long linkGoal) {
        this.linkGoal = linkGoal;
    }
}
