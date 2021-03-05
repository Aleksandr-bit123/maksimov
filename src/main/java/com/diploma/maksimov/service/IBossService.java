package com.diploma.maksimov.service;

import com.diploma.maksimov.dto.Boss;

import java.util.List;

public interface IBossService {
    void create(Boss boss);
    List<Boss> readAll();
    Boss read(long id);
    boolean update(Boss boss, long id);
    boolean delete(long id);
}
