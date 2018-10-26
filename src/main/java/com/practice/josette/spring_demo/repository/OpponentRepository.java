package com.practice.josette.spring_demo.repository;

import com.practice.josette.spring_demo.model.Opponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpponentRepository extends JpaRepository<Opponent, Long> {
    Opponent findByName(String name);
}
