package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.Goal;
import com.diploma.maksimov.service.IGoalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/logist")
public class GoalController {

    private final IGoalService goalService;

    public GoalController(IGoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping(value = "/goal",headers = "Accept=application/json")
    public ResponseEntity<Long> create(@RequestBody Goal goal) {
        long goalId = goalService.create(goal);
        return new ResponseEntity<>(goalId, HttpStatus.CREATED);
    }

    @GetMapping(value = "/goal")
    public ResponseEntity<List<Goal>> readAll() {
        final List<Goal> goals = goalService.readAll();

        if (goals != null && !goals.isEmpty()) {
            return new ResponseEntity<>(goals, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/goal/{id}")
    public ResponseEntity<Goal> read(@PathVariable long id) {
        final Goal goal = goalService.read(id);

        if (goal != null) {
            return new ResponseEntity<>(goal, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/goal/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody Goal goal) {
        final boolean updated = goalService.update(goal, id);
        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/goal/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        final boolean deleted = goalService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
