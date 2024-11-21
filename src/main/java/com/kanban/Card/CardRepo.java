package com.kanban.Card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepo extends JpaRepository<Card, Long> {
    Optional<Card> findById(Long id);

    List<Card> findByUserId(Long userId);
}