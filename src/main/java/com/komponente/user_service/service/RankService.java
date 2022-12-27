package com.komponente.user_service.service;

import com.komponente.user_service.dto.RankDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RankService {
    Page<RankDto> findAll(Pageable pageable);
    RankDto addRank(RankDto rankDto);
    RankDto getRankByDays(int days);
}
