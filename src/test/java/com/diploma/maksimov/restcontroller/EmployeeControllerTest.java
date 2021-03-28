package com.diploma.maksimov.restcontroller;

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
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmployeeControllerTest {
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
    Employee employee1 = new Employee(id, "Максимов", "Александр", "Викторович", LocalDate.of(1990, 2, 8), "1234 567890", "123-45-67", null, null, null);
    String startUri = "/rest/boss/employee/";

    //******************************************************************************************************************


    @Test
    @Transactional
    public void create() throws Exception {
        String content = objectMapper.writeValueAsString(employee);
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
        create();
        String uri = startUri;
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
    }

    @Test
    @Transactional
    public void read() throws Exception {
        create();
        String uri = startUri + id;
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
    }

    @Test
    @Transactional
    public void update() throws Exception {
        create();
        String content = objectMapper.writeValueAsString(employee1);
        String uri = startUri + id;
        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andDo(document(uri));
    }

    @Test
    public void delete() throws Exception {
        create();
        String uri = startUri + id;
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
    }
}