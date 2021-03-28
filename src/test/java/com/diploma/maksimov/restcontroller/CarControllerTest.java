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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CarControllerTest {

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
    Car car = new Car(null, id, "ВАЗ 21083", "А611ХМ78", 2.3, 11.2, "Цвет: красный");
    Car car1 = new Car(null, id, "BMW", "А777AA99", 4.3, 15.2, "Цвет: черный");
    String startUri = "/rest/boss/employee/" + id + "/driver/car/";

    Employee employee = new Employee(id, "Максимов", "Александр", "Викторович", LocalDate.of(1990, 2, 8), "1234 567890", "123-45-67", null, null, null);

    public long createCar() throws Exception {
        Long idBD;
        String content = objectMapper.writeValueAsString(car);
        String uri = startUri;
        MvcResult result = mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)).andReturn();
        //необходимо. так как БД автоматически назначает ip машине
        return Long.parseLong(result.getResponse().getContentAsString());
    }

    Driver driver = new Driver(id, null, "12 34 567890 B B1 M", "действительны до 16.06.2028", employee);
    String startDriverUri = "/rest/boss/employee/" + id + "/driver/";

    public void createDriver() throws Exception {
        String content = objectMapper.writeValueAsString(driver);
        String uri = startDriverUri;
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
        createDriver();
        String content = objectMapper.writeValueAsString(car);
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
        createDriver();
        createCar();
        String uri = "/rest/boss/employee/driver/car";
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
    }

    @Test
    @Transactional
    public void read() throws Exception {
        createEmployee();
        createDriver();
        long idBD = createCar();
        String uri = startUri + idBD;
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
    }

    @Test
    @Transactional
    public void update() throws Exception {
        createEmployee();
        createDriver();
        Long idBD = createCar();
        car1.setId(idBD);
        String content = objectMapper.writeValueAsString(car1);
        String uri = startUri + idBD;
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
        createDriver();
        long idBD = createCar();
        String uri = startUri + idBD;
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
    }
}