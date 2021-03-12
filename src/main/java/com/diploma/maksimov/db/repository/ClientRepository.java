package com.diploma.maksimov.db.repository;

import com.diploma.maksimov.db.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<ClientEntity,Long> {
}
