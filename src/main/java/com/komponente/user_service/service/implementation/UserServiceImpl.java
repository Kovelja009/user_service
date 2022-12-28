package com.komponente.user_service.service.implementation;

import com.komponente.user_service.dto.ClientCreateDto;
import com.komponente.user_service.dto.ManagerCreateDto;
import com.komponente.user_service.dto.UserCreateDto;
import com.komponente.user_service.dto.UserDto;
import com.komponente.user_service.exceptions.ForbiddenException;
import com.komponente.user_service.exceptions.NotFoundException;
import com.komponente.user_service.mapper.UserMapper;
import com.komponente.user_service.model.User;
import com.komponente.user_service.repository.UserRepository;
import com.komponente.user_service.security.service.TokenService;
import com.komponente.user_service.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private TokenService tokenService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, TokenService tokenService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::userToUserDto);

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

    private String generateActivationCode() {
        String url = "http://localhost:8080/api/user/activate/";
        Integer code = (int) (Math.random() * 1000000);
        url += code.toString();
        return url;
    }

}
