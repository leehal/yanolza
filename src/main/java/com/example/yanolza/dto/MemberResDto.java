package com.example.yanolza.dto;

import com.example.yanolza.constant.Social;
import com.example.yanolza.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResDto {
    private String nick;
    private String email;
    private String mid;
    private String image;
    private Social social;
    
    public static MemberResDto of (Member member){
        return MemberResDto.builder()
                .mid(member.getMid())
                .nick(member.getNick())
                .email(member.getEmail())
                .image(member.getImage())
                .social(member.getSocial())
                .build();
    }
}