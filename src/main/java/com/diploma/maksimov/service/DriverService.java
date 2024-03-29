package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.DriverEntity;
import com.diploma.maksimov.db.repository.DriverRepository;
import com.diploma.maksimov.dto.Driver;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService implements CrudService<Driver, Long, Void> {

    private final DriverRepository driverRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Void create(Driver driver) {
        if (!driverRepository.existsById(driver.getDriverId())) {
            DriverEntity driverEntity = objectMapper.convertValue(driver, DriverEntity.class);
            driverRepository.save(driverEntity);
        }
        return null;
    }

    @Override
    public List<Driver> readAll() {
        Iterable<DriverEntity> all = driverRepository.findAll();
        return objectMapper.convertValue(all, new TypeReference<>() {
        });
    }

    @Override
    public Driver read(Long id) {
        Optional<DriverEntity> driverEntityOptional = driverRepository.findById(id);
        if (driverEntityOptional.isPresent()) {
            DriverEntity driverEntity = driverEntityOptional.get();
            return objectMapper.convertValue(driverEntity, Driver.class);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean update(Driver driver, Long id) {
        Optional<DriverEntity> driverEntityOptional = driverRepository.findById(id);
        if (driverEntityOptional.isPresent()) {
            DriverEntity driverEntity = objectMapper.convertValue(driver, DriverEntity.class);
            driverEntity.setGoals(driverEntityOptional.get().getGoals());
            driverRepository.save(driverEntity);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Optional<DriverEntity> driverEntityOptional = driverRepository.findById(id);
        if (driverEntityOptional.isPresent()) {
            driverRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
