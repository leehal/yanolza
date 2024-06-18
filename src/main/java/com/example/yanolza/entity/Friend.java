package com.example.yanolza.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Friend {
    @Id
    @Column(name = "fno")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fno;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nick")
    private Member from;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uno")
    private Member to;
    @Column(nullable = false)
    private String accept;
}
