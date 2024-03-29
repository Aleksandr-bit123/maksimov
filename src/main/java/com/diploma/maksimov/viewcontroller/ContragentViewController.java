package com.diploma.maksimov.viewcontroller;

import com.diploma.maksimov.dto.Contragent;
import com.diploma.maksimov.service.ContragentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/boss")
public class ContragentViewController {
    private final ObjectMapper objectMapper;
    private final ContragentService contragentService;

    public ContragentViewController(ObjectMapper objectMapper, ContragentService contragentService) {
        this.objectMapper = objectMapper;
        this.contragentService = contragentService;
    }

    @GetMapping("/contragent")
    public String index(Model model) {
        List<Contragent> contragentList = contragentService.readAll();

        String contragentListAsString = null;

        try {
            contragentListAsString = objectMapper.writeValueAsString(contragentList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("Contragents", contragentListAsString);

        return "contragent";
    }

}
