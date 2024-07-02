package com.example.yanolza.service;

import com.example.yanolza.constant.Authority;
import com.example.yanolza.dto.ImageDto;
import com.example.yanolza.dto.ReviewDto;
import com.example.yanolza.dto.TravelDto;
import com.example.yanolza.entity.Image;
import com.example.yanolza.entity.Member;
import com.example.yanolza.entity.Review;
import com.example.yanolza.entity.Travel;
import com.example.yanolza.repository.ImageRepository;
import com.example.yanolza.repository.MemberRepository;
import com.example.yanolza.repository.ReviewRepository;
import com.example.yanolza.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final MemberService memberService;
    private final ReviewRepository reviewRepository;
    private final TravelRepository travelRepository;
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;

    //tno가 같은 review 리스트 보여주기
    public List<ReviewDto> findByTno(Long tno) {
        Member member = memberService.memberIdFindMember();
        List<ReviewDto> list = new ArrayList<>();
        List<Review> reviewList = reviewRepository.findByTravel_Tno(tno);
        for (Review e : reviewList) {
            List<Image> imageList = imageRepository.findByReview_Rno(e.getRno());
            List<ImageDto> imgList = new ArrayList<>();
            for (Image image : imageList) {
                imgList.add(ImageDto.of(image));
            }
            list.add(ReviewDto.of(e, imgList, member.getNick().equals(e.getRnick().getNick())));
        }
        return list;
    }

    public boolean updateReview(List<ReviewDto> dtos) {
        boolean isTrue = false;
        for (ReviewDto dto : dtos) {
            Review review = Review.builder()
                    .rno(dto.getRno())
                    .rdate(LocalDateTime.now()) // 수정하면 수정한 시간으로 시간 바뀌나요??
                    .title(dto.getTitle())
                    .rcontent(dto.getRcontent())
                    .rate(dto.getRate())
                    .build();
            reviewRepository.save(review);
            isTrue = true;
        }
        return isTrue;
    }

    public boolean UpdateImage(ImageDto img){
        boolean isTrue = false;
        Long rno = (long)img.getRno();
        Optional<Review> reImg = reviewRepository.findById(rno);
        if (reImg.isPresent()) {
                Image image = Image.builder()
                        .ino(img.getIno())
                        .iimage(img.getImage())
                        .review(reImg.get())
                        .build();
                imageRepository.save(image);
            isTrue = true;
        }
        return isTrue;
    }

//    public boolean deleteImage(ImageDto imageDto){
//
//    }



    public boolean saveReview(ReviewDto reviewDto) {
        boolean isTrue = false;
        Member member = memberService.memberIdFindMember();
        Long tno = (long) reviewDto.getTno();
        Optional<Travel> travel = travelRepository.findById(tno);
        if (travel.isPresent()) {
            Review review = Review.builder()
                    .title(reviewDto.getTitle())
                    .travel(travel.get())
                    .rcontent(reviewDto.getRcontent())
                    .rate(reviewDto.getRate())
                    .rdate(LocalDateTime.now())
                    .rnick(member)
                    .build();
            reviewRepository.save(review);
            isTrue = true;
        } else {
            isTrue = false;
        }
        return isTrue;
    }

    // 댓글 삭제
    public boolean reviewDelete(Long id) {
        try {
            Review review = reviewRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("해당 댓글이 존재하지 않습니다.")
            );
            reviewRepository.delete(review);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
