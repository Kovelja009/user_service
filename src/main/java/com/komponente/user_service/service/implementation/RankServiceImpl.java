package com.komponente.user_service.service.implementation;

import com.komponente.user_service.dto.RankDto;
import com.komponente.user_service.mapper.RankMapper;
import com.komponente.user_service.model.Rank_discount;
import com.komponente.user_service.repository.RankRepository;
import com.komponente.user_service.service.RankService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RankServiceImpl implements RankService {
    private RankRepository rankRepository;
    private RankMapper rankMapper;

    public RankServiceImpl(RankRepository rankRepository, RankMapper rankMapper) {
        this.rankRepository = rankRepository;
        this.rankMapper = rankMapper;
    }

    @Override
    public Page<RankDto> findAll(Pageable pageable) {
        return rankRepository.findAll(pageable).map(rankMapper::rankToRankDto);
    }

    @Override
    public RankDto addRank(RankDto rankDto) {
        Rank_discount rankDiscount = rankMapper.rankDtoToRank(rankDto);
        rankRepository.save(rankDiscount);
        return rankDto;
    }

    @Override
    public RankDto getRankByDays(int days) {
        Optional<Rank_discount> rankDiscount = rankRepository.findByMinDaysLessThanEqualAndMaxDaysGreaterThanEqual(days, days);
        if(rankDiscount.isPresent())
            return rankMapper.rankToRankDto(rankDiscount.get());
        RankDto rankDto = new RankDto();
        rankDto.setDiscount(0);
        rankDto.setName("No rank");
        return rankDto;
    }
}
