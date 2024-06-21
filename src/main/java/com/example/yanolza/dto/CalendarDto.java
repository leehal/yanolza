package com.example.yanolza.dto;

import com.example.yanolza.entity.Calendar;
import com.example.yanolza.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarDto {
    private LocalDateTime caDate;
    private String caContent;
    private String calenderNick;
    private Long calenderPno;
    private String caddr;

    public static CalendarDto of (Calendar calendar){
        return CalendarDto.builder()
                .caDate(calendar.getCaDate())
                .caContent(calendar.getCaContent())
                .calenderNick(calendar.getCalenderNick().getNick())
                .calenderPno(calendar.getCalenderPno().getPno())
                .caddr(calendar.getCaddr())
                .build();
    }
}
