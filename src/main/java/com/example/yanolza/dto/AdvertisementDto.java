package com.example.yanolza.dto;

import com.example.yanolza.entity.Advertisement;
import com.example.yanolza.entity.Calendar;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementDto {
    private Long ano;
    private String aimage; // 오타 표시되는 것 고치면 푸시 가능한지 확인 중
    private Long adate; // 2222
    private String alink;

    public static AdvertisementDto of (Advertisement advertisement){
        return AdvertisementDto.builder()
                .aimage(advertisement.getAimage())
                .adate(advertisement.getAdate())
                .alink(advertisement.getAlink())
                .ano(advertisement.getAno())
                .build();
    }
}
