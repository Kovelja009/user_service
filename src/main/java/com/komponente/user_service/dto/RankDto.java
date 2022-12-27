package com.komponente.user_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankDto {
    private String name;
    private int minDays;
    private int maxDays;
    private int discount;
}
