package com.diploma.maksimov.viewcontroller;

import com.diploma.maksimov.dto.Boss;
import com.diploma.maksimov.dto.Employee;
import com.diploma.maksimov.service.BossService;
import com.diploma.maksimov.service.EmployeeService;
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
public class EmployeeBossViewController {
    private final EmployeeService employeeService;
    private final UserService userService;
    private final BossService bossService;
    private final ObjectMapper objectMapper;

    public EmployeeBossViewController(EmployeeService employeeService, UserService userService, BossService bossService, ObjectMapper objectMapper) {
        this.employeeService = employeeService;
        this.userService = userService;
        this.bossService = bossService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/boss")
    public String index(Model model, @PathVariable Long id) {
        Employee employee = employeeService.read(id);
        if ( employee != null) {
            Boss boss = bossService.read(id);
            if (boss==null) {
                employee.setBoss(true);
                bossService.create(new Boss(id,"",employee));
                userService.addRole(id,3L);
                boss = bossService.read(id);
            }

            String bossAsString = null;
            String employeeAsString = null;

            try {
                bossAsString = objectMapper.writeValueAsString(boss);
                employeeAsString = objectMapper.writeValueAsString(employee);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            model.addAttribute("Boss",bossAsString);
            model.addAttribute("Employee",employeeAsString);
            return "employee_boss";
        }
        return "Отсутствует сотрудник с id: " + id;
    }

}
