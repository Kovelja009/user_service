package com.komponente.user_service.service.implementation;

import com.komponente.user_service.company_sync_comm.dto.CompanyIdDto;
import com.komponente.user_service.dto.ManagerCreateDto;
import com.komponente.user_service.dto.ManagerDto;
import com.komponente.user_service.dto.UserCreateDto;
import com.komponente.user_service.exceptions.NotFoundException;
import com.komponente.user_service.mapper.UserMapper;
import com.komponente.user_service.model.Manager;
import com.komponente.user_service.repository.ManagerRepository;
import com.komponente.user_service.repository.UserRepository;
import com.komponente.user_service.service.ManagerService;
import com.komponente.user_service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        CompanyIdDto companyIdDto = getCompanyId(company);
        if(!validCompany(companyIdDto))
            throw new NotFoundException("Company " + company + " is not valid!");
        Manager manager = managerRepository.findByUsername(userRepository.findById(id).get().getUsername()).get();
        manager.setCompany(companyIdDto.getId());
        managerRepository.save(manager);
        return company;
    }

    private boolean validCompany(CompanyIdDto companyIdDto){
        if(companyIdDto == null)
            return false;
        Optional<Manager> manager = managerRepository.findByCompany(companyIdDto.getId());
        return manager.isEmpty();
    }

    private CompanyIdDto getCompanyId(String company){
        ResponseEntity<CompanyIdDto> response = reservationServiceRestTemplate.exchange("/company/get_company?name="+company, HttpMethod.GET, null, CompanyIdDto.class);
        return response.getBody();
    }
}
