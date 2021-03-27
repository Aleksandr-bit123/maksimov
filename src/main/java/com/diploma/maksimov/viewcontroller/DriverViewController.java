package com.diploma.maksimov.viewcontroller;

import com.diploma.maksimov.db.entity.UserEntity;
import com.diploma.maksimov.dto.Goal;
import com.diploma.maksimov.service.GoalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
@RequestMapping("/driver")
public class DriverViewController {

    private final ObjectMapper objectMapper;
    private final GoalService goalService;

    public DriverViewController(ObjectMapper objectMapper, GoalService goalService) {
        this.objectMapper = objectMapper;
        this.goalService = goalService;
    }

    @GetMapping("/{date}")
    public String indexNow(Model model, @PathVariable String date) {

        UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        LocalDate localDate = LocalDate.now();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            localDate = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }

        List<Goal> goalList = goalService.readAllByDateAndDriver(localDate, currentUser.getId());
        String goalListAsString = null;
        try {
            goalListAsString = objectMapper.writeValueAsString(goalList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("goals",goalListAsString);

        return "driver";
    }



}
