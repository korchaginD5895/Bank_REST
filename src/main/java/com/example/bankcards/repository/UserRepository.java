package com.example.bankcards.repository;

import com.example.bankcards.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by their Keycloak ID.
     */
    Optional<User> findByKeycloakId(String keycloakId);

    /**
     * Delete a user by their Keycloak ID.
     */
    void deleteByKeycloakId(String keycloakId);

    /**
     * Search users by email or phone (case-insensitive), paginated.
     */
    Page<User> findByEmailContainingIgnoreCaseOrPhoneContainingIgnoreCase(
            String emailPart,
            String phonePart,
            Pageable pageable
    );
}
