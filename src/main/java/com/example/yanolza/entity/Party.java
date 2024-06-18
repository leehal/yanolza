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
public class Party {
    @Id
    @Column(name = "pno")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pno;
    @Column(nullable = false)
    private String pname;
    @Column(nullable = false)
    private LocalDateTime pdate;

}
