package com.example.yanolza.repository;

import com.example.yanolza.constant.Social;
import com.example.yanolza.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMid(String mid);
    boolean existsByMid(String mid);
    boolean existsByMidAndSocial(String mid, Social social);

}
