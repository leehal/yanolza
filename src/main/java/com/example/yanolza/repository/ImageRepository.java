package com.example.yanolza.repository;

import com.example.yanolza.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByReview_Rno (Long rno);
}
