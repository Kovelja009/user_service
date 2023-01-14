package com.komponente.user_service.controller;

import com.komponente.user_service.dto.ClientCreateDto;
import com.komponente.user_service.dto.ClientDto;
import com.komponente.user_service.dto.RankDto;
import com.komponente.user_service.security.service.TokenService;
import com.komponente.user_service.service.ClientService;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    private ClientService clientService;
    private TokenService tokenService;

    public ClientController(ClientService clientService, TokenService tokenService) {
        this.clientService = clientService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients() {
        return new ResponseEntity<>(clientService.findAll(), HttpStatus.OK);
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

    @PostMapping("/update")
    public ResponseEntity<ClientDto> updateClient(@RequestHeader("Authorization") String authorization, @RequestBody @Valid ClientCreateDto clientCreateDto) {
        return new ResponseEntity<>(clientService.updateClient(tokenService.getIdFromToken(authorization),clientCreateDto), HttpStatus.OK);
    }

    @PostMapping("/update_passport")
    public ResponseEntity<String> updatePassportNumber(@RequestHeader("Authorization") String authorization, @RequestBody @Valid String passportNumber) {
        return new ResponseEntity<>(clientService.updatePassportNumber(tokenService.getIdFromToken(authorization), passportNumber), HttpStatus.OK);
    }

    @PostMapping("/update_rent_days")
    public ResponseEntity<Integer> updateRentDays(@RequestParam Long user_id, @RequestParam Integer rentDays) {
        return new ResponseEntity<>(clientService.updateRentDays(user_id, rentDays), HttpStatus.OK);
    }

    @GetMapping("/get_rank")
    public ResponseEntity<RankDto> getRankByUserId(@RequestParam Long user_id) {
        return new ResponseEntity<>(clientService.getRankByUserId(user_id), HttpStatus.OK);
    }
}
