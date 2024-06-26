package com.example.yanolza.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "travel")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Travel {
    @Id
    @Column(name = "tno")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tno;
    @Column(nullable = false)
    private String tname;
    @Column(nullable = false)
    private String taddr; // 주소
    private boolean book; // 예약 (자동 not null)
    private int tprice; // (자동 not null)
    private String timage;
    private String tcategory; // 분류
    private String course;
    private String phone;
    private String homepage;
    private String main; // 대표 메뉴
    private String vehicle; // 교통수단
    private String park; // 주차
    private LocalDateTime sdate; // 시작일
    private LocalDateTime edate; // 종료일
    private String time; // 시작 & 종료 시간, 시대, 입퇴실
    private String guide;  // 셋 중 하나 고민 중
    private String info;
}
