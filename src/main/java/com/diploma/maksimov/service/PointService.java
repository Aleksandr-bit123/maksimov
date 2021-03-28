package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.PointEntity;
import com.diploma.maksimov.db.repository.PointRepository;
import com.diploma.maksimov.dto.Point;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PointService implements CrudService<Point,Long,Long>{
    private final PointRepository pointRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    @Override
    public Long create(Point point) {
        PointEntity pointEntity = objectMapper.convertValue(point, PointEntity.class);
        pointEntity = pointRepository.save(pointEntity);
        return pointEntity.getId();
    }

    @Override
    public List<Point> readAll() {
        Iterable<PointEntity> all = pointRepository.findAll();

        return objectMapper.convertValue(all, new TypeReference<>() {
        });
    }

    @Override
    public Point read(Long id) {
        Optional<PointEntity> pointEntityOptional = pointRepository.findById(id);
        if (pointEntityOptional.isPresent()) {
            PointEntity pointEntity = pointEntityOptional.get();
            return objectMapper.convertValue(pointEntity, Point.class);
        }
        return null;
    }

    @Override
    public boolean update(Point point, Long id) {
        if (pointRepository.findById(id).isPresent()) {
            pointRepository.save(objectMapper.convertValue(point, PointEntity.class));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        Optional<PointEntity> pointEntityOptional = pointRepository.findById(id);
        if (pointEntityOptional.isPresent()) {
            pointRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
