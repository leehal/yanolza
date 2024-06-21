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
public class Calendar {
    @Id
    @Column(name = "cano")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cano;
    @Column(nullable = false)
    private LocalDateTime caDate;
    @Column(nullable = false)
    private String caContent;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nick")
    private Member calenderNick;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno")
    private Party calenderPno;
    private String color;
    private String caddr;
}
