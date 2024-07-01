package com.example.yanolza.repository;

import com.example.yanolza.entity.Review;
import com.example.yanolza.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelRepository extends JpaRepository<Travel,Long> {
    // Optional, findByAll, save는 안에 쓰지 않음
    Optional<Travel> findByTno(Long Tno);
}

// findById : PK값으로 조회