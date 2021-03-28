package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.Contragent;
import com.diploma.maksimov.dto.Point;
import com.diploma.maksimov.service.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/boss")
public class ContragentController {
    private final CrudService<Point,Long,Long> pointService;
    private final CrudService<Contragent,Long,Void> contragentService;

    public ContragentController(CrudService<Point,Long,Long> pointService, CrudService<Contragent,Long, Void> contragentService) {
        this.pointService = pointService;
        this.contragentService = contragentService;
    }

    @PostMapping(value = "/contragent",headers = "Accept=application/json")
    public ResponseEntity<Long> create(@RequestBody Contragent contragent) {
        Long pointId = pointService.create(contragent.getPoint());
        contragent.setId(pointId);
        contragent.getPoint().setId(pointId);
        contragentService.create(contragent);
        return new ResponseEntity<>(pointId,HttpStatus.CREATED);
    }

    @GetMapping(value = "/contragent")
    public ResponseEntity<List<Contragent>> readAll() {
        final List<Contragent> contragents = contragentService.readAll();

        if (contragents != null) {
            return new ResponseEntity<>(contragents, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/contragent/{id}")
    public ResponseEntity<Contragent> read(@PathVariable long id) {
        final Contragent contragent = contragentService.read(id);

        if (contragent != null) {
            return new ResponseEntity<>(contragent, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/contragent/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody Contragent contragent) {
        final boolean updated = contragentService.update(contragent, id);
        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/contragent/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        final boolean deleted = contragentService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
