package com.example.yanolza.controller;


import com.example.yanolza.dto.ImageDto;
import com.example.yanolza.dto.MemberResDto;
import com.example.yanolza.dto.PartyNameListDto;
import com.example.yanolza.dto.ReviewDto;
import com.example.yanolza.entity.Image;
import com.example.yanolza.entity.Member;
import com.example.yanolza.entity.Review;
import com.example.yanolza.entity.Travel;
import com.example.yanolza.service.MemberService;
import com.example.yanolza.service.ReviewService;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final MemberService memberService;

    @GetMapping("/reviewlist")
    public ResponseEntity<List<ReviewDto>> reviewList(@RequestParam Long tno, @RequestParam String nick) {
        List<ReviewDto> list = reviewService.findByTno(tno,nick);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/reviewlist")
    public ResponseEntity<Boolean> createReview(@RequestBody ReviewDto review) {
        return ResponseEntity.ok(reviewService.saveReview(review));

    }
    @PostMapping("/saveimage")
    public ResponseEntity<Boolean> createImage(@RequestBody ReviewDto image){
        return ResponseEntity.ok(reviewService.saveImage(image));
    }


    @PostMapping("/delete/{id}")
    public ResponseEntity<Boolean> reviewDelete(@PathVariable Long id) {
        boolean result = reviewService.reviewDelete(id);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/update")
    public ResponseEntity<Boolean> reviewUpdate(@RequestBody ReviewDto review) {
        //reviewService.saveImage(review);
        return ResponseEntity.ok(reviewService.updateReview(review));
    }

    @PostMapping("/deleteimage/{id}")
    public ResponseEntity<Boolean> imageDelete(@PathVariable Long id) {
        boolean result = reviewService.imageDelete(id);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/upimgOne")
    public ResponseEntity<Boolean> imgOneUpdate(@RequestBody ImageDto imageDto){
        return ResponseEntity.ok(reviewService.updateOneImg(imageDto.getIno(), imageDto.getImage()));
    }
}


