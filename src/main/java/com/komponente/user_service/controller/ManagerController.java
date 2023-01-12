package com.komponente.user_service.controller;

import com.komponente.user_service.dto.ClientCreateDto;
import com.komponente.user_service.dto.ClientDto;
import com.komponente.user_service.dto.ManagerCreateDto;
import com.komponente.user_service.dto.ManagerDto;
import com.komponente.user_service.security.service.TokenService;
import com.komponente.user_service.service.ManagerService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    private ManagerService managerService;
    private TokenService tokenService;

    public ManagerController(ManagerService managerService, TokenService tokenService) {
        this.managerService = managerService;
        this.tokenService = tokenService;
    }


    @PostMapping("/register")
    public ResponseEntity<ManagerDto> addManager(@RequestBody @Valid ManagerCreateDto managerCreateDto) {
        return new ResponseEntity<>(managerService.addManager(managerCreateDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ManagerDto> updateUser(@RequestHeader("Authorization") String authorization, @RequestBody @Valid ManagerCreateDto clientCreateDto) {
        return new ResponseEntity<>(managerService.update(tokenService.getIdFromToken(authorization),clientCreateDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteManager(@RequestParam String username) {
        managerService.deleteManager(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/change_company")
    public ResponseEntity<String> changeCompany(@RequestHeader("Authorization") String authorization, @RequestParam @Valid String company) {
        return new ResponseEntity<>(managerService.changeCompany(tokenService.getIdFromToken(authorization), company), HttpStatus.OK);
    }

    @PostMapping("/change_date")
    public ResponseEntity<Date> changeDate(@RequestHeader("Authorization") String authorization, @RequestParam @Valid Date date) {
        return new ResponseEntity<>(managerService.changeDate(tokenService.getIdFromToken(authorization), date), HttpStatus.OK);
    }
}
