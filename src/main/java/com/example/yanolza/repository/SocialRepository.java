package com.example.yanolza.repository;

import com.example.yanolza.entity.Social;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialRepository extends JpaRepository<Social, Long> {

    Optional<Social> findByMid(String mid);

}
