package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.GoodEntity;
import com.diploma.maksimov.db.repository.GoodRepository;
import com.diploma.maksimov.dto.Good;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoodService implements CrudService<Good, Long> {

    private final GoodRepository goodRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public GoodService(GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }

    @Override
    public void create(Good good) {
        if (!goodRepository.existsById(good.getId())) {
            GoodEntity goodEntity = objectMapper.convertValue(good, GoodEntity.class);
            goodRepository.save(goodEntity);
        }
    }

    @Override
    public List<Good> readAll() {
        Iterable<GoodEntity> all = goodRepository.findAll();
        return objectMapper.convertValue(all, new TypeReference<>() {
        });
    }

    @Override
    public Good read(Long id) {
        Optional<GoodEntity> goodEntityOptional = goodRepository.findById(id);
        if (goodEntityOptional.isPresent()) {
            GoodEntity goodEntity = goodEntityOptional.get();
            return objectMapper.convertValue(goodEntity, Good.class);
        }
        return null;
    }

    @Override
    public boolean update(Good good, Long id) {
        Optional<GoodEntity> goodEntityOptional = goodRepository.findById(id);
        if (goodEntityOptional.isPresent()) {
            GoodEntity goodEntity = objectMapper.convertValue(good, GoodEntity.class);
            goodEntity.setOrders(goodEntityOptional.get().getOrders());
            goodEntity.setGoodTurnoverList(goodEntityOptional.get().getGoodTurnoverList());
            goodRepository.save(goodEntity);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        Optional<GoodEntity> goodEntityOptional = goodRepository.findById(id);
        if (goodEntityOptional.isPresent()) {
            GoodEntity goodEntity = goodEntityOptional.get();
            goodRepository.delete(goodEntity);
            return true;
        }
        return false;
    }
}
