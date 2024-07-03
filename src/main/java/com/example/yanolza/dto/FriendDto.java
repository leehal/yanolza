package com.example.yanolza.dto;

import com.example.yanolza.entity.Friend;
import com.example.yanolza.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendDto {
    Long fno;
    String from;
    String to;
    String accept;

    public static FriendDto of(Friend friend){
        return FriendDto.builder()
                .fno(friend.getFno())
                .from(friend.getFrom().getNick())
                .to(friend.getTo().getNick())
                .accept(friend.getAccept().toString())
                .build();
    }
}
