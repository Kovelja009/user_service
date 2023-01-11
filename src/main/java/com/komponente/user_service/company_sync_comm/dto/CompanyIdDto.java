package com.komponente.user_service.company_sync_comm.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyIdDto {
    @NotBlank
    Long id;
}
