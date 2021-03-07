package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.Logist;
import com.diploma.maksimov.service.ILogistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/boss/employee/{id}")
public class LogistController {
    private final ILogistService logistService;

    @Autowired
    public LogistController(ILogistService logistService) {
        this.logistService = logistService;
    }

    @PostMapping(value = "/logist")
    public ResponseEntity<?> create(@RequestBody Logist logist) {
        logistService.create(logist);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/logist")
    public ResponseEntity<List<Logist>> readAll() {
        final List<Logist> logists = logistService.readAll();

        if (logists != null && !logists.isEmpty()) {
            return new ResponseEntity<>(logists, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/logist/{id}")
    public ResponseEntity<Logist> read(@PathVariable long id) {
        final Logist logist = logistService.read(id);

        if (logist != null) {
            return new ResponseEntity<>(logist, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/logist/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Logist logist) {
        final boolean updated = logistService.update(logist, id);
        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/logist/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        final boolean deleted = logistService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
