package com.diploma.maksimov.viewcontroller;

import com.diploma.maksimov.dto.*;
import com.diploma.maksimov.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Controller
@RequestMapping("/logist")
public class GoalViewController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GoalServise goalServise;

    @Autowired
    private DriverService driverService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ContragentService contragentService;

    @GetMapping("/goal")
    public String index(Model model) {
        List<Goal> goalList = goalServise.readAll();

        String goalListAsString = null;

        try {
            goalListAsString = objectMapper.writeValueAsString(goalList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("goals",goalListAsString);

        return "goal";
    }

    @GetMapping("/goal/{date}")
    public String indexNow(Model model, @PathVariable String date) {
        LocalDate localDate = LocalDate.now();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                localDate = LocalDate.parse(date,formatter);
            } catch (DateTimeParseException e){
                e.printStackTrace();
            }

            List<Goal> goalList = goalServise.readAllByDate(localDate);
            String goalListAsString = null;
            try {
                goalListAsString = objectMapper.writeValueAsString(goalList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            List<Driver> driverList = driverService.readAll();
            String driverListAsString = null;
            try {
                driverListAsString = objectMapper.writeValueAsString(driverList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            List<Contragent> contragentList = contragentService.readAll();
            String contragentListAsString = null;
            try {
                contragentListAsString = objectMapper.writeValueAsString(contragentList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            List<Order> orderList = orderService.readAllByDate(localDate);
            String orderListAsString = null;
            try {
                orderListAsString = objectMapper.writeValueAsString(orderList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            Set<Long> clientIdSet = new TreeSet<>();
            orderList.forEach(order -> clientIdSet.add(order.getClientId()));

            List<Client> clientList = new LinkedList<>();
            clientIdSet.forEach(clientId ->clientList.add(clientService.read(clientId)));
            String clientListAsString = null;
            try {
                clientListAsString = objectMapper.writeValueAsString(clientList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            model.addAttribute("goals",goalListAsString);
            model.addAttribute("drivers",driverListAsString);
            model.addAttribute("contragents",contragentListAsString);
            model.addAttribute("orders",orderListAsString);
            model.addAttribute("clients",clientListAsString);

            return "goal";


    }
}
