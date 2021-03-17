package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.GoodTurnoverEntity;
import com.diploma.maksimov.db.repository.GoodTurnoverRepository;
import com.diploma.maksimov.dto.GoodTurnover;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoodTurnoverService implements IGoodTurnoverService{
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final GoodTurnoverRepository goodTurnoverRepository;

    public GoodTurnoverService(GoodTurnoverRepository goodTurnoverRepository) {
        this.goodTurnoverRepository = goodTurnoverRepository;
    }


    @Override
    public long create(GoodTurnover goodTurnover) {
        GoodTurnoverEntity goodTurnoverEntity = objectMapper.convertValue(goodTurnover, GoodTurnoverEntity.class);
        goodTurnoverEntity = goodTurnoverRepository.save(goodTurnoverEntity);
        return goodTurnoverEntity.getId();
    }

    @Override
    public List<GoodTurnover> readAll() {
        Iterable<GoodTurnoverEntity> all = goodTurnoverRepository.findAll();

        return objectMapper.convertValue(all, new TypeReference<List<GoodTurnover>>() {
        });
    }

    @Override
    public GoodTurnover read(long id) {
        Optional<GoodTurnoverEntity> goodTurnoverEntityOptional = goodTurnoverRepository.findById(id);
        if (goodTurnoverEntityOptional.isPresent()) {
            GoodTurnoverEntity goodTurnoverEntity = goodTurnoverEntityOptional.get();
            return objectMapper.convertValue(goodTurnoverEntity, GoodTurnover.class);
        }
        return null;
    }

    @Override
    public boolean update(GoodTurnover goodTurnover, long id) {
        if (goodTurnoverRepository.findById(id).isPresent()) {
            goodTurnoverRepository.save(objectMapper.convertValue(goodTurnover, GoodTurnoverEntity.class));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        Optional<GoodTurnoverEntity> goodTurnoverEntityOptional = goodTurnoverRepository.findById(id);
        if (goodTurnoverEntityOptional.isPresent()) {
            goodTurnoverRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
