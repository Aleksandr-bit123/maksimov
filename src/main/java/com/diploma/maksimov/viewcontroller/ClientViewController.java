package com.diploma.maksimov.viewcontroller;

import com.diploma.maksimov.dto.Client;
import com.diploma.maksimov.service.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/logist")
public class ClientViewController {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientService clientService;

    @GetMapping("/client")
    public String index(Model model) {
        List<Client> clientList = clientService.readAll();

        String clientListAsString = null;

        try {
            clientListAsString = objectMapper.writeValueAsString(clientList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("Clients",clientListAsString);

        return "client";
    }
}
