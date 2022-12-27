package com.komponente.user_service.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ClientCreateDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd-mm-yyyy")
    private Date dateOfBirth;
    private String passportNumber;
}
