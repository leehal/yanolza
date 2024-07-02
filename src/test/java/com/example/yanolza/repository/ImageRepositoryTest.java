package com.example.yanolza.repository;

import com.example.yanolza.entity.Image;
import com.example.yanolza.entity.Member;
import com.example.yanolza.entity.Review;
import com.example.yanolza.entity.Travel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
//@Transactional
@Slf4j
class ImageRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ImageRepository imageRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("save 실험")
    public void createImage(int rno, String image) {
        Long rno1 = (long) rno;
        Optional<Review> review = reviewRepository.findById(rno1);

        if(review.isPresent()){
            Image image1 = new Image();
            image1.setIimage(image);
            image1.setReview(review.get());
            imageRepository.save(image1);
        }
    }

    @Test
    @DisplayName("image 저장 테스트")
    public void saves(){
        createImage(61, "https://news.kbs.co.kr/data/news/2021/01/13/20210113_sYskRy.jpg");
        createImage(61, "https://res.klook.com/image/upload/q_85/c_fill,w_1360/v1617101647/blog/edlhmuf96dpqcnodl9qf.webp");
        createImage(86, "https://news.kbs.co.kr/data/fckeditor/new/image/2021/05/07/314691620354493423.jpg");
        createImage(86, "https://images.chosun.com/resizer/duCX7CUWjjlX9_MJW2UGUKVjvCg=/616x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/CU422HY2YJHS7JQ3REJ24E4H64.PNG");
        createImage(86, "https://assets.blog.engoo.com/wp-content/uploads/sites/2/2022/01/14205148/%ED%98%BC%EC%9E%90%EC%97%AC%ED%96%89-back-image-%EB%B3%B5%EC%82%AC.jpg.webp");
    }

    @Test
    @DisplayName("이미지 수정 테스트")
    public void update(){
        Long no = (long) 107;
        Long rno = (long) 61;
        Optional<Review> review = reviewRepository.findById(rno);
        if (review.isPresent()){

            Image image = Image.builder()
                    .ino(no)
                    .iimage("https://news.kbs.co.kr/data/news/2021/01/13/20210113_sYskRy.jpg")
                    .review(review.get())
                    .build();
            imageRepository.save(image);
        }
    }
}