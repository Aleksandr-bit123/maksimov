package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.db.entity.UserEntity;
import com.diploma.maksimov.dto.User;
import com.diploma.maksimov.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class RegistrationController {

    private final UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> addUser(@RequestBody User user) {
       UserEntity userEntity = objectMapper.convertValue(user, UserEntity.class);
        if (!user.getPassword().equals(user.getPasswordConfirm())){
            return new ResponseEntity<>("Пароли не совпадают",HttpStatus.I_AM_A_TEAPOT);
        }
        if (!userService.saveUser(userEntity)){
            return new ResponseEntity<>("Пользователь с таки именем уже существует",HttpStatus.I_AM_A_TEAPOT);
        }
        return new ResponseEntity<>("Пользователь создан",HttpStatus.CREATED);
    }
}
