package com.diploma.maksimov.service;

import com.diploma.maksimov.dto.Goal;

import java.util.List;

public interface IGoalService {
    long create(Goal goal);
    List<Goal> readAll();
    Goal read(long id);
    boolean update(Goal goal, long id);
    boolean delete(long id);
}
