package com.example.yanolza.repository;

import com.example.yanolza.entity.PartyPeople;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyPeopleReRepository extends JpaRepository<PartyPeople,Long> {
}
