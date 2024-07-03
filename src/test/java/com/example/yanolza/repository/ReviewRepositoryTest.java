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
    public void createReview(int tno, int uno, String content, String title, int trate) {
        Long tno1 = (long) tno;
        Long uno1 = (long) uno;
        Optional<Member> member = memberRepository.findById(uno1);
        log.info(member.toString());
        Optional<Travel> travel = travelRepository.findById(tno1);
        log.info(travel.toString());
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
        createReview(4, 52, "전망이 너무 좋아요 다시 오고 싶어요 추천합니다!","전망 최고",5);
        createReview(4, 52, "오랜만에 여행이라 설레는 맘으로 기대하고 갔는데 볼 것도 없고 너무 별로에요 최악이에요","최악의 여행지",1);
        createReview(4, 52, "후기 보고 걱정스러운 마음이었는데, 막상 가보니 정말 좋았어요 후기 다신 분이 예민한듯요!! 저는 너무 좋았어요!","감사합니다!!",5);
        createReview(4, 52, "기대 없이 갔는데 좋았어요","짱",5);
        createReview(4, 52, "그냥 그저 그랬어요","그냥",3);
        createReview(4, 52, "할인해줘서 남기는 후기","후기 남기면",5);
    }

    @Test
    @DisplayName("리뷰 수정 테스트")
    public void update() {
        Long no = (long) 64;
        Long tno = (long) 4;
        Long uno = (long) 52;
        Optional<Travel> travel = travelRepository.findById(tno);
        Optional<Member> member = memberRepository.findById(uno);


        Review review = Review.builder()
                .rno(no)
                .rate(2)
                .rcontent("Junit테스트 확인용")
                .title("Junit 테스트입니다.")
                .rdate(LocalDateTime.now())
                .rnick(member.get())
                .travel(travel.get())
                .build();
        reviewRepository.save(review);

    }
}