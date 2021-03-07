package com.diploma.maksimov.service;

import com.diploma.maksimov.dto.Employee;

import java.util.List;

public interface IEmployeeService {
    void create(Employee employee);
    List<Employee> readAll();
    Employee read(long id);
    boolean update(Employee employee, long id);
    boolean delete(long id);
}
