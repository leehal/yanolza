package com.example.yanolza.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PDateChatDto {
    private List<String > dates;
    private String roomId;
}
