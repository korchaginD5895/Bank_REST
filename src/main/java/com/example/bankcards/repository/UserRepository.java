package com.example.bankcards.repository;

import com.example.bankcards.entity.User;
import com.example.bankcards.enums.Role;
import com.example.bankcards.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository for User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Page<User> findAllByRole(Role role, Pageable pageable);
    Page<User> findAllByStatus(UserStatus status, Pageable pageable);
    Page<User> findAllByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String username, String fullName, String email, Pageable pageable
    );
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
