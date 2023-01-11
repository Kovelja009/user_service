package com.komponente.user_service.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ClientCreateDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date dateOfBirth;
    @NotBlank
    private String passportNumber;
}
