package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.Employee;
import com.diploma.maksimov.dto.Logist;
import com.diploma.maksimov.service.CrudService;
import com.diploma.maksimov.service.EmployeeService;
import com.diploma.maksimov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/boss/employee")
public class LogistController {
    private final CrudService<Logist,Long> logistService;

    @Autowired
    public LogistController(CrudService<Logist,Long> logistService) {
        this.logistService = logistService;
    }

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/{id}/logist")
    public ResponseEntity<?> create(@RequestBody Logist logist, @PathVariable Long id) {
        Employee employee = employeeService.read(id);
        if (employee != null) {
            employee.setLogist(true);
            logist.setEmployee(employee);
            employeeService.update(employee,id);
            logistService.create(logist);
            userService.addRole(id,4L);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/logist")
    public ResponseEntity<List<Logist>> readAll() {
        final List<Logist> logists = logistService.readAll();

        if (logists != null && !logists.isEmpty()) {
            return new ResponseEntity<>(logists, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}/logist")
    public ResponseEntity<Logist> read(@PathVariable long id) {
        if (employeeService.read(id) != null) {
            final Logist logist = logistService.read(id);

            if (logist != null) {
                return new ResponseEntity<>(logist, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{id}/logist")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Logist logist) {
        if (employeeService.read(id) != null) {
            Logist logist1 = logistService.read(id);
            logist1.setInfo(logist.getInfo());
            final boolean updated = logistService.update(logist1, id);
            if (updated) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}/logist")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Employee employee = employeeService.read(id);
        if (employee != null) {
            final boolean deleted = logistService.delete(id);
            employee.setLogist(false);
            employeeService.update(employee,id);
            userService.deleteRole(id,4L);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
