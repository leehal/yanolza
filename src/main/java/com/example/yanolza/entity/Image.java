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
public class Image {
    @Id
    @Column(name = "ino")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ino;
    @ManyToOne
    @JoinColumn(name = "rno")
    private Review rno;
    private String iimage;
}
