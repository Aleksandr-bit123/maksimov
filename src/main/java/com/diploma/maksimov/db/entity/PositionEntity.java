package com.diploma.maksimov.db.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_position")
public class PositionEntity {
    @Id
    private Long id;
    private String name;

    @Transient
    @ManyToMany(mappedBy = "positions")
    private Set<EmployeeEntity> employees;

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

    public Set<EmployeeEntity> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeEntity> employees) {
        this.employees = employees;
    }
}
