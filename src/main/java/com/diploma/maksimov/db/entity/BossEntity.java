package com.diploma.maksimov.db.entity;

import javax.persistence.*;

@Entity
@Table(schema = "public", name = "t_boss")
public class BossEntity {
    @Id
    private Long id;
    private String info;

    public BossEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
