package com.nicholas.app.Repositories; 
import org.springframework.data.jpa.repository.JpaRepository;

import com.nicholas.app.Entities.User;

import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}