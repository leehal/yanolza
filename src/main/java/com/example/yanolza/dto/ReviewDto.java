package com.example.yanolza.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Long rno;
    private String title;
    private String tripAddress; // 오타 표시되는 것 고치면 푸시 가능한지 확인 중
    private String category;
    private String tripContent; // 22222222
    private Date tdate;
    private int rate;
    private String nick;
}
