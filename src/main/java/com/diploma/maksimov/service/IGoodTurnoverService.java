package com.diploma.maksimov.service;

import com.diploma.maksimov.dto.GoodTurnover;

import java.util.List;

public interface IGoodTurnoverService {
    long create(GoodTurnover goodTurnover);
    List<GoodTurnover> readAll();
    GoodTurnover read(long id);
    boolean update(GoodTurnover goodTurnover, long id);
    boolean delete(long id);
}
