package com.diploma.maksimov.db.repository;

import com.diploma.maksimov.db.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<RoleEntity, Long> {
}
