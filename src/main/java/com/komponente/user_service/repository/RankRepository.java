package com.komponente.user_service.repository;

import com.komponente.user_service.model.Rank_discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Rank_discount, Long> {
    Rank_discount findByName(String name);

    Rank_discount findByMinDaysLessThanEqualAndMaxDaysGreaterThanEqual(int minDays, int maxDays);
}

