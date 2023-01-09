package com.komponente.user_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivationMailDTO {
    private String email;
    private String activationLink;
    private String firstName;
    private String lastName;
}
