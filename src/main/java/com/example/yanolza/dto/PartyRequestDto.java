package com.example.yanolza.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyRequestDto {
    private List<String> nick;
    private String pName;
}
