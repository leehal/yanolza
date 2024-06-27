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
                .tname("가령산")
                .taddr("충청북도 괴산군")
                .book(false)
                .tprice(0)
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

