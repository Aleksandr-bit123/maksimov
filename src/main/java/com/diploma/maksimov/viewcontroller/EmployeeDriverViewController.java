package com.diploma.maksimov.viewcontroller;

import com.diploma.maksimov.dto.Boss;
import com.diploma.maksimov.dto.Driver;
import com.diploma.maksimov.dto.Employee;
import com.diploma.maksimov.service.BossService;
import com.diploma.maksimov.service.DriverService;
import com.diploma.maksimov.service.EmployeeService;
import com.diploma.maksimov.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/boss/employee/{id}")
public class EmployeeDriverViewController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/driver")
    public String index(Model model, @PathVariable Long id) {
        Employee employee = employeeService.read(id);
        if ( employee != null) {

            Driver driver = driverService.read(id);
            if (driver==null) {
                driverService.create(new Driver(id,null,"",""));
                userService.addRole(id,5L);
                driver = driverService.read(id);
            }
            String driverAsString = null;

            try {
                driverAsString = objectMapper.writeValueAsString(driver);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            String employeeAsString = null;

            try {
                employeeAsString = objectMapper.writeValueAsString(employee);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            model.addAttribute("Driver",driverAsString);
            model.addAttribute("Employee",employeeAsString);
            return "employee_driver";
        }
        return "Отсутствует сотрудник с id: " + id;
    }
}
