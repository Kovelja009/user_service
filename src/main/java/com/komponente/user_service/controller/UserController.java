package com.komponente.user_service.controller;

import com.komponente.user_service.security.CheckSecurity;
import com.komponente.user_service.security.service.TokenService;
import com.komponente.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private TokenService tokenService;

    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
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

    @PostMapping("/update_username")
    public ResponseEntity<String> updateUsername(@RequestHeader("Authorization") String authorization, @RequestBody @Valid String username) {
        return new ResponseEntity<>(userService.updateUsername(tokenService.getIdFromToken(authorization), username), HttpStatus.OK);
    }

    @PostMapping("/update_password")
    public ResponseEntity<String> updatePassword(@RequestHeader("Authorization") String authorization, @RequestBody @Valid String password) {
        return new ResponseEntity<>(userService.updatePassword(tokenService.getIdFromToken(authorization), password), HttpStatus.OK);
    }

    @PostMapping("/update_firstName")
    public ResponseEntity<String> updateFirstName(@RequestHeader("Authorization") String authorization, @RequestBody @Valid String firstName) {
        return new ResponseEntity<>(userService.updateFirstName(tokenService.getIdFromToken(authorization), firstName), HttpStatus.OK);
    }

    @PostMapping("/update_lastName")
    public ResponseEntity<String> updateLastName(@RequestHeader("Authorization") String authorization, @RequestBody @Valid String lastName) {
        return new ResponseEntity<>(userService.updateLastName(tokenService.getIdFromToken(authorization), lastName), HttpStatus.OK);
    }

    @PostMapping("/update_email")
    public ResponseEntity<String> updateEmail(@RequestHeader("Authorization") String authorization, @RequestBody @Valid String email) {
        return new ResponseEntity<>(userService.updateEmail(tokenService.getIdFromToken(authorization), email), HttpStatus.OK);
    }

    @PostMapping("/update_phone")
    public ResponseEntity<String> updatePhoneNumber(@RequestHeader("Authorization") String authorization, @RequestBody @Valid String phoneNumber) {
        return new ResponseEntity<>(userService.updatePhoneNumber(tokenService.getIdFromToken(authorization), phoneNumber), HttpStatus.OK);
    }

    @PostMapping("/update_birthday")
    public ResponseEntity<Date> updateDateOfBirth(@RequestHeader("Authorization") String authorization, @RequestBody @Valid Date dateOfBirth) {
        return new ResponseEntity<>(userService.updateDateOfBirth(tokenService.getIdFromToken(authorization), dateOfBirth), HttpStatus.OK);
    }

}
