package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.Good;
import com.diploma.maksimov.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/boss")
public class GoodController {

    @Autowired
    private GoodService goodService;

    @PostMapping(value = "/good",headers = "Accept=application/json")
    public ResponseEntity<?> create(@RequestBody Good good) {
            goodService.create(good);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/good")
    public ResponseEntity<List<Good>> readAll() {
        final List<Good> goods = goodService.readAll();
        if (goods != null && !goods.isEmpty()) {
            return new ResponseEntity<>(goods, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/good/{id}")
    public ResponseEntity<Good> read(@PathVariable long id) {
        final Good good = goodService.read(id);

        if (good != null) {
            return new ResponseEntity<>(good, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/good/{id}",headers = "Accept=application/json")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Good good) {
        final boolean updated = goodService.update(good, id);
        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/good/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
            final boolean deleted = goodService.delete(id);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
