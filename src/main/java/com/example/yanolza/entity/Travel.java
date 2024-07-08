package com.example.yanolza.entity;

import lombok.*;

import javax.persistence.*;

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
    private String tprice; // (자동 not null)
    private String timage;
    private String tcategory; // 분류
    private String course;
    private String phone;
    private String homepage;
    private String main; // 대표 메뉴
    private String vehicle; // 교통수단, 주차
    private String season; // 기간
    private String time; // 시작 & 종료 시간, 시대, 입퇴실
    private String guide;
    private String info;
}
