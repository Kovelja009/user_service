package com.komponente.user_service.repository;

import com.komponente.user_service.model.Rank_discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankRepository extends JpaRepository<Rank_discount, Long> {
    Rank_discount findByName(String name);
    List<Rank_discount> findAll();

    Optional<Rank_discount> findByMinDaysLessThanEqualAndMaxDaysGreaterThanEqual(int minDays, int maxDays);
}

