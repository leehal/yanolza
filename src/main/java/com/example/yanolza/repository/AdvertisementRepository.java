package com.example.yanolza.repository;

import com.example.yanolza.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement,Long> {
 List<Advertisement> findByAdateGreaterThan(Long now);

}
