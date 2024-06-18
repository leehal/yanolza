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
@Table(name = "Chatting_Room")

public class ChattingRoom {
    @Id
    @Column(name = "crno")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long crno;
}
