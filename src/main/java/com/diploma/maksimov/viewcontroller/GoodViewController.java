package com.diploma.maksimov.viewcontroller;

import com.diploma.maksimov.dto.Good;
import com.diploma.maksimov.service.GoodService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GoodViewController {
    private final ObjectMapper objectMapper;
    private final GoodService goodService;

    public GoodViewController(ObjectMapper objectMapper, GoodService goodService) {
        this.objectMapper = objectMapper;
        this.goodService = goodService;
    }

    @GetMapping("/boss/good")
    public String index(Model model) {
        List<Good> goods = goodService.readAll();
        String goodsListAsString = null;

        try {
            goodsListAsString = objectMapper.writeValueAsString(goods);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("goods",goodsListAsString);
        return "good";
    }
}
