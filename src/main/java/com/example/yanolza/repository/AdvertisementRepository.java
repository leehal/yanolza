package com.example.yanolza.repository;

import com.example.yanolza.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdvertisementRepository extends JpaRepository<Advertisement,Long> {

}
