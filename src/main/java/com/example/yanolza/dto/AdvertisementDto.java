package com.example.yanolza.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementDto {
    private Long ano;
    private String ad_img; // 오타 표시되는 것 고치면 푸시 가능한지 확인 중
    private LocalDateTime ad_date; // 2222
    private int price;
}
