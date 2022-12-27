package com.komponente.user_service.service.implementation;

import com.komponente.user_service.dto.ClientCreateDto;
import com.komponente.user_service.dto.ManagerCreateDto;
import com.komponente.user_service.dto.UserCreateDto;
import com.komponente.user_service.dto.UserDto;
import com.komponente.user_service.mapper.UserMapper;
import com.komponente.user_service.model.User;
import com.komponente.user_service.repository.UserRepository;
import com.komponente.user_service.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::userToUserDto);

    }

    @Override
    public UserDto addUser(UserCreateDto userCreateDto) {
        User user = userMapper.userCreateDtoToUser(userCreateDto);
        user.setBanned(false);
        user.setActivationCode(generateActivationCode());
        userRepository.save(user);
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

    private String generateActivationCode() {
        String url = "http://localhost:8080/api/user/activate/";
        Integer code = (int) (Math.random() * 1000000);
        url += code.toString();
        return url;
    }

}
