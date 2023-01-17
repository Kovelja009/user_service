package com.komponente.user_service.service;

import com.komponente.user_service.dto.ManagerCreateDto;
import com.komponente.user_service.dto.ManagerDto;
import com.komponente.user_service.dto.UserDto;

import java.sql.Date;

public interface ManagerService {
    ManagerDto addManager(ManagerCreateDto managerCreateDto);
    boolean deleteManager(String username);
    String changeCompany(Long id, String company);
    ManagerDto updateManager(Long id, ManagerCreateDto managerCreateDto);
    Date changeDate (Long idFromToken, Date date);
    String getCompany(Long userId);

    UserDto getByCompany(Long companyId);
}

