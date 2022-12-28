package com.komponente.user_service.repository;

import com.komponente.user_service.model.Client;
import com.komponente.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUserId(User userId);
    @Query(value = "select c.id, c.passport_number, c.rent_days, c.user_id from users.user u join users.client c on u.id = c.user_id where u.username=?1", nativeQuery = true)
    Optional<Client> findByUsername(String username);
}
