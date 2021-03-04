package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.EmployeeEntity;
import com.diploma.maksimov.db.entity.RoleEntity;
import com.diploma.maksimov.db.entity.UserEntity;
import com.diploma.maksimov.db.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public boolean deleteEmployee(Long userId) {
        if (employeeRepository.findById(userId).isPresent()) {
            employeeRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public boolean saveEmployee(EmployeeEntity employeeEntity) {
//
//        System.out.println(employeeEntity.getId());
        employeeRepository.save(employeeEntity);
        return true;
    }
}
