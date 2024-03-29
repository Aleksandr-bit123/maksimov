package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.CarEntity;
import com.diploma.maksimov.db.repository.CarRepository;
import com.diploma.maksimov.dto.Car;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CarService implements CrudService<Car,Long,Long> {

    private final CarRepository carRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Long create(Car car) {
        CarEntity carEntity = objectMapper.convertValue(car, CarEntity.class);
        carEntity = carRepository.save(carEntity);
        return carEntity.getId();
    }

    @Override
    public List<Car> readAll() {
        Iterable<CarEntity> all = carRepository.findAll();
        return objectMapper.convertValue(all, new TypeReference<>() {
        });
    }

    @Override
    public Car read(Long id) {
        Optional<CarEntity> carEntityOptional = carRepository.findById(id);
        if (carEntityOptional.isPresent()) {
            CarEntity carEntity = carEntityOptional.get();
            return objectMapper.convertValue(carEntity, Car.class);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean update(Car car, Long id) {
        if (carRepository.findById(id).isPresent()) {
            carRepository.save(objectMapper.convertValue(car, CarEntity.class));
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Optional<CarEntity> carEntityOptional = carRepository.findById(id);
        if (carEntityOptional.isPresent()) {
            carRepository.deleteById(id);
            return true;
        }
        return false;
    }
}