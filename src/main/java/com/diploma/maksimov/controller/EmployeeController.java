package com.diploma.maksimov.controller;

import com.diploma.maksimov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/boss")
public class EmployeeController {

    @Autowired
    private UserService userService;


    @GetMapping("/employee")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "employee";
    }
}
