package com.diploma.maksimov.viewcontroller;

import com.diploma.maksimov.dto.Contragent;
import com.diploma.maksimov.service.ContragentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/boss")
public class ContragentViewController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ContragentService contragentService;

    @GetMapping("/contragent")
    public String index(Model model) {
        List <Contragent> contragentList = contragentService.readAll();

        String contragentListAsString = null;

        try {
            contragentListAsString = objectMapper.writeValueAsString(contragentList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("Contragents",contragentListAsString);

        return "contragent";
    }

}
