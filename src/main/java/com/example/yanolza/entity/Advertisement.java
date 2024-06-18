package com.example.yanolza.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private String aimg;
    @Column(nullable = false)
    private LocalDateTime adate;
    @Column(nullable = false)
    private int price;
}
