package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import com.example.bankcards.enums.CardStatus;
import com.example.bankcards.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Card entity.
 */
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByCardNumberEncrypted(String cardNumberEncrypted);
    Page<Card> findAllByOwner(User owner, Pageable pageable);
    Page<Card> findAllByOwnerAndStatus(User owner, CardStatus status, Pageable pageable);
    List<Card> findAllByOwner(User owner);
    Page<Card> findAllByStatus(CardStatus status, Pageable pageable);
    Page<Card> findAllByOwnerAndCardNumberEncryptedContainingOrOwnerAndStatus(
            User owner, String cardNumberEncrypted, User owner2, CardStatus status, Pageable pageable
    );
}
