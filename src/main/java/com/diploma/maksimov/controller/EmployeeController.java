package com.diploma.maksimov.controller;

import com.diploma.maksimov.db.entity.EmployeeEntity;
import com.diploma.maksimov.db.entity.UserEntity;
import com.diploma.maksimov.service.EmployeeService;
import com.diploma.maksimov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/boss")
public class EmployeeController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/employee")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "employee";
    }

//    @PostMapping("/employee")
//    public String  deleteEmployee(@RequestParam(required = true, defaultValue = "" ) Long userId,
//                              @RequestParam(required = true, defaultValue = "" ) String action,
//                              Model model) {
//        if (action.equals("delete")){
//            employeeService.deleteEmployee(userId);
//        }
//        return "redirect:/boss/employee";
//    }

    @PostMapping(value = "/employee",headers = "Accept=application/json")
    public String addEmployee(@RequestBody @Valid EmployeeEntity employeeForm) {
        employeeService.saveEmployee(employeeForm);
        return "redirect:/boss/employee";
    }
}
