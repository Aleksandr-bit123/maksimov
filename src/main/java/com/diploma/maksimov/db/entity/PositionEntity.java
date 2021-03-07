package com.diploma.maksimov.db.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_position")
public class PositionEntity {
    @Id
    private Long id;
    private String name;
    private Long roleId;

    public PositionEntity() {
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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
