package com.komponente.user_service.service;

import com.komponente.user_service.dto.ManagerCreateDto;
import com.komponente.user_service.dto.ManagerDto;

import java.sql.Date;

public interface ManagerService {
    ManagerDto addManager(ManagerCreateDto managerCreateDto);
    boolean deleteManager(String username);
    Date changeDate(Long id, Date date);
    String changeCompany(Long id, String company);

}
