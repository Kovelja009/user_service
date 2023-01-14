package com.komponente.user_service.service;

import com.komponente.user_service.dto.RankDto;

import java.util.List;

public interface RankService {
    List<RankDto> findAll();
    RankDto addRank(RankDto rankDto);
    RankDto getRankByDays(int days);
}
