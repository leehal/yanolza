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
public class Chatting {
    @Id
    @Column(name = "chno")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chno;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="crno")
    private ChattingRoom crno;
    @Column(nullable = false)
    private LocalDateTime chdate;
    @Column(nullable = false)
    private String chcontent;
}
