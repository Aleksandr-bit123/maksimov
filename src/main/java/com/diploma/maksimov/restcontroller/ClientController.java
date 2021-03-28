package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.Client;
import com.diploma.maksimov.dto.Point;
import com.diploma.maksimov.service.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/logist")
public class ClientController {

    private final CrudService<Point,Long, Long> pointService;
    private final CrudService<Client,Long, Void> clientService;

    public ClientController(CrudService<Point,Long,Long> pointService, CrudService<Client,Long,Void> clientService) {
        this.pointService = pointService;
        this.clientService = clientService;
    }

    @PostMapping(value = "/client",headers = "Accept=application/json")
    public ResponseEntity<Long> create(@RequestBody Client client) {
        Long pointId = pointService.create(client.getPoint());
        client.setId(pointId);
        client.getPoint().setId(pointId);
        clientService.create(client);
        return new ResponseEntity<>(pointId, HttpStatus.CREATED);
    }

    @GetMapping(value = "/client")
    public ResponseEntity<List<Client>> readAll() {
        final List<Client> clients = clientService.readAll();

        if (clients != null) {
            return new ResponseEntity<>(clients, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/client/{id}")
    public ResponseEntity<Client> read(@PathVariable long id) {
        final Client client = clientService.read(id);

        if (client != null) {
            return new ResponseEntity<>(client, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/client/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody Client client) {
        final boolean updated = clientService.update(client, id);
        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/client/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        final boolean deleted = clientService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
