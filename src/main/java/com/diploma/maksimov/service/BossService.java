package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.BossEntity;
import com.diploma.maksimov.db.repository.BossRepository;
import com.diploma.maksimov.dto.Boss;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BossService implements IBossService{
    private final BossRepository bossRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BossService(BossRepository bossRepository) {
        this.bossRepository = bossRepository;
    }

    @Transactional
    @PostConstruct
    public void init(){

    }

    @Override
    public void create(Boss boss) {
        BossEntity bossEntity = objectMapper.convertValue(boss, BossEntity.class);
        bossRepository.save(bossEntity);
    }

    @Override
    public List<Boss> readAll() {
        Iterable<BossEntity> all = bossRepository.findAll();
        return objectMapper.convertValue(all, ArrayList.class);
    }

    @Override
    public Boss read(long id) {
        if (bossRepository.findById(id).isPresent()) {
            BossEntity bossEntity = bossRepository.findById(id).stream().findFirst().get();
            return objectMapper.convertValue(bossEntity, Boss.class);
        }
        return null;
    }

    @Override
    public boolean update(Boss boss, long id) {
        if (bossRepository.findById(id).isPresent()){
            bossRepository.save(objectMapper.convertValue(boss, BossEntity.class));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        if (bossRepository.findById(id).isPresent()){
            BossEntity bossEntity = bossRepository.findById(id).stream().findFirst().get();
            bossRepository.delete(bossEntity);
            return true;
        }
        return false;
    }
}
