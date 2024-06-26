package com.example.yanolza.dto;

import com.example.yanolza.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DibsDto {
    private Long dno;
    private Member dibsNick;
    private String dibsAddress; // 오타 표시되는 것 고치면 푸시 가능한지 확인 중
}
