package com.example.yanolza.repository;

import com.example.yanolza.entity.Review;
import com.example.yanolza.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByTravel_Tno (Long tno);


}
