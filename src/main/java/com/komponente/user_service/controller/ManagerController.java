package com.komponente.user_service.controller;

import com.komponente.user_service.dto.ManagerCreateDto;
import com.komponente.user_service.dto.ManagerDto;
import com.komponente.user_service.dto.UserDto;
import com.komponente.user_service.security.CheckSecurity;
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

    @PostMapping("/update")
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<ManagerDto> updateManager(@RequestHeader("Authorization") String authorization, @RequestBody @Valid ManagerCreateDto clientCreateDto) {
        return new ResponseEntity<>(managerService.updateManager(tokenService.getIdFromToken(authorization),clientCreateDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<?> deleteManager(@RequestHeader("Authorization") String authorization, @RequestParam String username) {
        managerService.deleteManager(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/change_company")
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<String> changeCompany(@RequestHeader("Authorization") String authorization, @RequestParam @Valid String company) {
        return new ResponseEntity<>(managerService.changeCompany(tokenService.getIdFromToken(authorization), company), HttpStatus.OK);
    }

    @GetMapping("/get_company")
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<String> getCompany(@RequestHeader("Authorization") String authorization, @RequestParam @Valid Long user_id) {
        return new ResponseEntity<>(managerService.getCompany(user_id), HttpStatus.OK);
    }

    @GetMapping("manager_by_company")
    public ResponseEntity<UserDto> getManagerCompany(@RequestParam @Valid Long companyId) {
        return new ResponseEntity<>(managerService.getByCompany(companyId), HttpStatus.OK);
    }

    @PostMapping("/change_date")
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<Date> changeDate(@RequestHeader("Authorization") String authorization, @RequestParam @Valid Date date) {
        return new ResponseEntity<>(managerService.changeDate(tokenService.getIdFromToken(authorization), date), HttpStatus.OK);
    }
}
