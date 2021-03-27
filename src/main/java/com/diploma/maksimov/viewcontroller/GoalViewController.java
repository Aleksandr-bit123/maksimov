package com.diploma.maksimov.viewcontroller;

import com.diploma.maksimov.db.entity.UserEntity;
import com.diploma.maksimov.dto.*;
import com.diploma.maksimov.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final ObjectMapper objectMapper;
    private final GoalService goalService;
    private final DriverService driverService;
    private final OrderService orderService;
    private final ClientService clientService;
    private final ContragentService contragentService;
    private final GoodService goodService;

    public GoalViewController(ObjectMapper objectMapper, GoalService goalService, DriverService driverService, OrderService orderService, ClientService clientService, ContragentService contragentService, GoodService goodService) {
        this.objectMapper = objectMapper;
        this.goalService = goalService;
        this.driverService = driverService;
        this.orderService = orderService;
        this.clientService = clientService;
        this.contragentService = contragentService;
        this.goodService = goodService;
    }

    @GetMapping("/goal")
    public String index(Model model) {
        List<Goal> goalList = goalService.readAll();

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

            List<Goal> goalList = goalService.readAllByDate(localDate);
            String goalListAsString = null;

            List<Driver> driverList = driverService.readAll();
            String driverListAsString = null;

            List<Contragent> contragentList = contragentService.readAll();
            String contragentListAsString = null;

            List<Order> orderList = orderService.readAllByDate(localDate);
            String orderListAsString = null;

            Set<Long> clientIdSet = new TreeSet<>();
            orderList.forEach(order -> clientIdSet.add(order.getClientId()));

            List<Client> clientList = new LinkedList<>();
            clientIdSet.forEach(clientId ->clientList.add(clientService.read(clientId)));
            String clientListAsString = null;

            List<Good> goodList = goodService.readAll();
            String goodListAsString = null;

            try {
                goalListAsString = objectMapper.writeValueAsString(goalList);
                driverListAsString = objectMapper.writeValueAsString(driverList);
                contragentListAsString = objectMapper.writeValueAsString(contragentList);
                orderListAsString = objectMapper.writeValueAsString(orderList);
                clientListAsString = objectMapper.writeValueAsString(clientList);
                goodListAsString = objectMapper.writeValueAsString(goodList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("goals",goalListAsString);
            model.addAttribute("drivers",driverListAsString);
            model.addAttribute("contragents",contragentListAsString);
            model.addAttribute("orders",orderListAsString);
            model.addAttribute("clients",clientListAsString);
            model.addAttribute("goods",goodListAsString);
            model.addAttribute("logistId",currentUser.getId());

            return "goal";

    }
}
