package com.example.yanolza.repository;

import com.example.yanolza.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email); //optional null 방지
    Optional<Member> findByMid(String email); //optional null 방지
    Optional<Member> findByNick(String email); //optional null 방지
    List<Member> findAll();
    Optional<Member> findByEmailAndPwd(String email, String pwd);
    boolean existsByEmail(String email);
    boolean existsByNick(String nick);
    boolean existsByMid(String id);
    boolean existsByRefreshToken(String refreshToken);
}
