package com.example.yanolza.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "travel")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Travel {

    @Id
    @Column(name = "tno")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tno;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String addr; // 주소
    private String image;
    private String category; // 분류
    private String course;
    private String phone;
    private String homepage;
    private String main; // 대표 메뉴
    private String vehicle; // 교통수단
    private String park; // 주차
    private int cost;
    private LocalDateTime sdate; // 시작일
    private LocalDateTime edate; // 종료일
    private String time; // 시작 & 종료 시간, 시대, 입퇴실
    private boolean book; // 예약
    private String content;
    private String guide;
    private String info;
}
