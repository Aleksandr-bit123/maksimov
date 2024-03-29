package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.LogistEntity;
import com.diploma.maksimov.db.repository.LogistRepository;
import com.diploma.maksimov.dto.Logist;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class LogistService implements CrudService<Logist, Long, Void>{

    private final LogistRepository logistRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LogistService(LogistRepository logistRepository) {
        this.logistRepository = logistRepository;
    }

    @Override
    public Void create(Logist logist) {
        if (!logistRepository.existsById(logist.getLogistId())) {
            LogistEntity logistEntity = objectMapper.convertValue(logist, LogistEntity.class);
            logistRepository.save(logistEntity);
        }
        return null;
    }

    @Override
    public List<Logist> readAll() {
        Iterable<LogistEntity> all = logistRepository.findAll();
        return objectMapper.convertValue(all, new TypeReference<>() {
        });
    }

    @Override
    public Logist read(Long id) {
        Optional<LogistEntity> logistEntityOptional = logistRepository.findById(id);
        if (logistEntityOptional.isPresent()) {
            LogistEntity logistEntity = logistEntityOptional.get();
            return objectMapper.convertValue(logistEntity, Logist.class);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean update(Logist logist, Long id) {
        Optional<LogistEntity> logistEntityOptional = logistRepository.findById(id);
        if (logistEntityOptional.isPresent()) {
            LogistEntity logistEntity = objectMapper.convertValue(logist, LogistEntity.class);
            logistEntity.setGoals(logistEntityOptional.get().getGoals());
            logistRepository.save(logistEntity);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Optional<LogistEntity> logistEntityOptional = logistRepository.findById(id);
        if (logistEntityOptional.isPresent()) {
            logistRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
