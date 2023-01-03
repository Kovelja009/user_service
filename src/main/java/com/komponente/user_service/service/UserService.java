package com.komponente.user_service.service;

import com.komponente.user_service.dto.RankDto;
import com.komponente.user_service.dto.UserCreateDto;
import com.komponente.user_service.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;

public interface UserService {
    Page<UserDto> findAll(Pageable pageable);
    UserDto addUser(UserCreateDto userCreateDto);
    boolean activate(String code);
    String loginUser(String username, String password);
    void shouldBanUser(String username, boolean ban);
    String updateUsername(Long id, String username);
    String updatePassword(Long id, String password);
    String updateFirstName(Long id, String firstName);
    String updateLastName(Long id, String lastName);
    String updateEmail(Long id, String email);
    String updatePhoneNumber(Long id, String phoneNumber);
    Date updateDateOfBirth(Long id, Date dateOfBirth);
}
