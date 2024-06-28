package com.example.yanolza.repository;

import com.example.yanolza.entity.Member;
import com.example.yanolza.entity.Review;
import com.example.yanolza.entity.Travel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    TravelRepository travelRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("save 실험")
    public void createMember() {
        Long uno = (long)3;
        Long pno = (long)4;
        Optional<Member> member = memberRepository.findById(uno);
        Optional<Travel> travel = travelRepository.findById(pno);
        if(member.isPresent()){

        Review review = Review.builder()
                .rate(5)
                .rcontent("친구들과 함께 간 여행 너무 좋은 추억 남기고 갑니다")
                .rdate(LocalDateTime.now())
                .title("좋은 추억 남기고 갑니다.")
                .rnick(member.get())
                .travel(travel.get())
                .build();


        reviewRepository.save(review);
        }
    }
}