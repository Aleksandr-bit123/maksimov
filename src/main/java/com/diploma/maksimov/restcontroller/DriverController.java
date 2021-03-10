package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.Driver;
import com.diploma.maksimov.service.EmployeeService;
import com.diploma.maksimov.service.IDriverService;
import com.diploma.maksimov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/boss/employee")
public class DriverController {
    private final IDriverService driverService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    public DriverController(IDriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping(value = "/{id}/driver")
    public ResponseEntity<?> create(@RequestBody Driver driver, @PathVariable Long id) {
        if (employeeService.read(id) != null) {
            driverService.create(driver);
            userService.addRole(id,5L);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/driver")
    public ResponseEntity<List<Driver>> readAll() {
        final List<Driver> drivers = driverService.readAll();

        if (drivers != null && !drivers.isEmpty()) {
            return new ResponseEntity<>(drivers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}/driver")
    public ResponseEntity<Driver> read(@PathVariable long id) {
        if (employeeService.read(id) != null) {
            final Driver driver = driverService.read(id);

            if (driver != null) {
                return new ResponseEntity<>(driver, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{id}/driver")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Driver driver) {
        if (employeeService.read(id) != null) {
            final boolean updated = driverService.update(driver, id);
            if (updated) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}/driver")
    public ResponseEntity<?> delete(@PathVariable long id) {
        if (employeeService.read(id) != null) {
            final boolean deleted = driverService.delete(id);
            userService.deleteRole(id,5L);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
