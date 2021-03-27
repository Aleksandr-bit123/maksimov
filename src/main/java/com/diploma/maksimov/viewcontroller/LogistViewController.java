package com.diploma.maksimov.viewcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/logist")
public class LogistViewController {
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("date", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        return "logist";
    }
}
