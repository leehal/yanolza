package com.example.yanolza.repository;

import com.example.yanolza.entity.Member;
import com.example.yanolza.entity.Review;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
//@Transactional
@Slf4j

class ReviewRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("save 실험")
    public void createMember() {
        Long uno = (long)2;
        Optional<Member> member = memberRepository.findById(uno);
        if(member.isPresent()){

        Review review = Review.builder()
                .rate(4)
                .rcontent("너무 재밌었어요")
                .rdate(LocalDateTime.now())
                .title("재미난 강릉 여행")
                .rnick(member.get())
//                .travel()

                .build();

        reviewRepository.save(review);
        }
    }
}