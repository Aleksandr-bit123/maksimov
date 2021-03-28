package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.EmployeeEntity;
import com.diploma.maksimov.db.repository.EmployeeRepository;
import com.diploma.maksimov.dto.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeService implements CrudService<Employee, Long, Void>{
    private final EmployeeRepository employeeRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    @PostConstruct
    public void init(){
        log.info("Our bean up");
    }

    @Override
    public Void create(Employee employee) {
        EmployeeEntity employeeEntity = objectMapper.convertValue(employee, EmployeeEntity.class);
        employeeRepository.save(employeeEntity);
        return null;
    }

    @Override
    public List<Employee> readAll() {
        Iterable<EmployeeEntity> all = employeeRepository.findAll();
        return objectMapper.convertValue(all, new TypeReference<>() {
        });
    }

    @Override
    public Employee read(Long id) {
        Optional<EmployeeEntity> employeeEntityOptional = employeeRepository.findById(id);
        if (employeeEntityOptional.isPresent()) {
            EmployeeEntity employeeEntity = employeeEntityOptional.get();
            return objectMapper.convertValue(employeeEntity, Employee.class);
        }
        return null;
    }

    @Override
    public boolean update(Employee employee, Long id) {
        if (employeeRepository.findById(id).isPresent()){
            employeeRepository.save(objectMapper.convertValue(employee, EmployeeEntity.class));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        Optional<EmployeeEntity> employeeEntityOptional = employeeRepository.findById(id);
        if (employeeEntityOptional.isPresent()) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
