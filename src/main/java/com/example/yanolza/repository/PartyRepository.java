package com.example.yanolza.repository;

import com.example.yanolza.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartyRepository extends JpaRepository<Party,Long> {
    Optional<Party> findByPname(String pname);

//    List<>
}
