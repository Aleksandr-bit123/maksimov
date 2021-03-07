package com.diploma.maksimov.restcontroller;


import com.diploma.maksimov.dto.Car;
import com.diploma.maksimov.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/boss/employee/{id}/driver/{id}")

public class CarController {
    private final ICarService carService;

    @Autowired
    public CarController(ICarService carService) {
        this.carService = carService;
    }

    @PostMapping(value = "/car")
    public ResponseEntity<Long> create(@RequestBody Car car) {
            Long idBD = carService.create(car);
            return new ResponseEntity<>(idBD, HttpStatus.CREATED);
    }

    @GetMapping(value = "/car")
    public ResponseEntity<List<Car>> readAll() {
        final List<Car> cars = carService.readAll();
        if (cars != null && !cars.isEmpty()) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/car/{id}")
    public ResponseEntity<Car> read(@PathVariable long id) {
        final Car car = carService.read(id);
        if (car != null) {
            return new ResponseEntity<>(car, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/car/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Car car) {
        final boolean updated = carService.update(car, id);
        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/car/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        final boolean deleted = carService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
