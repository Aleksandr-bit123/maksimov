package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.Order;
import com.diploma.maksimov.service.IOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/logist")
public class OrderController {
    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/order",headers = "Accept=application/json")
    public ResponseEntity<Long> create(@RequestBody Order order) {
        Long idBD = orderService.create(order);
        return new ResponseEntity<>(idBD, HttpStatus.CREATED);
    }

    @GetMapping(value = "/order")
    public ResponseEntity<List<Order>> readAll() {
        final List<Order> orders = orderService.readAll();

        if (orders != null && !orders.isEmpty()) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/order/{id}")
    public ResponseEntity<Order> read(@PathVariable long id) {
        final Order order = orderService.read(id);

        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/order/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody Order order) {
        final boolean updated = orderService.update(order, id);
        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/order/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        final boolean deleted = orderService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
