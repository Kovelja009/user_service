package com.komponente.user_service.service.implementation;

import com.komponente.user_service.dto.ManagerCreateDto;
import com.komponente.user_service.dto.ManagerDto;
import com.komponente.user_service.dto.UserCreateDto;
import com.komponente.user_service.exceptions.NotFoundException;
import com.komponente.user_service.mapper.UserMapper;
import com.komponente.user_service.model.Manager;
import com.komponente.user_service.repository.ManagerRepository;
import com.komponente.user_service.repository.UserRepository;
import com.komponente.user_service.security.service.TokenService;
import com.komponente.user_service.service.ManagerService;
import com.komponente.user_service.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
public class ManagerServiceImpl implements ManagerService {
    private ManagerRepository managerRepository;
    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserService userService;
    private TokenService tokenService;

    public ManagerServiceImpl(ManagerRepository managerRepository, UserRepository userRepository, UserMapper userMapper, UserService userService, TokenService tokenService) {
        this.managerRepository = managerRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userService = userService;
        this.tokenService = tokenService;
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

    @Override
    public Date changeDate(Long id, Date date) {
        Manager manager = managerRepository.findByUsername(userRepository.findById(id).get().getUsername()).get();
        manager.setStartDate(date);
        managerRepository.save(manager);
        return date;
    }

    @Override
    public String changeCompany(Long id, String company) {
        if(managerRepository.findByCompany(company).isPresent())
            throw new NotFoundException("Company already exists");
        Manager manager = managerRepository.findByUsername(userRepository.findById(id).get().getUsername()).get();
        manager.setCompany(company);
        managerRepository.save(manager);
        return company;
    }
}
