package com.example.yanolza.repository;

import com.example.yanolza.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// DAO 같은 느낌
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email); //optional null 방지
    List<Member> findAll();
    Optional<Member> findByEmailAndPwd(String email, String pwd);
    boolean existsByEmail(String email);
}