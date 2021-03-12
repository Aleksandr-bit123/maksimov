package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.Contragent;
import com.diploma.maksimov.dto.Point;
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

import static org.junit.Assert.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ContragentControllerTest {
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

    Point point = new Point(null, "address", "phone");
    Point point1 = new Point(null, "address 1", "phone 1");
    Contragent contragent = new Contragent(null, "Контрагент", "информация о контрагенте", point);
    Contragent contragent1 = new Contragent(null, "Контрагент 1", "информация о контрагенте 1", point1);

    String startUri = "/rest/boss/contragent/";

    public long createContragent() throws Exception {
        String content = objectMapper.writeValueAsString(contragent);
        String uri = startUri;
        MvcResult result = mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)).andReturn();
        return Long.parseLong(result.getResponse().getContentAsString());
    }

    public void deleteContragent(Long pointId) throws Exception {
        String uri = startUri + pointId;
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(uri));
    }

    //******************************************************************************************************************
    @Test
    public void create() throws Exception {
        String content = objectMapper.writeValueAsString(contragent);
        String uri = startUri;
        MvcResult result = mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated())
                .andDo(document(uri)).andReturn();
        deleteContragent(Long.parseLong(result.getResponse().getContentAsString()));
    }

    @Test
    public void readAll() throws Exception {
        Long pointId = createContragent();
        String uri = startUri;
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
        deleteContragent(pointId);
    }

    @Test
    public void read() throws Exception {
        Long pointId = createContragent();
        String uri = startUri + pointId;
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
        deleteContragent(pointId);
    }

    @Test
    public void update() throws Exception {
        Long pointId = createContragent();
        String uri = startUri + pointId;
        contragent1.setId(pointId);
        contragent1.getPoint().setId(pointId);
        String content = objectMapper.writeValueAsString(contragent1);
        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andDo(document(uri));
        deleteContragent(pointId);
    }

    @Test
    public void delete() throws Exception {
        Long pointId = createContragent();
        String uri = startUri + pointId;
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
    }
}