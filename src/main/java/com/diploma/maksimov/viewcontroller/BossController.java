package com.diploma.maksimov.viewcontroller;

import com.diploma.maksimov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BossController {

    @Autowired
    private UserService userService;

    @GetMapping("/boss")
    public String boss() {
        return "boss";
    }

}
