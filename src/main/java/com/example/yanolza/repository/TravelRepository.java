package com.example.yanolza.repository;

import com.example.yanolza.entity.Travel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TravelRepository extends JpaRepository<Travel,Long> {
    Optional<Travel> findByTno(Long tno);
    Page<Travel> findByTcategory(String category, Pageable pageable);
    Page<Travel> findByTnameContaining(String name, Pageable pageable);
    Page<Travel> findByTaddrLike(String city,Pageable pageable);
    Page<Travel> findByTcategoryAndTnameContaining(String category,String name, Pageable pageable);
    Page<Travel> findByTaddrLikeAndTnameContaining(String city,String name,Pageable pageable);
    Page<Travel> findByTcategoryAndTaddrLike(String category,String city,Pageable pageable);
    Page<Travel> findByTcategoryAndTaddrLikeAndTnameContaining(String category,String city,String name,Pageable pageable);
}
