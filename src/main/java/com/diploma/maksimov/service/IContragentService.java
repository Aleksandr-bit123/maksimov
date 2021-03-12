package com.diploma.maksimov.service;

import com.diploma.maksimov.dto.Contragent;

import java.util.List;

public interface IContragentService {
    void create(Contragent contragent);
    List<Contragent> readAll();
    Contragent read(long id);
    boolean update(Contragent contragent, long id);
    boolean delete(long id);
}
