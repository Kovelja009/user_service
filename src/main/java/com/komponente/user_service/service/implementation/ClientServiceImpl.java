package com.komponente.user_service.service.implementation;

import com.komponente.user_service.dto.*;
import com.komponente.user_service.exceptions.ForbiddenException;
import com.komponente.user_service.exceptions.NotFoundException;
import com.komponente.user_service.mapper.RankMapper;
import com.komponente.user_service.mapper.UserMapper;
import com.komponente.user_service.model.Client;
import com.komponente.user_service.model.Rank_discount;
import com.komponente.user_service.repository.ClientRepository;
import com.komponente.user_service.repository.RankRepository;
import com.komponente.user_service.repository.UserRepository;
import com.komponente.user_service.service.ClientService;
import com.komponente.user_service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserService userService;
    private RankRepository rankRepo;
    private RankMapper rankMapper;

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream().map(userMapper::clientToClientDto).collect(Collectors.toList());
    }

    @Override
    public ClientDto addClient(ClientCreateDto clientCreateDto) {
        if(clientRepository.findByPassportNumber(clientCreateDto.getPassportNumber()).isPresent())
            throw new ForbiddenException("Passport already exists");
        UserCreateDto userCreateDto = userMapper.clientCreateDtoToUserCreateDto(clientCreateDto);
        userService.addUser(userCreateDto);
        Client client = userMapper.clientCreateDtoToClient(clientCreateDto);
        clientRepository.save(client);
        return userMapper.clientToClientDto(client);
    }

    @Override
    public ClientDto updateClient(Long id, ClientCreateDto clientCreateDto) {
//        if user update is not valid it will throw exception
        try {
            userService.update(id, userMapper.clientCreateDtoToUserCreateDto(clientCreateDto));
        }catch (Exception e){
            throw new NotFoundException("Invalid user update");
        }

        Optional<Client> optional = clientRepository.findByUser(userRepository.findById(id).get());
        if(!optional.isPresent())
            throw new NotFoundException("Client not found");
        /////////////////////////////////////////////////////////

        Client client = optional.get();
        updatePassportNumber(id, clientCreateDto.getPassportNumber()); // checks whether passport_number is unique
        client.setUser(userRepository.findById(id).get());
        clientRepository.save(client);
        return userMapper.clientToClientDto(client);
    }

    @Override
    public boolean deleteClient(String username) {
        Optional<Client> client = clientRepository.findByUsername(username);
        if(client.isPresent()) {
            clientRepository.delete(client.get());
            return true;
        }else
            throw new NotFoundException("Client not found");
    }

    @Override
    public String updatePassportNumber(Long id, String passportNumber) {
//        whether passport_number is unique
        Client client = clientRepository.findByUser(userRepository.findById(id).get()).get();
        Optional<Client> passport = clientRepository.findByPassportNumber(passportNumber);
        if(passport.isPresent() && passport.get().getId() != client.getId())
            throw new ForbiddenException("Passport already exists");

        client.setPassportNumber(passportNumber);
        clientRepository.save(client);
        return passportNumber;
    }

    @Override
    public Integer updateRentDays(Long id, Integer rentDays) {
        Client client = clientRepository.findByUser(userRepository.findById(id).get()).get();
        int updatedRentDays = client.getRentDays() + rentDays;
        client.setRentDays(updatedRentDays);
        clientRepository.save(client);

        return updatedRentDays;
    }

    @Override
    public RankDto getRankByUserId(Long id) {
        Client client = clientRepository.findByUser(userRepository.findById(id).get()).get();
        Optional<Rank_discount> rankDiscount = rankRepo.findByMinDaysLessThanEqualAndMaxDaysGreaterThanEqual(client.getRentDays(),client.getRentDays());
        if(rankDiscount.isPresent())
            return rankMapper.rankToRankDto(rankDiscount.get());
        RankDto rankDto = new RankDto();
        rankDto.setDiscount(0);
        rankDto.setName("No rank");
        return rankDto;
    }
}
