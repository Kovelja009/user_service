package com.komponente.user_service.mapper;

import com.komponente.user_service.dto.RankDto;
import com.komponente.user_service.model.Rank_discount;
import org.springframework.stereotype.Component;

@Component
public class RankMapper {
    public RankDto rankToRankDto(Rank_discount rank){
        RankDto rankDto = new RankDto();
        rankDto.setName(rank.getName());
        rankDto.setDiscount(rank.getDiscount());
        rankDto.setMinDays(rank.getMinDays());
        rankDto.setMaxDays(rank.getMaxDays());
        return rankDto;
    }

    public Rank_discount rankDtoToRank(RankDto rankDto){
        Rank_discount rankDiscount = new Rank_discount();
        rankDiscount.setName(rankDto.getName());
        rankDiscount.setDiscount(rankDto.getDiscount());
        rankDiscount.setMinDays(rankDto.getMinDays());
        rankDiscount.setMaxDays(rankDto.getMaxDays());
        return rankDiscount;
    }
}
