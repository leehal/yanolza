package com.example.yanolza.dto;

import com.example.yanolza.entity.Travel;
import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelDto {
    private Long tno;
    private String tname;
    private String taddr;
    private boolean book;
    private String tcategory; // 분류
    private String timage;
    private String tprice;
    private String course;
    private String phone;
    private String homepage;
    private String main; // 대표 메뉴
    private String vehicle; // 교통수단, 주차
    private String season; // 기간
    private String time; // 시작 & 종료 시간, 시대, 입퇴실
    private String guide;
    private String info;

    public static TravelDto of(Travel travel) {
        return TravelDto.builder()
                .tno(travel.getTno())
                .tname(travel.getTname())
                .taddr(travel.getTaddr())
                .tcategory(travel.getTcategory())
                .timage(travel.getTimage())
                .book(travel.isBook())
                .tprice(travel.getTprice())
                .course(travel.getCourse())
                .guide(travel.getGuide())
                .info(travel.getInfo())
                .homepage(travel.getHomepage())
                .main(travel.getMain())
                .phone(travel.getPhone())
                .vehicle(travel.getVehicle())
                .season(travel.getSeason())
                .time(travel.getTime())
                .build();
    }
}
