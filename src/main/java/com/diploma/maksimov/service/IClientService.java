package com.diploma.maksimov.service;

import com.diploma.maksimov.dto.Client;

import java.util.List;

public interface IClientService {
    void create(Client client);
    List<Client> readAll();
    Client read(long id);
    boolean update(Client client, long id);
    boolean delete(long id);
}
