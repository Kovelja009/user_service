package com.komponente.user_service.repository;

import com.komponente.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByActivationCodeEndsWith(String code);

}

