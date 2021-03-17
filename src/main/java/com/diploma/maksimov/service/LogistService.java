package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.LogistEntity;
import com.diploma.maksimov.db.repository.LogistRepository;
import com.diploma.maksimov.dto.Logist;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogistService implements CrudService<Logist, Long>{

    private final LogistRepository logistRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LogistService(LogistRepository logistRepository) {
        this.logistRepository = logistRepository;
    }

    @Transactional
    @PostConstruct
    public void init(){

    }

    @Override
    public void create(Logist logist) {
        LogistEntity logistEntity = objectMapper.convertValue(logist, LogistEntity.class);
        logistRepository.save(logistEntity);
    }

    @Override
    public List<Logist> readAll() {
        Iterable<LogistEntity> all = logistRepository.findAll();
        return objectMapper.convertValue(all, ArrayList.class);
    }

    @Override
    public Logist read(Long id) {
        if (logistRepository.findById(id).isPresent()) {
            LogistEntity logistEntity = logistRepository.findById(id).stream().findFirst().get();
            return objectMapper.convertValue(logistEntity, Logist.class);
        }
        return null;
    }

    @Override
    public boolean update(Logist logist, Long id) {
        if (logistRepository.findById(id).isPresent()){
            logistRepository.save(objectMapper.convertValue(logist, LogistEntity.class));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (logistRepository.findById(id).isPresent()){
            LogistEntity logistEntity = logistRepository.findById(id).stream().findFirst().get();
            logistRepository.delete(logistEntity);
            return true;
        }
        return false;
    }
}
