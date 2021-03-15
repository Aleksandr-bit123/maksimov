package com.diploma.maksimov.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(schema = "public", name = "t_order")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

//    private Long clientId;
//    private Long goodId;

    @ManyToOne (fetch = FetchType.EAGER, optional = true/*,cascade = CascadeType.ALL*/)
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @ManyToOne (fetch = FetchType.EAGER, optional = true/*,cascade = CascadeType.ALL*/)
    @JoinColumn(name = "good_id")
    private GoodEntity good;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate deliveryDate;

    private Byte quantity;
    private String info;

    public OrderEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getClientId() {
//        return clientId;
//    }
//
//    public void setClientId(Long clientId) {
//        this.clientId = clientId;
//    }
//
//    public Long getGoodId() {
//        return goodId;
//    }
//
//    public void setGoodId(Long goodId) {
//        this.goodId = goodId;
//    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public GoodEntity getGood() {
        return good;
    }

    public void setGood(GoodEntity good) {
        this.good = good;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Byte getQuantity() {
        return quantity;
    }

    public void setQuantity(Byte quantity) {
        this.quantity = quantity;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
