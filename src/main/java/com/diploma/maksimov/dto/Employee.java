package com.diploma.maksimov.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

public class Employee {
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

    private Boss bossEntity;
    private Logist logistEntity;
    private Driver driverEntity;

    public Employee() {
    }

    public Employee(Long id, String lastName, String firstName, String middleName, LocalDate birthdate, String passport, String phone, Boss bossEntity, Logist logistEntity, Driver driverEntity) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthdate = birthdate;
        this.passport = passport;
        this.phone = phone;
        this.bossEntity = bossEntity;
        this.logistEntity = logistEntity;
        this.driverEntity = driverEntity;
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

    public Boss getBossEntity() {
        return bossEntity;
    }

    public void setBossEntity(Boss bossEntity) {
        this.bossEntity = bossEntity;
    }

    public Logist getLogistEntity() {
        return logistEntity;
    }

    public void setLogistEntity(Logist logistEntity) {
        this.logistEntity = logistEntity;
    }

    public Driver getDriverEntity() {
        return driverEntity;
    }

    public void setDriverEntity(Driver driverEntity) {
        this.driverEntity = driverEntity;
    }
}
