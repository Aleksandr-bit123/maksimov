package com.diploma.maksimov.db.repository;

import com.diploma.maksimov.db.entity.GoodTurnoverEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GoodTurnoverRepository extends CrudRepository<GoodTurnoverEntity, Long> {
    @Query("select u from GoodTurnoverEntity u where u.orderId = :orderId")
    List<GoodTurnoverEntity> findAllByOrderId(Long orderId);
}
