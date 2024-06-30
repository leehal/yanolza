package com.example.yanolza.service;

import com.example.yanolza.constant.TF;
import com.example.yanolza.dto.AdvertisementDto;
import com.example.yanolza.dto.FriendDto;
import com.example.yanolza.dto.MemberReqDto;
import com.example.yanolza.dto.MemberResDto;
import com.example.yanolza.entity.Advertisement;
import com.example.yanolza.entity.Friend;
import com.example.yanolza.entity.Member;
import com.example.yanolza.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;

    public Boolean adPublish(AdvertisementDto advertisementDto) {
       Advertisement advertisement = advertisementRepository.save(
                Advertisement.builder()
                        .aimage(advertisementDto.getAimage())
                        .alink(advertisementDto.getAlink())
                        .adate(advertisementDto.getAdate())
                        .build()
        );

        return advertisement != null;
    }

    public List<AdvertisementDto> adList(Long now) {
        List<AdvertisementDto> list = new ArrayList<>();
        List<Advertisement> advertisements = advertisementRepository.findByAdateGreaterThan(now);
        for (Advertisement advertisement : advertisements) {
            AdvertisementDto advertisementDto = AdvertisementDto.of(advertisement);
            list.add(advertisementDto);
        }
        return list;
    }
}
