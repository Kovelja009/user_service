package com.komponente.user_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String passportNumber;
    private int rentDays;
}
