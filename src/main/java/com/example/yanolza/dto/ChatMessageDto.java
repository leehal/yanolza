package com.example.yanolza.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
    public enum MessageType{
        ENTER, TALK, CLOSE;
    }
    private MessageType type;
    private String roodId;
    private String sender; // 유저 이름
    private String message;
}
