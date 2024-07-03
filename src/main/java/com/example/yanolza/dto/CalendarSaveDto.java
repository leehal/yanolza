package com.example.yanolza.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalendarSaveDto {
    private Long pno;
    private String nick;
    private String  date;
    private List<CalendarDto> dtos;
}
