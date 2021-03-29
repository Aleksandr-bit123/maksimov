package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.db.entity.UserEntity;
import com.diploma.maksimov.dto.Goal;
import com.diploma.maksimov.service.GoalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/rest/driver")
public class DriverGoalController {

    private final GoalService goalService;

    public DriverGoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<Goal>> readAllGoalsByDate(Model model, @PathVariable String date) {

        UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        LocalDate localDate = LocalDate.now();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            localDate = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }

        List<Goal> goals = goalService.readAllByDateAndDriver(localDate, currentUser.getId());

        if (goals != null) {
            return new ResponseEntity<>(goals, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/goal/{id}")
    public ResponseEntity<Void> updateGoalStatus(@PathVariable long id) {
        final boolean updated = goalService.updateGoalStatus(id);
        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
