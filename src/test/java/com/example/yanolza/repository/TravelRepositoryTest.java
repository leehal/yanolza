package com.example.yanolza.repository;

import com.example.yanolza.entity.Travel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
@TestPropertySource(locations = "classpath:application-test.properties")
class TravelRepositoryTest {
    @Autowired
    TravelRepository travelRepository;

    public void createTravel() {
        Travel travel = Travel.builder()
                .tname("가리왕산")
                .taddr("강원도 정선군 정선읍 회동리ㆍ북평면, 평창군 진부면")
                .book(false)
                .tprice("0")
                .info("산의 모습이 큰 가리(벼나 나무를 쌓은 더미)같다고 하여 명명. 선정동강(東江)에 흘러드는 오대천과 조양강의 발원지")
                .course("가리왕산 자연휴양림-안부 전 갈림길-안부-상봉 정상 (총 2시간 10분)")
                .guide("활엽수 극상림 분포. 전국적인 산나물 자생지")
                .timage("")
                .build();
        travelRepository.save(travel);
    }

    @Test
    @DisplayName("전체 여행 조회")
    public void selectAllTravel() {
        createTravel();
        List<Travel> travels = travelRepository.findAll();
        for (Travel travel : travels) {
            log.info("결과 : " + travel);
        }
    }
}

