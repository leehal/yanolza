package com.example.yanolza.service;

import com.example.yanolza.constant.Authority;
import com.example.yanolza.dto.ReviewDto;
import com.example.yanolza.dto.TravelDto;
import com.example.yanolza.entity.Member;
import com.example.yanolza.entity.Review;
import com.example.yanolza.entity.Travel;
import com.example.yanolza.repository.MemberRepository;
import com.example.yanolza.repository.ReviewRepository;
import com.example.yanolza.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final TravelRepository travelRepository;
    private final MemberRepository memberRepository;

    //tno가 같은 review 리스트 보여주기
    public List<ReviewDto> findByTno(Long tno) {
        List<ReviewDto> list = new ArrayList<>();
        List<Review> reviewList = reviewRepository.findByTravel_Tno(tno);
        for (Review e : reviewList) {
            list.add(ReviewDto.of(e));
        }
        return list;
    }

    public boolean saveReview(ReviewDto reviewDto) {
        boolean isTrue = false;
        Long tno = (long)reviewDto.getTno();
        Optional<Travel> travel =travelRepository.findById(tno);
        Optional<Member> member = memberRepository.findByNick(reviewDto.getRnick());
        if(travel.isPresent()) {
            if (member.isPresent()) {
                Review review = Review.builder()
                        .title(reviewDto.getTitle())
                        .travel(travel.get())
                        .rcontent(reviewDto.getRcontent())
                        .rate(reviewDto.getRate())
                        .rnick(member.get())
                        .build();

                reviewRepository.save(review);
                isTrue = true;
            }
        }
        return isTrue;
    }


}
