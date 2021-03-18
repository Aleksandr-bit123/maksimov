package com.diploma.maksimov.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.List;

public class Goal {
    public enum Status {waiting, doing, completed, canceled}

    private Long id;
    private Long pointId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate deliveryDate;
    private Long driverId;
    private Long logistId;
    private Status status;
    private List<GoodTurnover> goodTurnoverList;

    public Goal() {
    }

    public Goal(Long id, Long pointId, LocalDate deliveryDate, Long driverId, Long logistId, Status status, List<GoodTurnover> goodTurnoverList) {
        this.id = id;
        this.pointId = pointId;
        this.deliveryDate = deliveryDate;
        this.driverId = driverId;
        this.logistId = logistId;
        this.status = status;
        this.goodTurnoverList = goodTurnoverList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPointId() {
        return pointId;
    }

    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Long getLogistId() {
        return logistId;
    }

    public void setLogistId(Long logistId) {
        this.logistId = logistId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<GoodTurnover> getGoodTurnoverList() {
        return goodTurnoverList;
    }

    public void setGoodTurnoverList(List<GoodTurnover> goodTurnoverList) {
        this.goodTurnoverList = goodTurnoverList;
    }
}
