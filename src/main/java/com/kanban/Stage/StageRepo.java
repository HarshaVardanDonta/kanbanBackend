package com.kanban.Stage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageRepo extends JpaRepository<Stage, Long> {
    List<Stage> findByUserId(Long userId);
}
