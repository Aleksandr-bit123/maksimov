package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.RoleEntity;
import com.diploma.maksimov.db.repository.RoleRepository;
import com.diploma.maksimov.dto.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRoleService{
    private final RoleRepository roleRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role read(long id) {
        Optional<RoleEntity> optionalRoleEntity = roleRepository.findById(id);
        if (optionalRoleEntity.isPresent()) {
            RoleEntity roleEntity = optionalRoleEntity.get();
            return objectMapper.convertValue(roleEntity, Role.class);
        }
        return null;
    }
}
