package com.komponente.user_service.repository;

import com.komponente.user_service.model.Manager;
import com.komponente.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Manager findByUserId(Long userId);

    @Query(value = "select m.id, m.company, m.start_date, m.user_id  from users.user u join users.manager m on u.id = m.user_id where u.username=?1", nativeQuery = true)
    Optional<Manager> findByUsername(String username);
    Optional<Manager> findByCompany(Long company);
}
