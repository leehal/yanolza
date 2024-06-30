package com.example.yanolza.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Advertisement {
    @Id
    @Column(name = "ano")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ano;
    @Column(nullable = false)
    private String aimage;
    @Column(nullable = false)
    private Long adate;
    @Column(nullable = false)
    private String alink;
}
