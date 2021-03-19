package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.dto.*;
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

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderControllerTest {
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
    Client client = new Client(null, "Фамилия","Имя","Отчество", "паспорт серия и номер", "информация о клиенте",point);

    String startClientUri = "/rest/logist/client/";


    public long createClient() throws Exception {
        String content = objectMapper.writeValueAsString(client);
        String uri = startClientUri;
        MvcResult result = mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)).andReturn();
        return Long.parseLong(result.getResponse().getContentAsString());
    }

    public void deleteClient(Long pointId) throws Exception {
        String uri = startClientUri + pointId;
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(uri));
    }

    Long id = 1000000L;

    Good good = new Good(1000000L,"Товар",2000.99,3.3,"информация о товаре");

    String startGoodUri = "/rest/boss/good/";

    public void createGood() throws Exception {
        String content = objectMapper.writeValueAsString(good);
        String uri = startGoodUri;
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
    }

    public void deleteGood() throws Exception {
        String uri = startGoodUri+id;
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(uri));
    }


    Order order = new Order(null,null,id, LocalDate.of(2000,02,10),(byte) 3,"информация");
    Order order1 = new Order(null,null,id, LocalDate.of(1999,05,2),(byte) 5,"информация 1");

    String startUri = "/rest/logist/order/";

    public long createOrder() throws Exception {
        String content = objectMapper.writeValueAsString(order);
        String uri = startUri;
        MvcResult result = mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)).andReturn();
        return Long.parseLong(result.getResponse().getContentAsString());
    }

    public void deleteOrder(Long idBD) throws Exception {
        String uri = startUri + idBD;
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(uri));
    }
    //******************************************************************************************************************

    @Test
    public void create() throws Exception {
        createGood();
        Long clientId = createClient();
        order.setClientId(clientId);
        String content = objectMapper.writeValueAsString(order);
        String uri = startUri;
        MvcResult result = mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated())
                .andDo(document(uri)).andReturn();
        deleteOrder(Long.parseLong(result.getResponse().getContentAsString()));
        deleteClient(clientId);
        deleteGood();
    }

    @Test
    public void readAll() throws Exception {
        createGood();
        Long clientId = createClient();
        order.setClientId(clientId);
        Long OrderId = createOrder();
        String uri = startUri;
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
        deleteOrder(OrderId);
        deleteClient(clientId);
        deleteGood();
    }

    @Test
    public void read() throws Exception {
        createGood();
        Long clientId = createClient();
        order.setClientId(clientId);
        Long OrderId = createOrder();
        String uri = startUri + OrderId;
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
        deleteOrder(OrderId);
        deleteClient(clientId);
        deleteGood();
    }

    @Test
    public void update() throws Exception {
        createGood();
        Long clientId = createClient();
        order.setClientId(clientId);
        order1.setClientId(clientId);
        Long OrderId = createOrder();
        String uri = startUri + OrderId;
        order1.setId(OrderId);
        String content = objectMapper.writeValueAsString(order1);
        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andDo(document(uri));
        deleteOrder(OrderId);
        deleteClient(clientId);
        deleteGood();
    }

    @Test
    public void delete() throws Exception {
        createGood();
        Long clientId = createClient();
       order.setClientId(clientId);
        Long OrderId = createOrder();
        String uri = startUri + OrderId;
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(uri))
                .andExpect(status().isOk())
                .andDo(document(uri));
        deleteClient(clientId);
        deleteGood();
    }

}