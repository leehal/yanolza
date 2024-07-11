package com.example.yanolza.service;

import com.example.yanolza.dto.TravelDto;
import com.example.yanolza.entity.Travel;
import com.example.yanolza.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, Object> selectAllTravels(int page, String category, String city, String name) {
        Pageable pageable = PageRequest.of(page,12);
        List<Travel> travels;
        if (category != null && city != null && name != null) {
            travels = travelRepository.findByTcategoryAndTaddrLikeAndTnameLike(category, city, name, pageable).getContent();
        } else if (category != null && city != null) {
            travels = travelRepository.findByTcategoryAndTaddrLike(category, city, pageable).getContent();
        } else if (city != null && name != null) {
            travels = travelRepository.findByTaddrLikeAndTnameLike(city, name, pageable).getContent();
        } else if (category != null && name != null) {
            travels = travelRepository.findByTcategoryAndTnameLike(category, name, pageable).getContent();
        } else if (category != null) {
            travels = travelRepository.findByTcategory(category, pageable).getContent();
        } else if (city != null) {
            travels = travelRepository.findByTaddrLike(city, pageable).getContent();
        } else {
            travels = travelRepository.findAll(pageable).getContent();
        }

        List<TravelDto> travelDtos = new ArrayList<>();
        for (Travel travel : travels) {
            TravelDto trip = TravelDto.of(travel);
            travelDtos.add(trip);
        }
        int cnt =travelRepository.findAll(pageable).getTotalPages();
        Map<String, Object> result = new HashMap<>();
        result.put("travels", travelDtos);
        result.put("totalPages", cnt);
        return result;
    }
}
