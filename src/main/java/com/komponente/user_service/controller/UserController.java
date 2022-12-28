package com.komponente.user_service.controller;

import com.komponente.user_service.security.CheckSecurity;
import com.komponente.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/activate/{code}")
    public ResponseEntity<Boolean> activateUserAccount(@PathVariable("code") String code) {
        return new ResponseEntity<>(userService.activate(code), HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam @Valid String username, @RequestParam @Valid String password) {
        return new ResponseEntity<>(userService.loginUser(username, password), HttpStatus.OK);
    }

    @PostMapping("/ban")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<?> banUser(@RequestHeader("Authorization") String authorization, @RequestParam @Valid String username, @RequestParam @Valid boolean ban) {
        userService.shouldBanUser(username, ban);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
