package com.komponente.user_service.controller;

import com.komponente.user_service.dto.ManagerCreateDto;
import com.komponente.user_service.dto.ManagerDto;
import com.komponente.user_service.service.ManagerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    private ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping
    public ResponseEntity<Page<ManagerDto>> getAllManagers(Pageable pageable) {
        return new ResponseEntity<>(managerService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ManagerDto> addManager(@RequestBody @Valid ManagerCreateDto managerCreateDto) {
        return new ResponseEntity<>(managerService.addManager(managerCreateDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteManager(@RequestParam String username) {
        managerService.deleteManager(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
