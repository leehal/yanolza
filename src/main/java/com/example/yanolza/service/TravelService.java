package com.example.yanolza.service;

import com.example.yanolza.dto.TravelDto;
import com.example.yanolza.entity.Travel;
import com.example.yanolza.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TravelService {
    private final TravelRepository travelRepository;

    // save
    public void travelInsert(TravelDto travelDto) {
        Travel travel = Travel.builder()
                .tname(travelDto.getTname())
                .taddr(travelDto.getTaddr())
                .book(travelDto.isBook())
                .tprice(travelDto.getTprice())
                .build();
        travelRepository.save(travel);
    }

    // 모든 여행 전체 조회
    public List<TravelDto> selectAllTravels() {
        List<Travel> travels = travelRepository.findAll();
        List<TravelDto> dtos = new ArrayList<>();
        for (Travel travel : travels) {
            TravelDto trip = TravelDto.of(travel);
            dtos.add(trip);
        }
        return dtos;
    }
}
