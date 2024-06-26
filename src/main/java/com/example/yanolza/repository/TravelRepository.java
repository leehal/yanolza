package com.example.yanolza.repository;

import com.example.yanolza.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelRepository extends JpaRepository<Travel,Long> {
    // Optional, findByAll, save는 안에 쓰지 않음
}

// findById : PK값으로 조회