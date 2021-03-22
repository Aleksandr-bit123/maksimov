package com.diploma.maksimov.db.entity;

import com.diploma.maksimov.dto.Goal;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(schema = "public", name = "t_goal")
public class GoalEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long pointId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate deliveryDate;
    private Long driverId;
    private Long logistId;
    private Goal.Status status;
    private String info;
    private Byte priority;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "goalId")
    private List<GoodTurnoverEntity> goodTurnoverList;

    public GoalEntity() {
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

    public Goal.Status getStatus() {
        return status;
    }

    public void setStatus(Goal.Status status) {
        this.status = status;
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

    public List<GoodTurnoverEntity> getGoodTurnoverList() {
        return goodTurnoverList;
    }

    public void setGoodTurnoverList(List<GoodTurnoverEntity> goodTurnoverList) {
        this.goodTurnoverList = goodTurnoverList;
    }
}
