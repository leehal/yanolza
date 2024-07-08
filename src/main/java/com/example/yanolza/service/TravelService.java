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
    public boolean travelInsert(TravelDto travelDto) {
        boolean isTrue = false;
        Travel travel = Travel.builder()
                .tname(travelDto.getTname())
                .taddr(travelDto.getTaddr())
                .tcategory(travelDto.getTcategory())
                .timage(travelDto.getTimage())
                .tprice(travelDto.getTprice()) // 5개
                .book(travelDto.isBook())
                .course(travelDto.getCourse())
                .homepage(travelDto.getHomepage())
                .guide(travelDto.getGuide())
                .info(travelDto.getInfo()) // 10개
                .main(travelDto.getMain())
                .phone(travelDto.getPhone())
                .season(travelDto.getSeason())
                .vehicle(travelDto.getVehicle())
                .time(travelDto.getTime()) // 15개
                .build();
        travelRepository.save(travel);
        isTrue = true;
        return isTrue;
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
