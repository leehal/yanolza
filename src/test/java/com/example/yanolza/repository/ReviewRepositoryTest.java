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
    public void createMember(int pno, int uno, String content, String title, int trate) {
        Long pno1 = (long) pno;
        Long uno1 = (long) uno;
        Optional<Member> member = memberRepository.findById(uno1);
        Optional<Travel> travel = travelRepository.findById(pno1);
        if(member.isPresent()){

        Review review = Review.builder()
                .rate(trate)
                .rcontent(content)
                .rdate(LocalDateTime.now())
                .title(title)
                .rnick(member.get())
                .travel(travel.get())
                .build();


        reviewRepository.save(review);
        }
    }

    @Test
    public void saves(){
        createMember(4, 2, "전망이 너무 좋아요 다시 오고 싶어요 추천합니다!","전망 최고",5);
    }
}