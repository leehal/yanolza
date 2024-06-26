package com.example.yanolza.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Long tno;
    private String title;
    private String raddress; // 오타 표시되는 것 고치면 푸시 가능한지 확인 중
    private String category;
    private String rcontent; // 22222222
    private Date rdate;
    private int rate;
    private String nick;
}
