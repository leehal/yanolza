package com.example.yanolza.repository;

import com.example.yanolza.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByNickname(String nickName);
}
