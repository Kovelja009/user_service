package com.komponente.user_service.service;

import com.komponente.user_service.dto.ClientCreateDto;
import com.komponente.user_service.dto.ClientDto;
import com.komponente.user_service.dto.RankDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {
    Page<ClientDto> findAll(Pageable pageable);
    ClientDto addClient(ClientCreateDto clientCreateDto);
    boolean deleteClient(String username);
    String updatePassportNumber(Long id, String passportNumber);
    Integer updateRentDays(Long id, Integer rentDays);
    RankDto getRankByUserId(Long id);

}
