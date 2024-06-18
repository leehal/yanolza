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
public class PartyPeople {
    @Id
    @Column(name = "ppno")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ppno;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno")
    private Party partyPeaplePno;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nick")
    private Member partyPeaplenick;
}
