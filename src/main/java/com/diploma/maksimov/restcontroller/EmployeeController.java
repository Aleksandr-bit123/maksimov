package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.Employee;
import com.diploma.maksimov.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/boss")
public class EmployeeController {
    private final IEmployeeService employeeService;

    @Autowired
    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping(value = "/employee")
    public ResponseEntity<?> create(@RequestBody Employee employee) {
        employeeService.create(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/employee")
    public ResponseEntity<List<Employee>> readAll() {
        final List<Employee> employees = employeeService.readAll();

        if (employees != null && !employees.isEmpty()) {
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/employee/{id}")
    public ResponseEntity<Employee> read(@PathVariable long id) {
        final Employee employee = employeeService.read(id);

        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/employee/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Employee employee) {
        final boolean updated = employeeService.update(employee, id);
        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/employee/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        final boolean deleted = employeeService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
