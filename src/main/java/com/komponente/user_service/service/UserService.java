package com.komponente.user_service.service;

import com.komponente.user_service.dto.UserCreateDto;
import com.komponente.user_service.dto.UserDto;
import com.komponente.user_service.dto.UserIdDto;

import java.sql.Date;
import java.util.List;

public interface UserService {
    List<UserDto> findAll();
    UserDto addUser(UserCreateDto userCreateDto);
    UserDto update(Long id, UserCreateDto userCreateDto);
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
    UserDto getUserById(Long id);

    UserIdDto getUserByUsername(String username);
}
