package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.RoleEntity;
import com.diploma.maksimov.db.entity.UserEntity;
import com.diploma.maksimov.db.repository.RoleRepository;
import com.diploma.maksimov.db.repository.UserRepository;
import com.diploma.maksimov.dto.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ObjectMapper objectMapper;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @PostConstruct
    public void init(){
        if(roleRepository.findAll().isEmpty()) {
            roleRepository.save(new RoleEntity(1L,"ROLE_USER"));
            roleRepository.save(new RoleEntity(2L,"ROLE_ADMIN"));
            roleRepository.save(new RoleEntity(3L,"ROLE_BOSS"));
            roleRepository.save(new RoleEntity(4L,"ROLE_LOGIST"));
            roleRepository.save(new RoleEntity(5L,"ROLE_DRIVER"));
        }

        User user = new User("admin","admin","admin");
        if (saveUser(objectMapper.convertValue(user,UserEntity.class))){
            UserEntity userEntity = userRepository.findByUsername("admin");
            Long userId = userEntity.getId();
            addRole(userId,1L);
            addRole(userId,2L);
            addRole(userId,3L);
            addRole(userId,4L);
            addRole(userId,5L);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public UserEntity findUserById(Long userId) {
        Optional<UserEntity> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new UserEntity());
    }

    public List<UserEntity> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(UserEntity user) {
        UserEntity userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new RoleEntity(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public boolean addRole(Long userId, Long roleId) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        Optional<RoleEntity> optionalRoleEntity = roleRepository.findById(roleId);

        if (optionalUserEntity.isPresent() && optionalRoleEntity.isPresent()){
                Set<RoleEntity> temp = optionalUserEntity.get().getRoles();
                temp.add(optionalRoleEntity.get());
                optionalUserEntity.get().setRoles(temp);
                userRepository.save(optionalUserEntity.get());
                return true;
        }
        return false;
    }


    public boolean deleteRole(Long userId, Long roleId) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        Optional<RoleEntity> optionalRoleEntity = roleRepository.findById(roleId);

        if (optionalUserEntity.isPresent() && optionalRoleEntity.isPresent()){
            Set<RoleEntity> temp = optionalUserEntity.get().getRoles();
            temp.remove(optionalRoleEntity.get());
            optionalUserEntity.get().setRoles(temp);
            userRepository.save(optionalUserEntity.get());
            return true;
        }
        return false;
    }
}
