package com.diploma.maksimov.viewcontroller;

import com.diploma.maksimov.dto.Client;
import com.diploma.maksimov.dto.Good;
import com.diploma.maksimov.dto.Order;
import com.diploma.maksimov.service.ClientService;
import com.diploma.maksimov.service.GoodService;
import com.diploma.maksimov.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/logist")
public class OrderViewController {
    private final ObjectMapper objectMapper;
    private final OrderService orderService;
    private final ClientService clientService;
    private final GoodService goodService;

    public OrderViewController(ObjectMapper objectMapper, OrderService orderService, ClientService clientService, GoodService goodService) {
        this.objectMapper = objectMapper;
        this.orderService = orderService;
        this.clientService = clientService;
        this.goodService = goodService;
    }

    @GetMapping("/order")
    public String index(Model model) {
        List<Order> orderList = orderService.readAll();
        String orderListAsString = null;

        try {
            orderListAsString = objectMapper.writeValueAsString(orderList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        List<Client> clientList = clientService.readAll();
        String clientListAsString = null;

        try {
            clientListAsString = objectMapper.writeValueAsString(clientList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        List<Good> goodList = goodService.readAll();
        String goodListAsString = null;

        try {
            goodListAsString = objectMapper.writeValueAsString(goodList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("orders", orderListAsString);
        model.addAttribute("clients", clientListAsString);
        model.addAttribute("goods", goodListAsString);
        return "order";
    }
}
