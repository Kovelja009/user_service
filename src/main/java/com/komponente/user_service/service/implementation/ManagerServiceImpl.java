package com.komponente.user_service.service.implementation;

import com.komponente.user_service.dto.ManagerCreateDto;
import com.komponente.user_service.dto.ManagerDto;
import com.komponente.user_service.dto.UserCreateDto;
import com.komponente.user_service.exceptions.NotFoundException;
import com.komponente.user_service.mapper.UserMapper;
import com.komponente.user_service.model.Client;
import com.komponente.user_service.model.Manager;
import com.komponente.user_service.repository.ManagerRepository;
import com.komponente.user_service.repository.UserRepository;
import com.komponente.user_service.service.ManagerService;
import com.komponente.user_service.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManagerServiceImpl implements ManagerService {
    private ManagerRepository managerRepository;
    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserService userService;

    public ManagerServiceImpl(ManagerRepository managerRepository, UserRepository userRepository, UserMapper userMapper, UserService userService) {
        this.managerRepository = managerRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Override
    public Page<ManagerDto> findAll(Pageable pageable) {
        return managerRepository.findAll(pageable).map(userMapper::managerToManagerDto);
    }

    @Override
    public ManagerDto addManager(ManagerCreateDto managerCreateDto) {
        UserCreateDto userCreateDto = userMapper.managerCreateDtoToUserCreateDto(managerCreateDto);
        userService.addUser(userCreateDto);
        Manager manager = userMapper.managerCreateDtoToManager(managerCreateDto);
        managerRepository.save(manager);
        return userMapper.managerToManagerDto(manager);
    }

    @Override
    public boolean deleteManager(String username) {
        Optional<Manager> manager = managerRepository.findByUsername(username);
        if(manager.isPresent()) {
            managerRepository.delete(manager.get());
            return true;
        }else
            throw new NotFoundException("Manager not found");
    }
}
