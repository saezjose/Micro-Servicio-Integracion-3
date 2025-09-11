package com.tomas.chat_microservice.repository;

import com.tomas.chat_microservice.model.UserTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepositoryTemp extends JpaRepository<UserTemp, Long> {
    Optional<UserTemp> findByEmail(String email);
    boolean existsByEmail(String email);
}
