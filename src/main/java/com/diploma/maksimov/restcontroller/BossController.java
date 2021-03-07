package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.Boss;
import com.diploma.maksimov.service.IBossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/boss/employee/{id}")
public class BossController {
    private final IBossService bossService;

    @Autowired
    public BossController(IBossService bossService) {
        this.bossService = bossService;
    }

    @PostMapping(value = "/boss")
    public ResponseEntity<?> create(@RequestBody Boss boss) {
        bossService.create(boss);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/boss")
    public ResponseEntity<List<Boss>> readAll() {
        final List<Boss> bosses = bossService.readAll();

        if (bosses != null && !bosses.isEmpty()) {
            return new ResponseEntity<>(bosses, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/boss/{id}")
    public ResponseEntity<Boss> read(@PathVariable long id) {
        final Boss boss = bossService.read(id);

        if (boss != null) {
            return new ResponseEntity<>(boss, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/boss/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Boss boss) {
        final boolean updated = bossService.update(boss, id);
        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/boss/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        final boolean deleted = bossService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
