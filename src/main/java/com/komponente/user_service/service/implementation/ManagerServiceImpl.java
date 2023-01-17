package com.komponente.user_service.service.implementation;

import com.komponente.user_service.company_sync_comm.dto.CompanyIdDto;
import com.komponente.user_service.dto.*;
import com.komponente.user_service.exceptions.ForbiddenException;
import com.komponente.user_service.exceptions.NotFoundException;
import com.komponente.user_service.mapper.UserMapper;
import com.komponente.user_service.model.Manager;
import com.komponente.user_service.model.User;
import com.komponente.user_service.repository.ManagerRepository;
import com.komponente.user_service.repository.UserRepository;
import com.komponente.user_service.service.ManagerService;
import com.komponente.user_service.service.UserService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ManagerServiceImpl implements ManagerService {
    private ManagerRepository managerRepository;
    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserService userService;
    private RestTemplate reservationServiceRestTemplate;

    //  first check if manager company is valid
    @Override
    public ManagerDto addManager(ManagerCreateDto managerCreateDto) {
        CompanyIdDto companyIdDto = getCompanyId(managerCreateDto.getCompany());
        if(!validCompany(companyIdDto))
            throw new NotFoundException("Company " + managerCreateDto.getCompany() + " is not valid!");
        UserCreateDto userCreateDto = userMapper.managerCreateDtoToUserCreateDto(managerCreateDto);
        userService.addUser(userCreateDto);
        Manager manager = userMapper.managerCreateDtoToManager(managerCreateDto, companyIdDto.getId());
        managerRepository.save(manager);
        return userMapper.managerToManagerDto(manager, managerCreateDto.getCompany());
    }

    @Override
    public ManagerDto updateManager(Long id, ManagerCreateDto managerCreateDto) {
        // if user update is not valid it will throw exception
        try {
            userService.update(id, userMapper.managerCreateDtoToUserCreateDto(managerCreateDto));
        }catch (Exception e){
            throw new NotFoundException("Invalid user update");
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
        Manager manager = managerRepository.findByUserId(userRepository.findById(id).get());
        changeCompany(id, managerCreateDto.getCompany()); // checks if company is valid
        manager.setUser(userRepository.findById(id).get());
        manager.setStartDate(managerCreateDto.getStartDate());
        managerRepository.save(manager);
        return userMapper.managerToManagerDto(manager, managerCreateDto.getCompany());
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
    public String changeCompany(Long id, String company) {
        // if company is not valid it will throw exception
        CompanyIdDto companyIdDto = getCompanyId(company);
        if(companyIdDto == null)
            throw new NotFoundException("Company " + company + " does not exist!");
        Optional<Manager> company_manager = managerRepository.findByCompany(companyIdDto.getId());
        if(company_manager.isPresent() && !company_manager.get().getUser().getId().equals(id))
            throw new ForbiddenException("Company " + company + " is already taken!");
        ////////////////////////////////////////////////////////////////////////////////////////////
        Manager manager = managerRepository.findByUserId(userRepository.findById(id).get());
        manager.setCompany(companyIdDto.getId());
        managerRepository.save(manager);
        return company;
    }

    private boolean validCompany(CompanyIdDto companyIdDto){
        if(companyIdDto == null)
            return false;
        Optional<Manager> manager = managerRepository.findByCompany(companyIdDto.getId());

        return !manager.isPresent();
    }

    private CompanyIdDto getCompanyId(String company){
        ResponseEntity<CompanyIdDto> response = reservationServiceRestTemplate.exchange("/company/get_company?name="+company, HttpMethod.GET, null, CompanyIdDto.class);
        return response.getBody();
    }

    @Override
    public Date changeDate(Long id, Date date) {
        Manager manager = managerRepository.findByUserId(userRepository.findById(id).get());
        manager.setStartDate(date);
        managerRepository.save(manager);
        return date;
    }

    @Override
    public String getCompany(Long userId) {
        Manager manager = managerRepository.findByUserId(userRepository.findById(userId).get());
        return manager.getCompany().toString();
    }

    @Override
    public UserDto getByCompany(Long companyId) {
        Manager manager = managerRepository.findByCompany(companyId).get();
        return userMapper.userToUserDto(manager.getUser());
    }
}
