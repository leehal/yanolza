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
    private String pname;
//    private LocalDateTime pdate;
    private List<String> nick;

//    public static PartyResponseDto of (Par)

}
