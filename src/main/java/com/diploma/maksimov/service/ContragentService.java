package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.ContragentEntity;
import com.diploma.maksimov.db.repository.ContragentRepository;
import com.diploma.maksimov.dto.Contragent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ContragentService implements CrudService<Contragent, Long, Void>{
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ContragentRepository contragentRepository;

    public ContragentService(ContragentRepository contragentRepository) {
        this.contragentRepository = contragentRepository;
    }


    @Override
    public Void create(Contragent contragent) {
        if (!contragentRepository.existsById(contragent.getId())){
            ContragentEntity contragentEntity = objectMapper.convertValue(contragent, ContragentEntity.class);
            contragentRepository.save(contragentEntity);
        }
        return null;
    }

    @Override
    public List<Contragent> readAll() {
        Iterable<ContragentEntity> all = contragentRepository.findAll();

        return objectMapper.convertValue(all, new TypeReference<>() {
        });
    }

    @Override
    public Contragent read(Long id) {
        Optional<ContragentEntity> contragentEntityOptional = contragentRepository.findById(id);
        if (contragentEntityOptional.isPresent()) {
            ContragentEntity contragentEntity = contragentEntityOptional.get();
            return objectMapper.convertValue(contragentEntity, Contragent.class);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean update(Contragent contragent, Long id) {
        Optional<ContragentEntity> contragentEntityOptional = contragentRepository.findById(id);
        if (contragentEntityOptional.isPresent()) {
            ContragentEntity contragentEntity = objectMapper.convertValue(contragent, ContragentEntity.class);
            contragentEntity.getPoint().setGoals(contragentEntityOptional.get().getPoint().getGoals());
            contragentRepository.save(contragentEntity);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Optional<ContragentEntity> contragentEntityOptional = contragentRepository.findById(id);
        if (contragentEntityOptional.isPresent()) {
            contragentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
