package com.diploma.maksimov.viewcontroller;

import com.diploma.maksimov.dto.Employee;
import com.diploma.maksimov.dto.Logist;
import com.diploma.maksimov.service.EmployeeService;
import com.diploma.maksimov.service.LogistService;
import com.diploma.maksimov.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/boss/employee/{id}")
public class EmployeeLogistViewController {

    private final EmployeeService employeeService;
    private final UserService userService;
    private final LogistService logistService;
    private final ObjectMapper objectMapper;

    public EmployeeLogistViewController(EmployeeService employeeService, UserService userService, LogistService logistService, ObjectMapper objectMapper) {
        this.employeeService = employeeService;
        this.userService = userService;
        this.logistService = logistService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/logist")
    public String index(Model model, @PathVariable Long id) {
        Employee employee = employeeService.read(id);
        if ( employee != null) {

            Logist logist = logistService.read(id);
            if (logist==null) {
                employee.setLogist(true);
                logistService.create(new Logist(id,"",employee));
                userService.addRole(id,4L);
            }
            String logistAsString = null;
            String employeeAsString = null;

            try {
                logistAsString = objectMapper.writeValueAsString(logist);
                employeeAsString = objectMapper.writeValueAsString(employee);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            model.addAttribute("Logist",logistAsString);
            model.addAttribute("Employee",employeeAsString);
            return "employee_logist";
        }
        return "Отсутствует сотрудник с id: " + id;
    }
}
