package com.diploma.maksimov.viewcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BossViewController {

    @GetMapping("/boss")
    public String boss() {
        return "boss";
    }

}
