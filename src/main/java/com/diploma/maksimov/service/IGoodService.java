package com.diploma.maksimov.service;

import com.diploma.maksimov.dto.Good;

import java.util.List;

public interface IGoodService {
    void create(Good good);
    List<Good> readAll();
    Good read(long id);
    boolean update(Good good, long id);
    boolean delete(long id);
}
