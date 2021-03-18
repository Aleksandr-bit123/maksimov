package com.diploma.maksimov.viewcontroller;

import com.diploma.maksimov.dto.Employee;
import com.diploma.maksimov.dto.Logist;
import com.diploma.maksimov.service.EmployeeService;
import com.diploma.maksimov.service.LogistService;
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
public class EmployeeLogistViewController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UserService userService;

    @Autowired
    private LogistService logistService;

    @Autowired
    private ObjectMapper objectMapper;

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

            try {
                logistAsString = objectMapper.writeValueAsString(logist);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            String employeeAsString = null;

            try {
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
