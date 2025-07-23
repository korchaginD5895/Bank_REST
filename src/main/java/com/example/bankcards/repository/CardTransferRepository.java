package com.example.bankcards.repository;

import com.example.bankcards.entity.CardTransfer;
import com.example.bankcards.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository for CardTransfer entity.
 */
public interface CardTransferRepository extends JpaRepository<CardTransfer, Long> {
    Page<CardTransfer> findAllByFromCardOrToCard(Card fromCard, Card toCard, Pageable pageable);
    List<CardTransfer> findAllByFromCard(Card fromCard);
    List<CardTransfer> findAllByToCard(Card toCard);
}
