package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.Driver;
import com.diploma.maksimov.service.IDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/boss/employee/{id}")
public class DriverController {
    private final IDriverService driverService;

    @Autowired
    public DriverController(IDriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping(value = "/driver")
    public ResponseEntity<?> create(@RequestBody Driver driver) {
        driverService.create(driver);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/driver")
    public ResponseEntity<List<Driver>> readAll() {
        final List<Driver> drivers = driverService.readAll();

        if (drivers != null && !drivers.isEmpty()) {
            return new ResponseEntity<>(drivers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/driver/{id}")
    public ResponseEntity<Driver> read(@PathVariable long id) {
        final Driver driver = driverService.read(id);

        if (driver != null) {
            return new ResponseEntity<>(driver, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/driver/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Driver driver) {
        final boolean updated = driverService.update(driver, id);
        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/driver/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        final boolean deleted = driverService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
