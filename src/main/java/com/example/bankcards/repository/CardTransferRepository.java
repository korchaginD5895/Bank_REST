package com.example.bankcards.repository;

import com.example.bankcards.entity.CardTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardTransferRepository extends JpaRepository<CardTransfer, Long> {

    /**
     * All transfers outgoing from the given card ID.
     */
    List<CardTransfer> findByFromCard_Id(Long fromCardId);

    /**
     * All transfers incoming to the given card ID.
     */
    List<CardTransfer> findByToCard_Id(Long toCardId);
}
