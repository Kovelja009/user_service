package com.komponente.user_service.controller;

import com.komponente.user_service.dto.ClientCreateDto;
import com.komponente.user_service.dto.ClientDto;
import com.komponente.user_service.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {
    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<Page<ClientDto>> getAllClients(Pageable pageable) {
        return new ResponseEntity<>(clientService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ClientDto> addClient(@RequestBody @Valid ClientCreateDto clientCreateDto) {
        return new ResponseEntity<>(clientService.addClient(clientCreateDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteClient(@RequestParam String username) {
        clientService.deleteClient(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
