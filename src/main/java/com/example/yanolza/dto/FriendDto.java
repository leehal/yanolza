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
    String from;
    String to;
    String accept;

    public static FriendDto of(Friend friend){
        return FriendDto.builder()
                .from(friend.getFrom().getNick())
                .to(friend.getTo().getNick())
                .accept(friend.getAccept().toString())
                .build();
    }
}
