package com.diploma.maksimov.service;

import com.diploma.maksimov.dto.Logist;

import java.util.List;

public interface ILogistService {

    void create(Logist logist);
    List<Logist> readAll();
    Logist read(long id);
    boolean update(Logist logist, long id);
    boolean delete(long id);
}
