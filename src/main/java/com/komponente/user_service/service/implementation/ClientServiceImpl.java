package com.komponente.user_service.service.implementation;

import com.komponente.user_service.dto.ClientCreateDto;
import com.komponente.user_service.dto.ClientDto;
import com.komponente.user_service.dto.UserCreateDto;
import com.komponente.user_service.mapper.UserMapper;
import com.komponente.user_service.model.Client;
import com.komponente.user_service.repository.ClientRepository;
import com.komponente.user_service.repository.UserRepository;
import com.komponente.user_service.service.ClientService;
import com.komponente.user_service.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserService userService;

    public ClientServiceImpl(ClientRepository clientRepository, UserMapper userMapper, UserRepository userRepository, UserService userService) {
        this.clientRepository = clientRepository;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public Page<ClientDto> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable).map(userMapper::clientToClientDto);
    }

    @Override
    public ClientDto addClient(ClientCreateDto clientCreateDto) {
        UserCreateDto userCreateDto = userMapper.clientCreateDtoToUserCreateDto(clientCreateDto);
        userService.addUser(userCreateDto);
        Client client = userMapper.clientCreateDtoToClient(clientCreateDto);
        clientRepository.save(client);
        return userMapper.clientToClientDto(client);
    }

    @Override
    public boolean deleteClient(String username) {
        try {
            Client client = clientRepository.findByUsername(username);
            clientRepository.delete(client);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}