package com.komponente.user_service.service;

import com.komponente.user_service.dto.ClientCreateDto;
import com.komponente.user_service.dto.ManagerCreateDto;
import com.komponente.user_service.dto.UserCreateDto;
import com.komponente.user_service.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDto> findAll(Pageable pageable);
    UserDto addUser(UserCreateDto userCreateDto);
    boolean activate(String code);
    String loginUser(String username, String password);
}
