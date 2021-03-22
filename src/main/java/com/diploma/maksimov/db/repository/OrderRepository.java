package com.diploma.maksimov.db.repository;

import com.diploma.maksimov.db.entity.GoalEntity;
import com.diploma.maksimov.db.entity.OrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    @Query("select u from OrderEntity u where u.deliveryDate = :date")
    List<OrderEntity> findAllByDate(LocalDate date);

    @Query("select u from OrderEntity u where u.deliveryDate = :date and u.clientId = :clientId and u.status IS NULL")
    List<OrderEntity> findAllByDateAndClientIdandStatusIsNull(LocalDate date, Long clientId);
}
