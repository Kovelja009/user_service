package com.komponente.user_service.repository;

import com.komponente.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User findByActivationCodeEndsWith(String code);
    Optional<User> findByUsernameAndPassword(String username, String password);

}

