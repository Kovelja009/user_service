package com.komponente.user_service.service;

import com.komponente.user_service.dto.ClientCreateDto;
import com.komponente.user_service.dto.ClientDto;
import com.komponente.user_service.dto.RankDto;

import java.util.List;

public interface ClientService {
    List<ClientDto> findAll();
    ClientDto addClient(ClientCreateDto clientCreateDto);

    ClientDto updateClient(Long id, ClientCreateDto clientCreateDto);
    boolean deleteClient(String username);
    String updatePassportNumber(Long id, String passportNumber);
    Integer updateRentDays(Long id, Integer rentDays);
    RankDto getRankByUserId(Long id);

}
