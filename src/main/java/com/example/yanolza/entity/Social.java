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
    private Long sno;
    private String mid;
    private String nick;
    private String profile;

    @Builder
    public Social(String mid, String nick, String profile) {
        this.mid = mid;
        this.nick = nick;
        this.profile = profile;
    }
}