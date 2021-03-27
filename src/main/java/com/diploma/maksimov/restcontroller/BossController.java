package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.Boss;
import com.diploma.maksimov.dto.Employee;
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
public class BossController {
    private final CrudService<Boss,Long> bossService;
    private final UserService userService;
    private final EmployeeService employeeService;

    @Autowired
    public BossController(CrudService<Boss, Long> bossService, UserService userService, EmployeeService employeeService) {
        this.bossService = bossService;
        this.userService = userService;
        this.employeeService = employeeService;
    }

    @PostMapping(value = "/{id}/boss")
    public ResponseEntity<Void> create(@RequestBody Boss boss, @PathVariable Long id) {
        Employee employee = employeeService.read(id);
        if (employee != null) {
            employee.setBoss(true);
            boss.setEmployee(employee);
            employeeService.update(employee,id);
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
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody Boss boss) {
        Employee employee = employeeService.read(id);
        if (employee != null) {
            //изменяю существующий объект, т.к. конфликт сессии гибернейт
            Boss boss1 = bossService.read(id);
            boss1.setInfo(boss.getInfo());
            final boolean updated = bossService.update(boss1, id);
            //изменяю существующий объект, т.к. конфликт сессии гибернейт
            if (updated) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}/boss")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        Employee employee = employeeService.read(id);
        if (employee != null) {
            final boolean deleted = bossService.delete(id);
            employee.setBoss(false);
            employeeService.update(employee,id);
            userService.deleteRole(id,3L);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
