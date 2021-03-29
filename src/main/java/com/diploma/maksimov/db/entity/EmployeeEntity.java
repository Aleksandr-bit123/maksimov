package com.diploma.maksimov.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(schema = "public", name = "t_employee")
public class EmployeeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID;

    static {
        serialVersionUID = 2405172041950251807L;
    }

    @Id
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

    private Boolean isBoss;
    private Boolean isLogist;
    private Boolean isDriver;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity user;


    public EmployeeEntity() {
        //здесь должен быть пустой конструктор
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

    public Boolean getBoss() {
        return isBoss;
    }

    public void setBoss(Boolean boss) {
        isBoss = boss;
    }

    public Boolean getLogist() {
        return isLogist;
    }

    public void setLogist(Boolean logist) {
        isLogist = logist;
    }

    public Boolean getDriver() {
        return isDriver;
    }

    public void setDriver(Boolean driver) {
        isDriver = driver;
    }

}
