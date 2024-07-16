package com.example.yanolza.entity;


import com.example.yanolza.constant.Authority;
import com.example.yanolza.constant.Social;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    @Enumerated(EnumType.STRING)
    private Authority authority;
    @Enumerated(EnumType.STRING)
    private Social social;
    @Column(name="refresh_token")
    private String refreshToken;
    @Column(name="refresh_token_exp")
    private Long refreshTokenExpiresIn;

    @OneToMany(mappedBy = "calenderNick", cascade = CascadeType.REMOVE)
    private List<Calendar> calendars;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.REMOVE)
    private List<Chatting> sentMessages;

    @OneToMany(mappedBy = "dnick", cascade = CascadeType.REMOVE)
    private List<Dibs> dibs;

    @OneToMany(mappedBy = "from", cascade = CascadeType.REMOVE)
    private List<Friend> sentFriendRequests;

    @OneToMany(mappedBy = "to", cascade = CascadeType.REMOVE)
    private List<Friend> receivedFriendRequests;

    @OneToMany(mappedBy = "rnick", cascade = CascadeType.REMOVE)
    private List<Review> rnick;

    @OneToMany(mappedBy = "partyPeopleNick", cascade = CascadeType.REMOVE)
    private List<PartyPeople> partyPeople;

    @Builder
    public Member(String nick, String mid, String pwd, String email, String image, Social social,Authority authority) {
        this.nick = nick;
        this.mid = mid;
        this.pwd = pwd;
        this.email = email;
        this.image = image;
        this.social = social;
        this.authority = authority;
    }
}