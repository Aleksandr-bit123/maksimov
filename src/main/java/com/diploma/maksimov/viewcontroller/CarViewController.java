package com.diploma.maksimov.viewcontroller;

import com.diploma.maksimov.dto.Car;
import com.diploma.maksimov.dto.Driver;
import com.diploma.maksimov.dto.Employee;
import com.diploma.maksimov.service.CarService;
import com.diploma.maksimov.service.DriverService;
import com.diploma.maksimov.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/boss/employee/{id}/driver")
public class CarViewController {
    private final EmployeeService employeeService;
    private final DriverService driverService;
    private final CarService carService;
    private final ObjectMapper objectMapper;

    public CarViewController(EmployeeService employeeService, DriverService driverService, CarService carService, ObjectMapper objectMapper) {
        this.employeeService = employeeService;
        this.driverService = driverService;
        this.carService = carService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/car")
    public String index(Model model, @PathVariable Long id) {
        Driver driver = driverService.read(id);
        if ( driver != null) {

            List<Car> carList= driver.getCarEntities();
            if (carList.isEmpty()) {
                Long carId = carService.create(new Car(null,id,"","",0.0,0.0,""));
                Car car = carService.read(carId);
                carList.add(car);
            }
            String carListAsString = null;

            try {
                carListAsString = objectMapper.writeValueAsString(carList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            String employeeAsString = null;

            Employee employee = employeeService.read(id);

            try {
                employeeAsString = objectMapper.writeValueAsString(employee);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            model.addAttribute("Cars",carListAsString);
            model.addAttribute("Employee",employeeAsString);
            return "car";
        }
        return "Отсутствует водитель с id: " + id;
    }
}
