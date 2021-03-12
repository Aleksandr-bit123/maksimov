package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.ContragentEntity;
import com.diploma.maksimov.db.repository.ContragentRepository;
import com.diploma.maksimov.dto.Contragent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContragentService implements IContragentService{
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ContragentRepository contragentRepository;

    public ContragentService(ContragentRepository contragentRepository) {
        this.contragentRepository = contragentRepository;
    }


    @Override
    public void create(Contragent contragent) {
        ContragentEntity contragentEntity = objectMapper.convertValue(contragent, ContragentEntity.class);
        contragentRepository.save(contragentEntity);
    }

    @Override
    public List<Contragent> readAll() {
        Iterable<ContragentEntity> all = contragentRepository.findAll();

        return objectMapper.convertValue(all, new TypeReference<List<Contragent>>() {
        });
    }

    @Override
    public Contragent read(long id) {
        Optional<ContragentEntity> contragentEntityOptional = contragentRepository.findById(id);
        if (contragentEntityOptional.isPresent()) {
            ContragentEntity contragentEntity = contragentEntityOptional.get();
            return objectMapper.convertValue(contragentEntity, Contragent.class);
        }
        return null;
    }

    @Override
    public boolean update(Contragent contragent, long id) {
        if (contragentRepository.findById(id).isPresent()) {
            contragentRepository.save(objectMapper.convertValue(contragent, ContragentEntity.class));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        Optional<ContragentEntity> contragentEntityOptional = contragentRepository.findById(id);
        if (contragentEntityOptional.isPresent()) {
            contragentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}