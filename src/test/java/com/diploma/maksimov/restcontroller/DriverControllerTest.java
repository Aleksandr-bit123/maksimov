package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.Car;
import com.diploma.maksimov.dto.Driver;
import com.diploma.maksimov.dto.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DriverControllerTest {

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper objectMapper;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @Before
    public void setUp() {
        ConfigurableMockMvcBuilder builder =
                MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                        .apply(documentationConfiguration(this.restDocumentation));
        this.mockMvc = builder.build();
    }
    //******************************************************************************************************************

    Long id = 1000000L;
    Employee employee = new Employee(id, "Максимов", "Александр", "Викторович", LocalDate.of(1990, 2, 8), "1234 567890", "123-45-67", null, null, null);
    Car car = new Car(id, id, "", "", 0.0, 0.0, "");
    ArrayList<Car> cars = new ArrayList<>();

    public void setCars(ArrayList<Car> cars) {
        cars.add(car);
        this.cars = cars;
    }

    Driver driver = new Driver(id, cars, "12 34 567890 B B1 M", "действительны до 16.06.2028", employee);

    Driver driver1 = new Driver(id, cars, "12 34 567890 B B1 M", "действительны до 16.06.2028", employee);
    String startUri = "/rest/boss/employee/" + id + "/driver/";

    public void createDriver() throws Exception {
        String content = objectMapper.writeValueAsString(driver);
        String uri = startUri;
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
    }

    String startEmloyeeUri = "/rest/boss/employee/";

    public void createEmployee() throws Exception {
        String content = objectMapper.writeValueAsString(employee);
        String uri = startEmloyeeUri;
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
    }

    //******************************************************************************************************************

    @Test
    @Transactional
    public void create() throws Exception {
        createEmployee();
        String content = objectMapper.writeValueAsString(driver);
        String uri = startUri;
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated())
                .andDo(document(uri));
    }

    @Test
    @Transactional
    public void readAll() throws Exception {
        createEmployee();
        create();
        String uri = "/rest/boss/employee/driver";
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
    }

    @Test
    @Transactional
    public void read() throws Exception {
        createEmployee();
        create();
        String uri = startUri;
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
    }

    @Test
    @Transactional
    public void update() throws Exception {
        createEmployee();
        create();
        String content = objectMapper.writeValueAsString(driver1);
        String uri = startUri;
        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andDo(document(uri));
    }

    @Test
    @Transactional
    public void delete() throws Exception {
        createEmployee();
        create();
        String uri = startUri;
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
    }
}