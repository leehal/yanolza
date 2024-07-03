package com.example.yanolza.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PmemberDibsReqDto {
    private List<MemberResDto> memberResDtos;
}
