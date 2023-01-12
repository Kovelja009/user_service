package com.komponente.user_service.service.implementation;

import com.komponente.user_service.dto.ClientCreateDto;
import com.komponente.user_service.dto.ClientDto;
import com.komponente.user_service.dto.RankDto;
import com.komponente.user_service.dto.UserCreateDto;
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
import com.komponente.user_service.service.RankService;
import com.komponente.user_service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Page<ClientDto> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable).map(userMapper::clientToClientDto);
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
    public ClientDto update(Long id, ClientCreateDto clientCreateDto) {
        Client client = clientRepository.findByUser(userRepository.findById(id).get()).get();
        if(!clientRepository.findByPassportNumber(clientCreateDto.getPassportNumber()).isPresent())
            client.setPassportNumber(clientCreateDto.getPassportNumber());
        userService.update(id, userMapper.clientCreateDtoToUserCreateDto(clientCreateDto));
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
        Client client = clientRepository.findByUser(userRepository.findById(id).get()).get();
        if(clientRepository.findByPassportNumber(passportNumber).isPresent())
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
