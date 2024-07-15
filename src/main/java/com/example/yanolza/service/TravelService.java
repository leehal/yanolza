package com.example.yanolza.service;

import com.example.yanolza.dto.ImageDto;
import com.example.yanolza.dto.ReviewDto;
import com.example.yanolza.dto.TravelDto;
import com.example.yanolza.entity.Image;
import com.example.yanolza.entity.Member;
import com.example.yanolza.entity.Review;
import com.example.yanolza.entity.Travel;
import com.example.yanolza.repository.ReviewRepository;
import com.example.yanolza.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TravelService {
    private final TravelRepository travelRepository;
    private final MemberService memberService;
    private final ReviewRepository reviewRepository;
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
        if (city.equals("제주특별자치도")) {
            city = "제주도";
        } else if (city.equals("전북특별자치도")){
            city = "전북";
        }

        int cnt;
        if (!category.isEmpty() && !city.isEmpty() && !name.isEmpty()) {
            travels = travelRepository.findByTcategoryAndTaddrContainingAndTnameContaining(category, city, name, pageable).getContent();
            cnt = travelRepository.findByTcategoryAndTaddrContainingAndTnameContaining(category, city, name, pageable).getTotalPages();
        } else if (!category.isEmpty() && !city.isEmpty()) {
            travels = travelRepository.findByTcategoryAndTaddrContaining(category, city, pageable).getContent();
            cnt = travelRepository.findByTcategoryAndTaddrContaining(category, city, pageable).getTotalPages();
        } else if (!city.isEmpty() && !name.isEmpty()) {
            travels = travelRepository.findByTaddrContainingAndTnameContaining(city, name, pageable).getContent();
            cnt = travelRepository.findByTaddrContainingAndTnameContaining(city, name, pageable).getTotalPages();
        } else if (!category.isEmpty() && !name.isEmpty()) {
            travels = travelRepository.findByTcategoryAndTnameContaining(category, name, pageable).getContent();
            cnt = travelRepository.findByTcategoryAndTnameContaining(category, name, pageable).getTotalPages();
        } else if (!category.isEmpty()) {
            travels = travelRepository.findByTcategory(category, pageable).getContent();
            cnt = travelRepository.findByTcategory(category, pageable).getTotalPages();
        } else if (!city.isEmpty()) {
            travels = travelRepository.findByTaddrContaining(city, pageable).getContent();
            cnt = travelRepository.findByTaddrContaining(city, pageable).getTotalPages();
        } else if(!name.isEmpty()){
            travels = travelRepository.findByTnameContaining(name,pageable).getContent();
            cnt = travelRepository.findByTnameContaining(name,pageable).getTotalPages();
        } else{
            travels = travelRepository.findAll(pageable).getContent();
            cnt =travelRepository.findAll(pageable).getTotalPages();
        }

        List<TravelDto> travelDtos = new ArrayList<>();
        for (Travel travel : travels) {
            TravelDto trip = TravelDto.of(travel);
            travelDtos.add(trip);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("travels", travelDtos);
        result.put("totalPages", cnt);
        return result;
    }
    //내가 review쓴 여행 리스트 보여주기
    public TreeSet<TravelDto> myTravelList() {
        Member member = memberService.memberIdFindMember();
        TreeSet<TravelDto> travelDtoSet = new TreeSet<>();
        List<Review> reviewList = reviewRepository.findByRnick(member);
        for (Review e : reviewList) {
            if(travelRepository.findById(e.getTravel().getTno()).isPresent()){
                TravelDto  travelDto = TravelDto.of(travelRepository.findById(e.getTravel().getTno()).get());
                travelDtoSet.add(travelDto);
            }
        }
        return travelDtoSet;
    }
}
