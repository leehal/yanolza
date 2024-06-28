package com.example.yanolza.service;

import com.example.yanolza.dto.ReviewDto;
import com.example.yanolza.entity.Review;
import com.example.yanolza.entity.Travel;
import com.example.yanolza.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    //addr이 같은 review 리스트 보여주기
    public List<ReviewDto> findByTaddr(String addr) {
        List<ReviewDto> list = new ArrayList<>();
        List<Review> reviewList = reviewRepository.findByTravel_Taddr(addr);
        for(Review e:reviewList){
            list.add(ReviewDto.of(e));
        }
        return list;
    }

}
