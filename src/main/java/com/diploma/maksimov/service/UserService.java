package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.RoleEntity;
import com.diploma.maksimov.db.entity.UserEntity;
import com.diploma.maksimov.db.repository.RoleRepository;
import com.diploma.maksimov.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

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

    public List<UserEntity> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM UserEntity u WHERE u.id > :paramId", UserEntity.class)
                .setParameter("paramId", idMin).getResultList();
    }

    public boolean addRole(Long userId, Long roleId) {
        UserEntity userFromDB = userRepository.findById(userId).stream().findFirst().get();
//        if (userFromDB == null) {
//            return false;
//        }
        RoleEntity roleFromDB = roleRepository.findById(roleId).stream().findFirst().get();
        if (roleFromDB == null) {
            return false;
        }
        Set<RoleEntity> temp = userFromDB.getRoles();
        temp.add(roleFromDB);
        userFromDB.setRoles(temp);
        userRepository.save(userFromDB);
        return true;
    }
    public boolean deleteRole(Long userId, Long roleId) {
        UserEntity userFromDB = userRepository.findById(userId).stream().findFirst().get();
//        if (userFromDB == null) {
//            return false;
//        }
        RoleEntity roleFromDB = roleRepository.findById(roleId).stream().findFirst().get();
        if (roleFromDB == null) {
            return false;
        }
        Set<RoleEntity> temp = userFromDB.getRoles();
        temp.remove(roleFromDB);
        userFromDB.setRoles(temp);
        userRepository.save(userFromDB);
        return true;
    }
}
