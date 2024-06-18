package com.example.yanolza.entity;


import com.example.yanolza.constant.Authority;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Member {
    @Id
    @Column(name = "uno")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long uno;
    @Column(unique = true,nullable = false)
    private String id;
    @Column(unique = true,nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String pwd;
    @Column(unique = true,nullable = false)
    private String email;
    private String image;
    private LocalDateTime regDate;
    @Enumerated(EnumType.STRING)
    private Authority authority;

//    public Member()

    @Builder
    public Member(String nickname, String id, String pwd, String email, String image, Authority authority) {
        this.nickname = nickname;
        this.id = id;
        this.pwd = pwd;
        this.email = email;
        this.image = image;
        this.authority = authority;
        this.regDate = LocalDateTime.now();
    }
}