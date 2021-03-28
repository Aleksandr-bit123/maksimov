package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.ClientEntity;
import com.diploma.maksimov.db.repository.ClientRepository;
import com.diploma.maksimov.dto.Client;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements CrudService<Client, Long, Void> {
    private final ClientRepository clientRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Override
    public Void create(Client client) {
        if (!clientRepository.existsById(client.getId())){
            ClientEntity clientEntity = objectMapper.convertValue(client, ClientEntity.class);
            clientRepository.save(clientEntity);
        }
        return null;
    }

    @Override
    public List<Client> readAll() {
        Iterable<ClientEntity> all = clientRepository.findAll();
        return objectMapper.convertValue(all, new TypeReference<>() {
        });
    }

    @Override
    public Client read(Long id) {
        Optional<ClientEntity> clientEntityOptional = clientRepository.findById(id);
        if (clientEntityOptional.isPresent()) {
            ClientEntity clientEntity = clientEntityOptional.get();
            return objectMapper.convertValue(clientEntity, Client.class);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean update(Client client, Long id) {
        Optional<ClientEntity> clientEntityOptional = clientRepository.findById(id);
        if (clientEntityOptional.isPresent()) {
            ClientEntity clientEntity = objectMapper.convertValue(client, ClientEntity.class);
            clientEntity.setOrders(clientEntityOptional.get().getOrders());
            clientEntity.getPoint().setGoals(clientEntityOptional.get().getPoint().getGoals());
            clientRepository.save(clientEntity);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Optional<ClientEntity> clientEntityOptional = clientRepository.findById(id);
        if (clientEntityOptional.isPresent()) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
