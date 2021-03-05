package com.diploma.maksimov.service;

import com.diploma.maksimov.dto.Driver;

import java.util.List;

public interface IDriverService {

    void create(Driver driver);
    List<Driver> readAll();
    Driver read(long id);
    boolean update(Driver driver, long id);
    boolean delete(long id);
}
