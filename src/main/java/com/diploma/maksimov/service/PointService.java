package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.PointEntity;
import com.diploma.maksimov.db.repository.PointRepository;
import com.diploma.maksimov.dto.Point;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PointService implements IPointService{
    private final PointRepository pointRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    @Transactional
    @PostConstruct
    public void init(){
    }

    @Override
    public long create(Point point) {
        PointEntity pointEntity = objectMapper.convertValue(point, PointEntity.class);
        pointEntity = pointRepository.save(pointEntity);
        return pointEntity.getId();
    }

    @Override
    public List<Point> readAll() {
        Iterable<PointEntity> all = pointRepository.findAll();

        return objectMapper.convertValue(all, new TypeReference<List<Point>>() {
        });
    }

    @Override
    public Point read(long id) {
        Optional<PointEntity> pointEntityOptional = pointRepository.findById(id);
        if (pointEntityOptional.isPresent()) {
            PointEntity pointEntity = pointEntityOptional.get();
            return objectMapper.convertValue(pointEntity, Point.class);
        }
        return null;
    }

    @Override
    public boolean update(Point point, long id) {
        if (pointRepository.findById(id).isPresent()) {
            pointRepository.save(objectMapper.convertValue(point, PointEntity.class));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        Optional<PointEntity> pointEntityOptional = pointRepository.findById(id);
        if (pointEntityOptional.isPresent()) {
            pointRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
