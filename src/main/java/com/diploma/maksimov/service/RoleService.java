package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.RoleEntity;
import com.diploma.maksimov.db.repository.RoleRepository;
import com.diploma.maksimov.dto.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Service
public class RoleService implements IRoleService{
    private final RoleRepository roleRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    @PostConstruct
    public void init(){

    }

    @Override
    public Role read(long id) {
        if (roleRepository.findById(id).isPresent()) {
            RoleEntity roleEntity = roleRepository.findById(id).stream().findFirst().get();
            return objectMapper.convertValue(roleEntity, Role.class);
        }
        return null;
    }
}
