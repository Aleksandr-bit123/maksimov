package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.DriverEntity;
import com.diploma.maksimov.db.repository.DriverRepository;
import com.diploma.maksimov.dto.Driver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class DriverService implements IDriverService{

    private final DriverRepository driverRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Transactional
    @PostConstruct
    public void init(){

    }

    @Override
    public void create(Driver driver) {
        DriverEntity driverEntity = objectMapper.convertValue(driver, DriverEntity.class);
        driverRepository.save(driverEntity);
    }

    @Override
    public List<Driver> readAll() {
        Iterable<DriverEntity> all = driverRepository.findAll();
        return objectMapper.convertValue(all, ArrayList.class);
    }

    @Override
    public Driver read(long id) {
        if (driverRepository.findById(id).isPresent()) {
            DriverEntity driverEntity = driverRepository.findById(id).stream().findFirst().get();
            return objectMapper.convertValue(driverEntity, Driver.class);
        }
        return null;
    }

    @Override
    public boolean update(Driver driver, long id) {
        if (driverRepository.findById(id).isPresent()){
            driverRepository.save(objectMapper.convertValue(driver, DriverEntity.class));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        if (driverRepository.findById(id).isPresent()){
            DriverEntity driverEntity = driverRepository.findById(id).stream().findFirst().get();
            driverRepository.delete(driverEntity);
            return true;
        }
        return false;
    }
}
