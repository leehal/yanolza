package com.example.yanolza.repository;

import com.example.yanolza.entity.Dibs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DibsRepository extends JpaRepository<Dibs,Long> {
    Optional<Dibs> findByDibsNick(String dibsNick);
}
