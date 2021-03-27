package com.diploma.maksimov.viewcontroller;

import com.diploma.maksimov.db.entity.UserEntity;
import com.diploma.maksimov.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/boss")
public class EmployeeViewController {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    public EmployeeViewController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/employee")
    public String index(Model model) {
        List<UserEntity> userList = userService.allUsers();
        String userListAsString = null;

        try {
            userListAsString = objectMapper.writeValueAsString(userList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("allUsers",userListAsString);
        return "employee_new";
    }

}
