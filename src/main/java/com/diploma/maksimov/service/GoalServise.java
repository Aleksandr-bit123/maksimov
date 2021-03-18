package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.GoalEntity;
import com.diploma.maksimov.db.repository.GoalRepository;
import com.diploma.maksimov.dto.Goal;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalServise implements IGoalService{
    private final GoalRepository goalRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GoalServise(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    public long create(Goal goal) {
        GoalEntity goalEntity = objectMapper.convertValue(goal, GoalEntity.class);
        goalEntity = goalRepository.save(goalEntity);
        return goalEntity.getId();
    }

    @Override
    public List<Goal> readAll() {
        Iterable<GoalEntity> all = goalRepository.findAll();

        return objectMapper.convertValue(all, new TypeReference<List<Goal>>() {
        });
    }

    @Override
    public Goal read(long id) {
        Optional<GoalEntity> goalEntityOptional = goalRepository.findById(id);
        if (goalEntityOptional.isPresent()) {
            GoalEntity goalEntity = goalEntityOptional.get();
            return objectMapper.convertValue(goalEntity, Goal.class);
        }
        return null;
    }

    @Override
    public boolean update(Goal goal, long id) {
        if (goalRepository.findById(id).isPresent()) {
            goalRepository.save(objectMapper.convertValue(goal, GoalEntity.class));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        Optional<GoalEntity> goalEntityOptional = goalRepository.findById(id);
        if (goalEntityOptional.isPresent()) {
            goalRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
