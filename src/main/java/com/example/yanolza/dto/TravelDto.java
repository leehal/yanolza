package com.example.yanolza.dto;

import com.example.yanolza.entity.Travel;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelDto {
    private String tname;
    private String taddr;
    private boolean book;
    private int tprice;
    private String timage;
    private String tcategory; // 분류
    private String course;
    private String phone;
    private String homepage;
    private String main; // 대표 메뉴
    private String vehicle; // 교통수단
    private String park; // 주차
    private LocalDateTime sdate; // 시작일
    private LocalDateTime edate; // 종료일
    private String time; // 시작 & 종료 시간, 시대, 입퇴실
    private String guide;
    private String info;

    public static TravelDto of(Travel travel) {
        return TravelDto.builder()
                .tname(travel.getTname())
                .taddr(travel.getTaddr())
                .book(travel.isBook())
                .tprice(travel.getTprice())
                .build();
    }
}
