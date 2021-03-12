package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.ClientEntity;
import com.diploma.maksimov.db.repository.ClientRepository;
import com.diploma.maksimov.dto.Client;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements IClientService{
    private final ClientRepository clientRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Override
    public void create(Client client) {
        ClientEntity clientEntity = objectMapper.convertValue(client, ClientEntity.class);
        clientRepository.save(clientEntity);
    }

    @Override
    public List<Client> readAll() {
        Iterable<ClientEntity> all = clientRepository.findAll();

        return objectMapper.convertValue(all, new TypeReference<List<Client>>() {
        });
    }

    @Override
    public Client read(long id) {
        Optional<ClientEntity> clientEntityOptional = clientRepository.findById(id);
        if (clientEntityOptional.isPresent()) {
            ClientEntity clientEntity = clientEntityOptional.get();
            return objectMapper.convertValue(clientEntity, Client.class);
        }
        return null;
    }

    @Override
    public boolean update(Client client, long id) {
        if (clientRepository.findById(id).isPresent()) {
            clientRepository.save(objectMapper.convertValue(client, ClientEntity.class));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        Optional<ClientEntity> clientEntityOptional = clientRepository.findById(id);
        if (clientEntityOptional.isPresent()) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }
}