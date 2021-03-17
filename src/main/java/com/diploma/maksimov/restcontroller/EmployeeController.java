package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.Employee;
import com.diploma.maksimov.service.CrudService;
import com.diploma.maksimov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/boss")
public class EmployeeController {
    private final CrudService<Employee,Long> employeeService;

    @Autowired
    public EmployeeController(CrudService<Employee,Long> employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    private UserService userService;

    @PostMapping(value = "/employee",headers = "Accept=application/json")
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
        userService.deleteRole(id, 3L);
        userService.deleteRole(id, 4L);
        userService.deleteRole(id, 5L);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
