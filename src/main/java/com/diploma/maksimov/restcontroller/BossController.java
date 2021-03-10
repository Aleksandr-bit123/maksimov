package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.Boss;
import com.diploma.maksimov.service.EmployeeService;
import com.diploma.maksimov.service.IBossService;
import com.diploma.maksimov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/boss/employee")
public class BossController {
    private final IBossService bossService;

    @Autowired
    public BossController(IBossService bossService) {
        this.bossService = bossService;
    }

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/{id}/boss")
    public ResponseEntity<?> create(@RequestBody Boss boss, @PathVariable Long id) {
        if (employeeService.read(id) != null) {
            bossService.create(boss);
            userService.addRole(id,3L);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/boss")
    public ResponseEntity<List<Boss>> readAll() {
        final List<Boss> bosses = bossService.readAll();

        if (bosses != null && !bosses.isEmpty()) {
            return new ResponseEntity<>(bosses, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}/boss")
    public ResponseEntity<Boss> read(@PathVariable long id) {
        if (employeeService.read(id) != null) {
            final Boss boss = bossService.read(id);

            if (boss != null) {
                return new ResponseEntity<>(boss, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{id}/boss")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Boss boss) {
        if (employeeService.read(id) != null) {
            final boolean updated = bossService.update(boss, id);
            if (updated) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}/boss")
    public ResponseEntity<?> delete(@PathVariable long id) {
        if (employeeService.read(id) != null) {
            final boolean deleted = bossService.delete(id);
            userService.deleteRole(id,3L);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
