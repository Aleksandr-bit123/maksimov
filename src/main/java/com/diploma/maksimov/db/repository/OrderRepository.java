package com.diploma.maksimov.db.repository;

import com.diploma.maksimov.db.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
}
