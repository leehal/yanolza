package com.example.yanolza.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyResponseDto {
    private List<CalendarDto> dto;
    private List<String> nick;

}
