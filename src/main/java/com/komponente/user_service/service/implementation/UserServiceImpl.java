package com.komponente.user_service.service.implementation;

import com.komponente.user_service.dto.*;
import com.komponente.user_service.exceptions.ForbiddenException;
import com.komponente.user_service.exceptions.NotFoundException;
import com.komponente.user_service.mapper.UserMapper;
import com.komponente.user_service.model.User;
import com.komponente.user_service.repository.UserRepository;
import com.komponente.user_service.security.service.TokenService;
import com.komponente.user_service.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private TokenService tokenService;


    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::userToUserDto);

    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        return userMapper.userToUserDto(user.get());
    }

    @Override
    public UserIdDto getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        UserIdDto userIdDto = new UserIdDto();
        userIdDto.setId(user.get().getId());
        return userIdDto;
    }

    @Override
    public UserDto addUser(UserCreateDto userCreateDto) {
        if(userRepository.findByUsername(userCreateDto.getUsername()).isPresent())
            throw new ForbiddenException("Username already exists");

        User user = userMapper.userCreateDtoToUser(userCreateDto);
        user.setBanned(false);
        user.setActivationCode(generateActivationCode());
        userRepository.save(user);
//        TODO add notification sending through message broker
        // localhost/8080/api/user/activate/{code}
        //ActivationDto firstName lastName link: localhost:8080/api/register/tajCode kad klikne na to idemo u controller
        System.out.println(user.getActivationCode());
        return userMapper.userToUserDto(user);
    }

    @Override
    public boolean activate(String code) {
        try {
            User user = userRepository.findByActivationCodeEndsWith(code);
            user.setActivationCode("REGISTERED");
            userRepository.save(user);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public String loginUser(String username, String password) {
        //Try to find active user for specified credentials
        User user = userRepository
                .findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with username: %s and password: %s not found.", username,
                                password)));
        if(!user.getActivationCode().equals("REGISTERED"))
            throw new ForbiddenException("User is not activated");
        if(user.isBanned())
            throw new ForbiddenException("User is banned");

        //Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole());
        //Generate token
        return tokenService.generate(claims);
    }

    @Override
    public void shouldBanUser(String username, boolean ban) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(String.format("User with username: %s not found.", username)));
        user.setBanned(ban);
        userRepository.save(user);
    }

    private String generateActivationCode() {
        String url = "http://localhost:8080/api/user/activate/";
        Integer code = (int) (Math.random() * 1000000);
        url += code.toString();
        return url;
    }

    @Override
    public String updateUsername(Long id, String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isPresent())
            throw new ForbiddenException("Username already exists");
        User user = userRepository.findById(id).get();
        user.setUsername(username);
        userRepository.save(user);
        return username;
    }

    @Override
    public String updatePassword(Long id, String password) {
        User user = userRepository.findById(id).get();
        user.setPassword(password);
        userRepository.save(user);
        return password;
    }

    @Override
    public String updateFirstName(Long id, String firstName) {
        User user = userRepository.findById(id).get();
        user.setFirstName(firstName);
        userRepository.save(user);
        return firstName;
    }

    @Override
    public String updateLastName(Long id, String lastName) {
        User user = userRepository.findById(id).get();
        user.setLastName(lastName);
        userRepository.save(user);
        return lastName;
    }

    @Override
    public String updateEmail(Long id, String email) {
        User user = userRepository.findById(id).get();
        user.setEmail(email);
        userRepository.save(user);
        return email;
    }

    @Override
    public String updatePhoneNumber(Long id, String phoneNumber) {
        User user = userRepository.findById(id).get();
        user.setPhone(phoneNumber);
        userRepository.save(user);
        return phoneNumber;
    }

    @Override
    public Date updateDateOfBirth(Long id, Date dateOfBirth) {
        User user = userRepository.findById(id).get();
        user.setDateOfBirth(dateOfBirth);
        userRepository.save(user);
        return dateOfBirth;
    }
}
