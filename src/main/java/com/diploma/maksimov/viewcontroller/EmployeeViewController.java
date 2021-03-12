package com.diploma.maksimov.viewcontroller;

import com.diploma.maksimov.db.entity.EmployeeEntity;
import com.diploma.maksimov.service.EmployeeService;
import com.diploma.maksimov.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/boss")
public class EmployeeViewController {

    @Autowired
    private UserService userService;

    @Autowired
    ObjectMapper objectMapper;

@GetMapping("/employee")
    public String index(Model model) {
        List userList = userService.allUsers();
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
