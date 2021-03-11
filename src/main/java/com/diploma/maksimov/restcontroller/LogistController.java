package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.Logist;
import com.diploma.maksimov.service.EmployeeService;
import com.diploma.maksimov.service.ILogistService;
import com.diploma.maksimov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/boss/employee")
public class LogistController {
    private final ILogistService logistService;

    @Autowired
    public LogistController(ILogistService logistService) {
        this.logistService = logistService;
    }

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/{id}/logist")
    public ResponseEntity<?> create(@RequestBody Logist logist, @PathVariable Long id) {
        if (employeeService.read(id) != null) {
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
            final boolean updated = logistService.update(logist, id);
            if (updated) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}/logist")
    public ResponseEntity<?> delete(@PathVariable long id) {
        if (employeeService.read(id) != null) {
            final boolean deleted = logistService.delete(id);
            userService.deleteRole(id,4L);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
