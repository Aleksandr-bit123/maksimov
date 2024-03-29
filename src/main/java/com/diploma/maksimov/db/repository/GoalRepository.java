package com.diploma.maksimov.db.repository;

import com.diploma.maksimov.db.entity.GoalEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface GoalRepository extends CrudRepository<GoalEntity,Long> {
    @Query("select u from GoalEntity u where u.deliveryDate = :date order by u.driverId, u.priority ")
    List<GoalEntity> findAllByDate(LocalDate date);

    @Query("select u from GoalEntity u where u.deliveryDate = :date and u.driverId = :driverId order by u.priority ")
    List<GoalEntity> findAllByDateAndDriverId(LocalDate date, Long driverId);
}
