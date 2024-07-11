package com.example.yanolza.repository;

import com.example.yanolza.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TravelRepository extends JpaRepository<Travel,Long> {
    Optional<Travel> findByTno(Long tno);
}
