package com.komponente.user_service.controller;

import com.komponente.user_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/activate/{code}")
    public ResponseEntity<Boolean> getDiscount(@PathVariable("code") String code) {
        return new ResponseEntity<>(userService.activate(code), HttpStatus.OK);
    }

}
