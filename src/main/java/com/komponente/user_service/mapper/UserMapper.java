package com.komponente.user_service.mapper;

import com.komponente.user_service.dto.*;
import com.komponente.user_service.model.Client;
import com.komponente.user_service.model.Manager;
import com.komponente.user_service.model.User;
import com.komponente.user_service.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private UserRepository userRepository;

    public UserMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Client clientCreateDtoToClient(ClientCreateDto clientCreateDto){
        Client client = new Client();
        client.setPassportNumber(clientCreateDto.getPassportNumber());
        client.setRentDays(0);
        client.setUser(userRepository.findByUsername(clientCreateDto.getUsername()));
        return client;
    }

    public Manager managerCreateDtoToManager(ManagerCreateDto managerCreateDto){
        Manager manager = new Manager();
        manager.setCompany(managerCreateDto.getCompany());
        manager.setStartDate(managerCreateDto.getStartDate());
        manager.setUser(userRepository.findByUsername(managerCreateDto.getUsername()));
        return manager;
    }

    public ClientDto clientToClientDto(Client client){
        ClientDto clientDto = new ClientDto();
        clientDto.setUsername(client.getUser().getUsername());
        clientDto.setFirstName(client.getUser().getFirstName());
        clientDto.setLastName(client.getUser().getLastName());
        clientDto.setEmail(client.getUser().getEmail());
        clientDto.setPassportNumber(client.getPassportNumber());
        clientDto.setRentDays(client.getRentDays());
        return clientDto;
    }

    public ManagerDto managerToManagerDto(Manager manager){
        ManagerDto managerDto = new ManagerDto();
        managerDto.setUsername(manager.getUser().getUsername());
        managerDto.setFirstName(manager.getUser().getFirstName());
        managerDto.setLastName(manager.getUser().getLastName());
        managerDto.setEmail(manager.getUser().getEmail());
        managerDto.setCompany(manager.getCompany());
        managerDto.setPhone(manager.getUser().getPhone());
        return managerDto;
    }

    public UserDto userToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public User userCreateDtoToUser(UserCreateDto userCreateDto){
        User user = new User();
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        user.setEmail(userCreateDto.getEmail());
        user.setPhone(userCreateDto.getPhone());
        user.setRole(userCreateDto.getRole());
        user.setDateOfBirth(userCreateDto.getDateOfBirth());
        return user;
    }

    public UserCreateDto clientCreateDtoToUserCreateDto(ClientCreateDto clientCreateDto) {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setUsername(clientCreateDto.getUsername());
        userCreateDto.setPassword(clientCreateDto.getPassword());
        userCreateDto.setFirstName(clientCreateDto.getFirstName());
        userCreateDto.setLastName(clientCreateDto.getLastName());
        userCreateDto.setEmail(clientCreateDto.getEmail());
        userCreateDto.setPhone(clientCreateDto.getPhone());
        userCreateDto.setRole("ROLE_CLIENT");
        userCreateDto.setDateOfBirth(clientCreateDto.getDateOfBirth());
        return userCreateDto;
    }

    public UserCreateDto managerCreateDtoToUserCreateDto(ManagerCreateDto managerCreateDto) {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setUsername(managerCreateDto.getUsername());
        userCreateDto.setPassword(managerCreateDto.getPassword());
        userCreateDto.setFirstName(managerCreateDto.getFirstName());
        userCreateDto.setLastName(managerCreateDto.getLastName());
        userCreateDto.setEmail(managerCreateDto.getEmail());
        userCreateDto.setPhone(managerCreateDto.getPhone());
        userCreateDto.setRole("ROLE_MANAGER");
        userCreateDto.setDateOfBirth(managerCreateDto.getDateOfBirth());
        return userCreateDto;
    }

}
