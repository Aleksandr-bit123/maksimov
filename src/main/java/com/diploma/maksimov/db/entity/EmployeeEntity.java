package com.diploma.maksimov.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(schema = "public", name = "t_employee")
public class EmployeeEntity {
    @Id
//    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthdate;

    private String passport;
    private String phone;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<PositionEntity> positions;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private BossEntity bossEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private DriverEntity driverEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private LogistEntity logistEntity;

    public EmployeeEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BossEntity getBossEntity() {
        return bossEntity;
    }

    public void setBossEntity(BossEntity bossEntity) {
        this.bossEntity = bossEntity;
    }

    public DriverEntity getDriverEntity() {
        return driverEntity;
    }

    public void setDriverEntity(DriverEntity driverEntity) {
        this.driverEntity = driverEntity;
    }

    public LogistEntity getLogistEntity() {
        return logistEntity;
    }

    public void setLogistEntity(LogistEntity logistEntity) {
        this.logistEntity = logistEntity;
    }

    public Set<PositionEntity> getPositions() {
        return positions;
    }

    public void setPositions(Set<PositionEntity> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        return "EmployeeEntity{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", birthdate=" + birthdate +
                ", passport='" + passport + '\'' +
                ", phone='" + phone + '\'' +
                ", positions=" + positions +
                ", bossEntity=" + bossEntity +
                ", driverEntity=" + driverEntity +
                ", logistEntity=" + logistEntity +
                '}';
    }
}
