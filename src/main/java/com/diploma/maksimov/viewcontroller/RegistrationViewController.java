package com.diploma.maksimov.viewcontroller;

import com.diploma.maksimov.db.entity.UserEntity;
import com.diploma.maksimov.dto.User;
import com.diploma.maksimov.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationViewController {

    private final UserService userService;
    private static final String REG = "registration";
    private final ObjectMapper objectMapper;

    public RegistrationViewController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserEntity());

        return REG;
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return REG;
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return REG;
        }
        if (!userService.saveUser(objectMapper.convertValue(userForm, UserEntity.class))){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return REG;
        }

        return "redirect:/";
    }
}
