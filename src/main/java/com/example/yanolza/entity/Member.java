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
    private String mid;
    @Column(unique = true,nullable = false)
    private String nick;
    @Column(nullable = false)
    private String pwd;
    @Column(unique = true)
    private String email;
    private String image;
    private String social;
    @Column(name="refresh_token")
    private String refreshToken;
    @Column(name="refresh_token_exp")
    private Long refreshTokenExpiresIn;

    @Builder
    public Member(String nick, String mid, String pwd, String email, String image, String social) {
        this.nick = nick;
        this.mid = mid;
        this.pwd = pwd;
        this.email = email;
        this.image = image;
        this.social = social;
    }
}