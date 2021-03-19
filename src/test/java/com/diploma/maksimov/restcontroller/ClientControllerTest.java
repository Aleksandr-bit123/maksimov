package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.Client;
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
public class ClientControllerTest {
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

    Point point = new Point(null, "address", "phone", Point.PointType.client);
    Point point1 = new Point(null, "address 1", "phone 1", Point.PointType.client);
    Client client = new Client(null, "Фамилия","Имя","Отчество", "паспорт серия и номер", "информация о клиенте",point);
    Client client1 = new Client(null, "Фамилия 1","Имя 1","Отчество 1", "паспорт 1 серия и номер", "информация о клиенте 1",point1);

    String startUri = "/rest/logist/client/";

    public long createClient() throws Exception {
        String content = objectMapper.writeValueAsString(client);
        String uri = startUri;
        MvcResult result = mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)).andReturn();
        return Long.parseLong(result.getResponse().getContentAsString());
    }

    public void deleteClient(Long pointId) throws Exception {
        String uri = startUri + pointId;
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(uri));
    }

    //******************************************************************************************************************

    @Test
    public void create() throws Exception {
        String content = objectMapper.writeValueAsString(client);
        String uri = startUri;
        MvcResult result = mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated())
                .andDo(document(uri)).andReturn();
        deleteClient(Long.parseLong(result.getResponse().getContentAsString()));
    }

    @Test
    public void readAll() throws Exception {
        Long pointId = createClient();
        String uri = startUri;
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
        deleteClient(pointId);
    }

    @Test
    public void read() throws Exception {
        Long pointId = createClient();
        String uri = startUri + pointId;
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
        deleteClient(pointId);
    }

    @Test
    public void update() throws Exception {
        Long pointId = createClient();
        String uri = startUri + pointId;
        client1.setId(pointId);
        client1.getPoint().setId(pointId);
        String content = objectMapper.writeValueAsString(client1);
        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andDo(document(uri));
        deleteClient(pointId);
    }

    @Test
    public void delete() throws Exception {
        Long pointId = createClient();
        String uri = startUri + pointId;
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
    }
}