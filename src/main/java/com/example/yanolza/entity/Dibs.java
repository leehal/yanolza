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
public class Dibs {
    @Id
    @Column(name = "dno")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long dno;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "nick")
    private Member dnick;
    private Long tno;
}
