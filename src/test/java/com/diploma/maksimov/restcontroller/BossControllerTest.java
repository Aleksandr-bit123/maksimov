package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.Boss;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BossControllerTest {
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

    Boss boss = new Boss(id,"Директор 1 тест");
    Boss boss1 = new Boss(id,"Директор 2 тест");

    String startUri = "/rest/boss/employee/"+id+"/boss/";

    public void createBoss() throws Exception {
        String content = objectMapper.writeValueAsString(boss);
        String uri = startUri;
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
    }

    public void deleteBoss() throws Exception {
        String uri = startUri;
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(uri));
    }
    //******************************************************************************************************************

    @Test
    public void create() throws Exception {
        String content = objectMapper.writeValueAsString(boss);
        String uri = startUri;
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated())
                .andDo(document(uri));
        deleteBoss();
    }

//    @Test
//    public void readAll() throws Exception {
//        createBoss();
//        String uri = startUri;
//        mockMvc.perform(get(uri))
//                .andExpect(status().isOk())
//                .andDo(document(uri));
//        deleteBoss();
//    }

    @Test
    public void read() throws Exception {
        createBoss();
        String uri = startUri;
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
        deleteBoss();
    }

    @Test
    public void update() throws Exception {
        createBoss();
        String content = objectMapper.writeValueAsString(boss1);
        String uri = startUri;
        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andDo(document(uri));
        deleteBoss();
    }

    @Test
    public void delete() throws Exception {
        createBoss();
        String uri = startUri;
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
    }
}