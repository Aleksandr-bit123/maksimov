package com.diploma.maksimov.db.repository;

import com.diploma.maksimov.db.entity.CarEntity;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<CarEntity,Long> {
}
