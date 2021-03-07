package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.Car;
import com.diploma.maksimov.dto.Driver;
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
import org.springframework.web.context.WebApplicationContext;

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
    Car car = new Car(id,id,"ВАЗ 21083","А611ХМ78",2.3,11.2,"Цвет: красный");
    Car car1 = new Car(id,id,"BMW","А777AA99",4.3,15.2,"Цвет: черный");
    String startUri = "/rest/boss/employee/"+id+"/driver/car/";


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

    public void deleteCar(long idBD) throws Exception {
        String uri = startUri + idBD;
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(uri));
    }

    Driver driver = new Driver(id, null,"12 34 567890 B B1 M","действительны до 16.06.2028");
    String startDriverUri = "/rest/boss/employee/"+id+"/driver/";

    public void createDriver() throws Exception {
        String content = objectMapper.writeValueAsString(driver);
        String uri = startDriverUri;
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
    }

    public void deleteDriver() throws Exception {
        String uri = startDriverUri;
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(uri));
    }
    //******************************************************************************************************************
    @Test
    public void create() throws Exception {
        createDriver();
        String content = objectMapper.writeValueAsString(car);
        String uri = startUri;
        MvcResult result = mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated())
                .andDo(document(uri)).andReturn();
        deleteCar(Long.parseLong(result.getResponse().getContentAsString()));
        deleteDriver();
    }

    @Test
    public void readAll() throws Exception {
        createDriver();
        Long idBD = createCar();
        String uri = startUri;
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
        deleteCar(idBD);
        deleteDriver();
    }

    @Test
    public void read() throws Exception {
        createDriver();
        Long idBD = createCar();
        String uri = startUri+idBD;
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
        deleteCar(idBD);
        deleteDriver();
    }

    @Test
    public void update() throws Exception{
        createDriver();
        Long idBD = createCar();
        car1.setId(idBD);
        String content = objectMapper.writeValueAsString(car1);
        String uri = startUri+idBD;
        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andDo(document(uri));
        deleteCar(idBD);
        deleteDriver();
    }

    @Test
    public void delete() throws Exception {
        createDriver();
        Long idBD = createCar();
        String uri = startUri + idBD;
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
        deleteDriver();
    }
}