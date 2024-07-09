package com.example.yanolza.dto;

import com.example.yanolza.entity.Calendar;
import com.example.yanolza.entity.Member;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarDto {
    private Long cano;
    private String caDate;
    private String caContent;
    private String calenderNick;
    private Long calenderPno;
    private String caddr;
    private String cplace;

    public static CalendarDto of (Calendar calendar){
        LocalDateTime dateTime = calendar.getCaDate();
        // 원하는 형식의 DateTimeFormatter 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // LocalDateTime을 문자열로 변환
        String date = dateTime.format(formatter);

        return CalendarDto.builder()
                .cano(calendar.getCano())
                .caDate(date)
                .caContent(calendar.getCaContent())
                .calenderNick(calendar.getCalenderNick().getNick())
                .calenderPno(calendar.getCalenderPno().getPno())
                .caddr(calendar.getCaddr())
                .cplace(calendar.getCplace())
                .build();
    }
}
