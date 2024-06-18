package com.example.yanolza.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Review {
    @Id
    @Column(name = "rno")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rno;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String taddr;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String tcontent;
    @Column(nullable = false)
    private Date tdate;
    @Column(nullable = false)
    private int rate;
    @Column(nullable = false)
    private String nick;
}
