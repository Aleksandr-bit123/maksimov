package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.EmployeeEntity;
import com.diploma.maksimov.db.repository.EmployeeRepository;
import com.diploma.maksimov.dto.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class EmployeeService implements IEmployeeService{
    @Autowired
    EmployeeRepository employeeRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    @PostConstruct
    public void init(){
        log.info("Our bean up");
    }

    @Override
    public void create(Employee employee) {
        EmployeeEntity employeeEntity = objectMapper.convertValue(employee, EmployeeEntity.class);
        employeeRepository.save(employeeEntity);
    }

    @Override
    public List<Employee> readAll() {
        Iterable<EmployeeEntity> all = employeeRepository.findAll();
        return objectMapper.convertValue(all, ArrayList.class);
    }

    @Override
    public Employee read(long id) {
        if (employeeRepository.findById(id).isPresent()) {
            EmployeeEntity employeeEntity = employeeRepository.findById(id).stream().findFirst().get();
            return objectMapper.convertValue(employeeEntity, Employee.class);
        }
        return null;
    }

    @Override
    public boolean update(Employee employee, long id) {
        if (employeeRepository.findById(id).isPresent()){
            employeeRepository.save(objectMapper.convertValue(employee, EmployeeEntity.class));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        if (employeeRepository.findById(id).isPresent()){
            EmployeeEntity employeeEntity = employeeRepository.findById(id).stream().findFirst().get();
            employeeRepository.delete(employeeEntity);
            return true;
        }
        return false;
    }
}
