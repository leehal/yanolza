package com.example.yanolza.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Social {
    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private Long id;
    private String pwd;
    private String mid;
    private String nick;
    private String profile;
    @Column(name="refresh_token")
    private String refreshToken;
    @Column(name="refresh_token_exp")
    private Long refreshTokenExpiresIn;

    @Builder
    public Social(String pwd,String mid, String nick, String profile) {
        this.pwd = pwd;
        this.mid = mid;
        this.nick = nick;
        this.profile = profile;
    }
}