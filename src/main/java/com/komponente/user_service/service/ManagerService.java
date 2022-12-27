package com.komponente.user_service.service;

import com.komponente.user_service.dto.ManagerCreateDto;
import com.komponente.user_service.dto.ManagerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManagerService {
    Page<ManagerDto> findAll(Pageable pageable);
    ManagerDto addManager(ManagerCreateDto managerCreateDto);
    boolean deleteManager(String username);


}
