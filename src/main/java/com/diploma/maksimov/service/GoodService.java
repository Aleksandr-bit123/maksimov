package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.GoodEntity;
import com.diploma.maksimov.db.repository.GoodRepository;
import com.diploma.maksimov.dto.Good;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodService implements IGoodService{

    private final GoodRepository goodRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public GoodService(GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }

    @Transactional
    @PostConstruct
    public void init(){

    }

    @Override
    public void create(Good good) {
        GoodEntity goodEntity = objectMapper.convertValue(good, GoodEntity.class);
        goodRepository.save(goodEntity);
    }

    @Override
    public List<Good> readAll() {
        Iterable<GoodEntity> all = goodRepository.findAll();
        return objectMapper.convertValue(all, ArrayList.class);
    }

    @Override
    public Good read(long id) {
        if (goodRepository.findById(id).isPresent()) {
            GoodEntity goodEntity = goodRepository.findById(id).stream().findFirst().get();
            return objectMapper.convertValue(goodEntity, Good.class);
        }
        return null;
    }

    @Override
    public boolean update(Good good, long id) {
        if (goodRepository.findById(id).isPresent()){
            goodRepository.save(objectMapper.convertValue(good, GoodEntity.class));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        if (goodRepository.findById(id).isPresent()){
            GoodEntity goodEntity = goodRepository.findById(id).stream().findFirst().get();
            goodRepository.delete(goodEntity);
            return true;
        }
        return false;
    }
}