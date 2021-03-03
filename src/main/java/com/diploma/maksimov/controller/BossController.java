package com.diploma.maksimov.controller;

import com.diploma.maksimov.db.entity.UserEntity;
import com.diploma.maksimov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class BossController {

    @Autowired
    private UserService userService;

    @GetMapping("/boss")
    public String boss() {
        return "boss";
    }

}
