package com.example.yanolza.entity;

import com.example.yanolza.constant.TF;
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
@Table(name = "friend")
@Slf4j
public class Friend {
    @Id
    @Column(name = "fno")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fno;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender")
    private Member from;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver")
    private Member to;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TF accept;
}
