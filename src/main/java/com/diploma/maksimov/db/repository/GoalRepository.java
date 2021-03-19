package com.diploma.maksimov.db.repository;

import com.diploma.maksimov.db.entity.GoalEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface GoalRepository extends CrudRepository<GoalEntity,Long> {
    @Query("select u from GoalEntity u where u.deliveryDate = :date")
    List<GoalEntity> findAllByDate(LocalDate date);
}
