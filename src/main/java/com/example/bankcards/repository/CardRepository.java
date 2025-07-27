package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import com.example.bankcards.enums.CardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    /**
     * List cards for a given user (by Keycloak ID), with pagination.
     */
    Page<Card> findByOwner_KeycloakId(String keycloakId, Pageable pageable);

    /**
     * Filter cards by owner and status.
     */
    Page<Card> findByOwner_KeycloakIdAndStatus(
            String keycloakId,
            CardStatus status,
            Pageable pageable
    );

    /**
     * Find a specific card by its ID and owner (to ensure ownership).
     */
    Optional<Card> findByIdAndOwner_KeycloakId(Long id, String keycloakId);

    /**
     * Look up a card by its encrypted number.
     */
    Optional<Card> findByCardNumberEncrypted(String encryptedNumber);
}
