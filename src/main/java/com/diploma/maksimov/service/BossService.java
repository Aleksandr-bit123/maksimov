package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.BossEntity;
import com.diploma.maksimov.db.repository.BossRepository;
import com.diploma.maksimov.dto.Boss;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BossService implements CrudService<Boss, Long, Void> {
    private final BossRepository bossRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BossService(BossRepository bossRepository) {
        this.bossRepository = bossRepository;
    }

    @Override
    public Void create(Boss boss) {
        BossEntity bossEntity = objectMapper.convertValue(boss, BossEntity.class);
        bossRepository.save(bossEntity);
        return null;
    }

    @Override
    public List<Boss> readAll() {
        Iterable<BossEntity> all = bossRepository.findAll();

        return objectMapper.convertValue(all, new TypeReference<>() {
        });
    }

    @Override
    public Boss read(Long id) {
        Optional<BossEntity> bossEntityOptional = bossRepository.findById(id);
        if (bossEntityOptional.isPresent()) {
            BossEntity bossEntity = bossEntityOptional.get();
            return objectMapper.convertValue(bossEntity, Boss.class);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean update(Boss boss, Long id) {
        if (bossRepository.findById(id).isPresent()) {
            bossRepository.save(objectMapper.convertValue(boss, BossEntity.class));
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Optional<BossEntity> bossEntityOptional = bossRepository.findById(id);
        if (bossEntityOptional.isPresent()) {
            bossRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
